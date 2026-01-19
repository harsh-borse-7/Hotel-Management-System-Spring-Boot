package com.project.hotel_management.service.impl;

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
  public Hotel addHotel(Hotel hotel) {
    return hotelRepository.save(hotel);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Hotel> getHotels() {
    return hotelRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Hotel findByHotelID(long hotelId) {
    return hotelRepository
        .findById(hotelId)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel Id " + hotelId + " not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public Hotel findByHotelName(String hotelName) {
    return hotelRepository
        .findByHotelName(hotelName)
        .orElseThrow(
            () -> new ResourceNotFoundException("Hotel name  " + hotelName + " not found"));
  }

  @Override
  public Hotel updateHotel(Long hotelId, Hotel hotel) {
    Hotel updateHotel =
        hotelRepository
            .findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel id " + hotelId + " not found"));

    if (hotel.getHotelName() != null && !hotel.getHotelName().isBlank())
      updateHotel.setHotelName(hotel.getHotelName());
    if (hotel.getLocation() != null && !hotel.getLocation().isBlank())
      updateHotel.setLocation(hotel.getLocation());

    return updateHotel;
  }

  @Override
  public void deleteHotel(Long hotel_id) {
    if (hotelRepository.existsById(hotel_id)) {
      hotelRepository.deleteById(hotel_id);
    } else {
      throw new ResourceNotFoundException("Hotel id " + hotel_id + " not found");
    }
  }
}
