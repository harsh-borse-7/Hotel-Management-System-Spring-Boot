package com.project.hotel_management.service;

import com.project.hotel_management.dto.HotelResponse;
import com.project.hotel_management.model.Hotel;

import java.util.List;

public interface HotelService {

    Hotel addHotel(Hotel hotel);
    Hotel updateHotel(String hotelName, Hotel hotel);
    List<HotelResponse> getHotels();
    HotelResponse findByHotelID(long hotelID);
    HotelResponse findByHotelName(String hotelName);
    void deleteHotel(String hotelName);

}
