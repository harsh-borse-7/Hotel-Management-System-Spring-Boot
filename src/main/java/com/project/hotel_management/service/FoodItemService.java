package com.project.hotel_management.service;

import com.project.hotel_management.dto.FoodItemDto;
import java.util.List;

public interface FoodItemService {

  FoodItemDto addFoodItems(Long hotelId, FoodItemDto foodItem);

  List<FoodItemDto> getFoodItemsByHotel(Long hotelId, Long foodItemId, String foodItemName);

  FoodItemDto updateItem(Long hotelId, Long foodItemId, FoodItemDto foodItem);

  void deleteItem(Long hotelId, Long foodItemId);
}
