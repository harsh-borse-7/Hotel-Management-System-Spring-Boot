package com.project.hotel_management.service;

import com.project.hotel_management.model.FoodItem;
import java.util.List;

public interface ItemService {

  FoodItem addItem(Long hotelId, FoodItem foodItem);

  List<FoodItem> getAllItems(Long hotelId);

  FoodItem findByItemID(Long hotelId, Long itemID);

  FoodItem findByItemName(Long hotelId, String itemName);

  FoodItem updateItem(Long hotelId, Long itemId, FoodItem foodItem);

  void deleteItem(Long hotelId, Long itemId);
}
