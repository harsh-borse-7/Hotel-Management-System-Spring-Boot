package com.project.hotel_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @Column(name = "item_id")
    private long itemID;

    @Column(name = "item_name", unique = true)
    private String itemName;

    @Column(name = "base_price")
    private long basePrice;

    @Column(name = "description")
    private String description;

    public Menu() {}

    public Menu(long itemID, String itemName, long basePrice, String description) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.basePrice = basePrice;
        this.description = description;
    }

    public long getitemID() {
        return itemID;
    }

    public void setitemID(long itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getbasePrice() {
        return basePrice;
    }

    public void setbasePrice(long basePrice) {
        this.basePrice = basePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
