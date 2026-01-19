package com.project.hotel_management.service;

import com.project.hotel_management.model.Hotel;
import java.util.List;

public interface HotelService {

  Hotel addHotel(Hotel hotel);

  List<Hotel> getHotels();

  Hotel findByHotelID(long hotelId);

  Hotel findByHotelName(String hotelName);

  Hotel updateHotel(Long hotelName, Hotel hotel);

  void deleteHotel(Long hotelId);
}
