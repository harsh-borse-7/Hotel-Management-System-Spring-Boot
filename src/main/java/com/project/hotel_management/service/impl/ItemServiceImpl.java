package com.project.hotel_management.service.impl;

import com.project.hotel_management.dto.FoodItemDto;
import com.project.hotel_management.exception.ResourceNotFoundException;
import com.project.hotel_management.model.FoodItem;
import com.project.hotel_management.model.Hotel;
import com.project.hotel_management.repository.HotelRepository;
import com.project.hotel_management.repository.ItemRepository;
import com.project.hotel_management.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;
  private final HotelRepository hotelRepository;

  @Override
  public FoodItemDto addItemToHotel(Long hotelId, FoodItemDto foodItem) {
    Hotel hotel =
        hotelRepository
            .findById(hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));
    hotel.addFoodItem(
        new FoodItem(
            foodItem.getItemName(), foodItem.getPrice(), foodItem.getDescription(), hotel));
    hotelRepository.save(hotel);
    return foodItem;
  }

  @Override
  @Transactional(readOnly = true)
  public List<FoodItemDto> getAllItemsByHotelId(Long hotelId) {
    hotelRepository
        .findById(hotelId)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));
    return itemRepository.findByHotel_HotelId(hotelId).stream()
        .map(
            fooditems ->
                new FoodItemDto(
                    fooditems.getItemId(),
                    fooditems.getItemName(),
                    fooditems.getPrice(),
                    fooditems.getDescription(),
                    hotelId))
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public FoodItemDto findByItemID(Long hotelId, Long itemID) {
    hotelRepository
        .findById(hotelId)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));
    FoodItem foodItem =
        itemRepository
            .findByItemIdAndHotel_HotelId(itemID, hotelId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Item with id " + itemID + " not found for hotel " + hotelId));

    return new FoodItemDto(
        foodItem.getItemId(),
        foodItem.getItemName(),
        foodItem.getPrice(),
        foodItem.getDescription(),
        hotelId);
  }

  @Override
  @Transactional(readOnly = true)
  public FoodItemDto findByItemName(Long hotelId, String itemName) {
    hotelRepository
        .findById(hotelId)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));
    FoodItem foodItem =
        itemRepository
            .findByItemNameAndHotel_HotelId(itemName, hotelId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Item '" + itemName + "' not found for hotel " + hotelId));
    return new FoodItemDto(
        foodItem.getItemId(),
        foodItem.getItemName(),
        foodItem.getPrice(),
        foodItem.getDescription(),
        hotelId);
  }

  @Override
  public FoodItemDto updateItem(Long hotelId, Long itemId, FoodItemDto foodItem) {
    hotelRepository
        .findById(hotelId)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));
    FoodItem updateItem =
        itemRepository
            .findByItemIdAndHotel_HotelId(itemId, hotelId)
            .orElseThrow(() -> new ResourceNotFoundException("Item Id " + itemId + " not found"));
    if (foodItem.getItemName() != null && !foodItem.getItemName().isBlank())
      updateItem.setItemName(foodItem.getItemName());
    if (foodItem.getPrice() != null) updateItem.setPrice(foodItem.getPrice());
    if (foodItem.getDescription() != null && !foodItem.getDescription().isBlank())
      updateItem.setDescription(foodItem.getDescription());

    itemRepository.save(updateItem);

    return new FoodItemDto(
        updateItem.getItemId(),
        updateItem.getItemName(),
        updateItem.getPrice(),
        updateItem.getDescription(),
        hotelId);
  }

  @Override
  public void deleteItem(Long hotelId, Long itemId) {
    hotelRepository
        .findById(hotelId)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));
    FoodItem item =
        itemRepository
            .findByItemIdAndHotel_HotelId(itemId, hotelId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Item with id " + itemId + " not found for hotel " + hotelId));

    itemRepository.delete(item);
  }
}
