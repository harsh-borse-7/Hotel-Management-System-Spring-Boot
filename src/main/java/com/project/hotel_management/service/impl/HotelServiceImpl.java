package com.project.hotel_management.service.impl;

import com.project.hotel_management.dto.FoodItemDto;
import com.project.hotel_management.dto.HotelDto;
import com.project.hotel_management.exception.ResourceNotFoundException;
import com.project.hotel_management.model.Hotel;
import com.project.hotel_management.repository.HotelRepository;
import com.project.hotel_management.service.HotelService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelServiceImpl implements HotelService {

  private final HotelRepository hotelRepository;

  @Override
  public HotelDto addHotel(HotelDto hotelDto) {
    Hotel addHotel = new Hotel(hotelDto.getHotelName(), hotelDto.getLocation());
    Hotel hotel = hotelRepository.save(addHotel);

    return new HotelDto(
        hotel.getHotelId(), hotel.getHotelName(), hotel.getLocation(), mapFoodItems(hotel));
  }

  @Override
  @Transactional(readOnly = true)
  public List<HotelDto> getHotels() {

    return hotelRepository.findAll().stream()
        .map(
            hotel ->
                new HotelDto(
                    hotel.getHotelId(),
                    hotel.getHotelName(),
                    hotel.getLocation(),
                    mapFoodItems(hotel)))
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public HotelDto findByHotelID(long hotelId) {
    Hotel hotel =
        hotelRepository
            .findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel Id " + hotelId + " not found"));
    return new HotelDto(
        hotel.getHotelId(), hotel.getHotelName(), hotel.getLocation(), mapFoodItems(hotel));
  }

  @Override
  @Transactional(readOnly = true)
  public HotelDto findByHotelName(String hotelName) {
    Hotel hotel =
        hotelRepository
            .findByHotelName(hotelName)
            .orElseThrow(
                () -> new ResourceNotFoundException("Hotel name  " + hotelName + " not found"));
    return new HotelDto(
        hotel.getHotelId(), hotel.getHotelName(), hotel.getLocation(), mapFoodItems(hotel));
  }

  @Override
  public HotelDto updateHotel(Long hotelId, HotelDto hotelDetails) {
    Hotel hotel =
        hotelRepository
            .findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel id " + hotelId + " not found"));

    if (hotelDetails.getHotelName() != null && !hotelDetails.getHotelName().isBlank())
      hotel.setHotelName(hotelDetails.getHotelName());
    if (hotelDetails.getLocation() != null && !hotelDetails.getLocation().isBlank())
      hotel.setLocation(hotelDetails.getLocation());

    return new HotelDto(
        hotel.getHotelId(), hotel.getHotelName(), hotel.getLocation(), mapFoodItems(hotel));
  }

  @Override
  public void deleteHotel(Long hotelId) {
    if (hotelRepository.existsById(hotelId)) {
      hotelRepository.deleteById(hotelId);
    } else {
      throw new ResourceNotFoundException("Hotel id " + hotelId + " not found");
    }
  }

  private List<FoodItemDto> mapFoodItems(Hotel hotel) {
    return hotel.getFoodItems().stream()
        .map(
            food ->
                new FoodItemDto(
                    food.getItemId(),
                    food.getItemName(),
                    food.getPrice(),
                    food.getDescription(),
                    hotel.getHotelId()))
        .toList();
  }
}
