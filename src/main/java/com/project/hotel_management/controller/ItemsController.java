package com.project.hotel_management.controller;

import com.project.hotel_management.model.FoodItem;
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
  public ResponseEntity<FoodItem> addItemInMenu(
      @PathVariable Long hotelId, @RequestBody FoodItem foodItem) {
    return ResponseEntity.status(HttpStatus.CREATED).body(itemService.addItem(hotelId, foodItem));
  }

  @GetMapping
  public ResponseEntity<List<FoodItem>> getAllItems(@PathVariable Long hotelId) {
    return ResponseEntity.ok(itemService.getAllItems(hotelId));
  }

  @GetMapping("/{itemID}")
  public ResponseEntity<FoodItem> getItemById(
      @PathVariable Long hotelId, @PathVariable Long itemID) {
    return ResponseEntity.ok(itemService.findByItemID(hotelId, itemID));
  }

  @GetMapping("/by-name")
  public ResponseEntity<FoodItem> getItemByName(
      @PathVariable Long hotelId, @RequestParam String name) {
    return ResponseEntity.ok(itemService.findByItemName(hotelId, name));
  }

  @PatchMapping("/{itemId}")
  public ResponseEntity<FoodItem> updateItem(
      @PathVariable Long hotelId, @PathVariable Long itemId, @RequestBody FoodItem itemDetails) {
    return ResponseEntity.ok(itemService.updateItem(hotelId, itemId, itemDetails));
  }

  @DeleteMapping("/{itemId}")
  public ResponseEntity<FoodItem> deleteItem(
      @PathVariable Long hotelId, @PathVariable Long itemId) {
    itemService.deleteItem(hotelId, itemId);
    return ResponseEntity.noContent().build();
  }
}
