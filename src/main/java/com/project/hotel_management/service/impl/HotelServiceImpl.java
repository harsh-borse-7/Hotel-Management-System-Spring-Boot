package com.project.hotel_management.service.impl;

import com.project.hotel_management.dto.HotelResponse;
import com.project.hotel_management.exception.ResourceConflictException;
import com.project.hotel_management.exception.ResourceNotFoundException;
import com.project.hotel_management.model.Hotel;
import com.project.hotel_management.model.Menu;
import com.project.hotel_management.repository.HotelRepository;
import com.project.hotel_management.repository.MenuRepository;
import com.project.hotel_management.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final MenuRepository menuRepository;
    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, MenuRepository menuRepository) {
        this.hotelRepository = hotelRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public Hotel addHotel(Hotel hotel){
        Optional<Hotel> findHotel = hotelRepository.findByHotelName(hotel.getHotelName());
        if (findHotel.isPresent()) {
            throw new ResourceConflictException("HotelName " + hotel.getHotelName() + " already exists");
        }
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel updateHotel(String hotelName, Hotel hotel) {
        Optional<Hotel> hotelOptional = hotelRepository.findByHotelName(hotelName);
        if(hotelOptional!=null){
            Hotel updateHotel = hotelOptional.get();
            updateHotel.setHotelID(hotel.getHotelID());
            updateHotel.setLocation(hotel.getLocation());
            updateHotel.setMultiplier(hotel.getMultiplier());
            updateHotel.setItemIDs(hotel.getItemIDs());
            return hotelRepository.save(updateHotel);
        }
        else{
            throw new ResourceNotFoundException("Hotel name " + hotelName + " not found");
        }
    }

    @Override
    public List<HotelResponse> getHotels() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream()
                .map(this::mapHotelToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public HotelResponse findByHotelID(long hotelID) {
        Hotel hotel = hotelRepository.findById(hotelID)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel Id " + hotelID + " not found"));

        // Mapping the Hotel entity to HotelResponse
        return mapHotelToResponse(hotel);
    }

    @Override
    public HotelResponse findByHotelName(String hotelName) {
        Hotel hotel = hotelRepository.findByHotelName(hotelName)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel name  " + hotelName + " not found"));
        return mapHotelToResponse(hotel);
    }

    @Override
    public void deleteHotel(String hotelName) {
         Hotel hotel= hotelRepository.findByHotelName(hotelName)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Name: " + hotelName));
        hotelRepository.delete(hotel);
    }

    private HotelResponse mapHotelToResponse(Hotel hotel) {
        List<Object> items = getMenuItemsByIDs(hotel.getItemIDs(),hotel.getMultiplier());
        return new HotelResponse.Builder()
                .hotelID(hotel.getHotelID())
                .hotelName(hotel.getHotelName())
                .location(hotel.getLocation())
                .multiplier(hotel.getMultiplier())
                .items(items)
                .build();
    }


    private List<Object> getMenuItemsByIDs(String itemIDs, Double multiplier) {
        List<Object> result = new ArrayList<>();
        String[] itemIdArray = itemIDs.split(",");

        for (String itemId : itemIdArray) {
            try {
                Long id = Long.parseLong(itemId.trim());
                Menu menuItem = menuRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + id));
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("itemID", menuItem.getitemID());
                itemData.put("itemName", menuItem.getItemName());
                itemData.put("basePrice", (long) (menuItem.getbasePrice() * multiplier));
                itemData.put("description", menuItem.getDescription());
                result.add(itemData);
            } catch (ResourceNotFoundException ex) {
                // Add a placeholder object for the missing item
                Map<String, Object> error = new HashMap<>();
                error.put("itemID", itemId);
                error.put("error", ex.getMessage());
                result.add(error);
            }
        }
        return result;
    }

}
