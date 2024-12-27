package com.project.hotel_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @Column(name = "hotel_id")
    private long hotelID;

    @Column(name = "hotel_name",unique = true)
    private String hotelName;

    @Column(name = "location")
    private String location;

    @Column(name = "multiplier")
    private Double multiplier;


    @Column(name = "item_ids")
    private String itemIDs;

    public Hotel(){}

    public Hotel(long hotelID, String hotelName, String location, Double multiplier, String itemIDs) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.location = location;
        this.multiplier = multiplier;
        this.itemIDs = itemIDs;
    }

    public long getHotelID() {
        return hotelID;
    }

    public void setHotelID(long hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public String getItemIDs() {
        return itemIDs;
    }

    public void setItemIDs(String itemIDs) {
        this.itemIDs = itemIDs;
    }

}
