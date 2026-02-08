package com.project.hotel_management.service;

import com.project.hotel_management.dto.HotelDto;
import java.util.List;

public interface HotelService {

  HotelDto addHotel(HotelDto hotelDto);

  List<HotelDto> getHotels();

  HotelDto findByHotelID(long hotelId);

  HotelDto findByHotelName(String hotelName);

  HotelDto updateHotel(Long hotelName, HotelDto hotel);

  void deleteHotel(Long hotelId);
}
