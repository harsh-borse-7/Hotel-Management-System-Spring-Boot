package com.project.hotel_management.dto;

public class ItemResponse {
    private Long itemID;
    private String itemName;
    private double price; // Updated price
    private String description;

    public ItemResponse(Long itemID, String itemName, double price, String description) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.price = price;
        this.description = description;
    }

    // Getters
    public Long getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
