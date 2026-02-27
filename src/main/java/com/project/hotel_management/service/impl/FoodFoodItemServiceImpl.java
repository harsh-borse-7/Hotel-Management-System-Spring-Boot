package com.project.hotel_management.service.impl;

import com.project.hotel_management.dto.FoodItemDto;
import com.project.hotel_management.exception.ResourceNotFoundException;
import com.project.hotel_management.model.FoodItem;
import com.project.hotel_management.model.Hotel;
import com.project.hotel_management.repository.HotelRepository;
import com.project.hotel_management.repository.FoodItemRepository;
import com.project.hotel_management.service.FoodItemService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodFoodItemServiceImpl implements FoodItemService {

  private final FoodItemRepository foodItemRepository;
  private final HotelRepository hotelRepository;

  @Override
  public FoodItemDto addFoodItems(Long hotelId, FoodItemDto foodItem) {
    Hotel hotel =
        hotelRepository
            .findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));
    hotel.addFoodItem(
        new FoodItem(
            foodItem.getFoodItemName(), foodItem.getPrice(), foodItem.getDescription(), hotel));
    hotelRepository.save(hotel);
    return foodItem;
  }

  @Override
  @Transactional(readOnly = true)
  public List<FoodItemDto> getFoodItemsByHotel(Long hotelId, Long foodItemId, String foodItemName) {
    hotelRepository
        .findById(hotelId)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));

    if (foodItemId != null) {
      return Collections.singletonList(
          foodItemRepository
              .findByItemIdAndHotel_HotelId(foodItemId, hotelId)
              .map(
                  foodItem ->
                      new FoodItemDto(
                          foodItem.getFoodItemId(),
                          foodItem.getFoodItemName(),
                          foodItem.getPrice(),
                          foodItem.getDescription(),
                          hotelId))
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Item with id " + foodItemId + " not found for hotel " + hotelId)));
    } else if (foodItemName != null) {
      foodItemRepository
          .findByItemNameAndHotel_HotelId(foodItemName, hotelId)
          .map(
              foodItem ->
                  new FoodItemDto(
                      foodItem.getFoodItemId(),
                      foodItem.getFoodItemName(),
                      foodItem.getPrice(),
                      foodItem.getDescription(),
                      hotelId))
          .orElseThrow(
              () ->
                  new ResourceNotFoundException(
                      "Item '" + foodItemName + "' not found for hotel " + hotelId));
    }
    return foodItemRepository.findByHotel_HotelId(hotelId).stream()
        .map(
            fooditems ->
                new FoodItemDto(
                    fooditems.getFoodItemId(),
                    fooditems.getFoodItemName(),
                    fooditems.getPrice(),
                    fooditems.getDescription(),
                    hotelId))
        .toList();
  }

  @Override
  public FoodItemDto updateItem(Long hotelId, Long foodItemId, FoodItemDto foodItem) {
    hotelRepository
        .findById(hotelId)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));
    FoodItem updateItem =
        foodItemRepository
            .findByItemIdAndHotel_HotelId(foodItemId, hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Item Id " + foodItemId + " not found"));
    if (foodItem.getFoodItemName() != null && !foodItem.getFoodItemName().isBlank())
      updateItem.setFoodItemName(foodItem.getFoodItemName());
    if (foodItem.getPrice() != null) updateItem.setPrice(foodItem.getPrice());
    if (foodItem.getDescription() != null && !foodItem.getDescription().isBlank())
      updateItem.setDescription(foodItem.getDescription());

    foodItemRepository.save(updateItem);

    return new FoodItemDto(
        updateItem.getFoodItemId(),
        updateItem.getFoodItemName(),
        updateItem.getPrice(),
        updateItem.getDescription(),
        hotelId);
  }

  @Override
  public void deleteItem(Long hotelId, Long foodItemId) {
    hotelRepository
        .findById(hotelId)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));
    FoodItem item =
        foodItemRepository
            .findByItemIdAndHotel_HotelId(foodItemId, hotelId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Item with id " + foodItemId + " not found for hotel " + hotelId));

    foodItemRepository.delete(item);
  }
}
