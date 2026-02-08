package com.project.hotel_management.service;

import com.project.hotel_management.dto.FoodItemDto;
import java.util.List;

public interface ItemService {

  FoodItemDto addItemToHotel(Long hotelId, FoodItemDto foodItem);

  List<FoodItemDto> getAllItemsByHotelId(Long hotelId);

  FoodItemDto findByItemID(Long hotelId, Long itemID);

  FoodItemDto findByItemName(Long hotelId, String itemName);

  FoodItemDto updateItem(Long hotelId, Long itemId, FoodItemDto foodItem);

  void deleteItem(Long hotelId, Long itemId);
}
