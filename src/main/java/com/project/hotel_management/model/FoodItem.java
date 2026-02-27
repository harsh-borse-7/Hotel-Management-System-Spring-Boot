package com.project.hotel_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FoodItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long FoodItemId;

  @Column(nullable = false)
  private String foodItemName;

  @Column(nullable = false)
  private Long price;

  private String description;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Hotel hotel;

  protected FoodItem() {}

  public FoodItem(String foodItemName, Long price, String description, Hotel hotel) {
    this.foodItemName = foodItemName;
    this.price = price;
    this.description = description;
    this.hotel = hotel;
  }
}
