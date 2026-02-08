package com.project.hotel_management.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HotelDto {

  private Long hotelId;
  private String hotelName;
  private String location;
  private List<FoodItemDto> foodItems;
}
