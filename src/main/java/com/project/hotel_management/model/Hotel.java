package com.project.hotel_management.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Hotel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long hotelId;

  @Column(nullable = false)
  private String hotelName;

  private String location;

  @OneToMany(
      mappedBy = "hotel",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<FoodItem> foodItems = new ArrayList<>();

  protected Hotel() {}

  public Hotel(String hotelName, String location) {
    this.hotelName = hotelName;
    this.location = location;
  }

  public void addFoodItem(FoodItem foodItem) {
    foodItems.add(foodItem);
    foodItem.setHotel(this);
  }
}
