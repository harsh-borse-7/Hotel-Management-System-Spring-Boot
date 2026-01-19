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
  private Long itemId;

  @Column(nullable = false)
  private String itemName;

  @Column(nullable = false)
  private Long price;

  private String description;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Hotel hotel;

  protected FoodItem() {}

  public FoodItem(String itemName, Long price, String description, Hotel hotel) {
    this.itemName = itemName;
    this.price = price;
    this.description = description;
    this.hotel = hotel;
  }
}
