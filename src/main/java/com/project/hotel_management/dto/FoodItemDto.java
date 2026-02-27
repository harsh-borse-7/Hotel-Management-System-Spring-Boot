package com.project.hotel_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FoodItemDto {
  private Long foodItemId;
  private String foodItemName;
  private Long price;
  private String description;
  private Long hotelId;
}
