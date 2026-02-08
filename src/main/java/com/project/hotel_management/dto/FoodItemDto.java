package com.project.hotel_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FoodItemDto {
  private Long itemId;
  private String itemName;
  private Long price;
  private String description;
  private Long hotelId;
}
