package com.project.hotel_management.controller;

import com.project.hotel_management.dto.FoodItemDto;
import com.project.hotel_management.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels/{hotelId}/items")
@RequiredArgsConstructor
public class ItemsController {

  private final ItemService itemService;

  @PostMapping
  public ResponseEntity<FoodItemDto> addItemToHotel(
      @PathVariable Long hotelId, @RequestBody FoodItemDto foodItem) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(itemService.addItemToHotel(hotelId, foodItem));
  }

  @GetMapping
  public ResponseEntity<List<FoodItemDto>> getAllItemsByHotelId(@PathVariable Long hotelId) {
    return ResponseEntity.ok(itemService.getAllItemsByHotelId(hotelId));
  }

  @GetMapping("/{itemID}")
  public ResponseEntity<FoodItemDto> getItemById(
      @PathVariable Long hotelId, @PathVariable Long itemID) {
    return ResponseEntity.ok(itemService.findByItemID(hotelId, itemID));
  }

  @GetMapping("/by-name")
  public ResponseEntity<FoodItemDto> getItemByName(
      @PathVariable Long hotelId, @RequestParam String name) {
    return ResponseEntity.ok(itemService.findByItemName(hotelId, name));
  }

  @PatchMapping("/{itemId}")
  public ResponseEntity<FoodItemDto> updateItem(
      @PathVariable Long hotelId, @PathVariable Long itemId, @RequestBody FoodItemDto itemDetails) {
    return ResponseEntity.ok(itemService.updateItem(hotelId, itemId, itemDetails));
  }

  @DeleteMapping("/{itemId}")
  public ResponseEntity<Void> deleteItem(@PathVariable Long hotelId, @PathVariable Long itemId) {
    itemService.deleteItem(hotelId, itemId);
    return ResponseEntity.noContent().build();
  }
}
