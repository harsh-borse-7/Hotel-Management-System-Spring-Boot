package com.project.hotel_management.controller;

import com.project.hotel_management.dto.FoodItemDto;
import com.project.hotel_management.service.FoodItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels/{hotelId}/foodItems")
@RequiredArgsConstructor
public class FoodItemController {

  private final FoodItemService foodItemService;

  @PostMapping
  public ResponseEntity<FoodItemDto> addItemToHotel(
      @PathVariable Long hotelId, @RequestBody FoodItemDto foodItem) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(foodItemService.addFoodItems(hotelId, foodItem));
  }

  @GetMapping
  public ResponseEntity<List<FoodItemDto>> getAllItemsByHotelId(
      @PathVariable Long hotelId,
      @RequestParam(required = false) Long foodItemId,
      @RequestParam(required = false) String foodItemName) {
    return ResponseEntity.ok(foodItemService.getFoodItemsByHotel(hotelId, foodItemId, foodItemName));
  }

  @PatchMapping("/{itemId}")
  public ResponseEntity<FoodItemDto> updateItem(
      @PathVariable Long hotelId, @PathVariable Long foodItemId, @RequestBody FoodItemDto FoodItemDetails) {
    return ResponseEntity.ok(foodItemService.updateItem(hotelId, foodItemId, FoodItemDetails));
  }

  @DeleteMapping("/{itemId}")
  public ResponseEntity<Void> deleteItem(@PathVariable Long hotelId, @PathVariable Long foodItemId) {
    foodItemService.deleteItem(hotelId, foodItemId);
    return ResponseEntity.noContent().build();
  }
}
