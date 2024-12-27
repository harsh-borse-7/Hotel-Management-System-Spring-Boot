package com.project.hotel_management.dto;

import com.project.hotel_management.model.Menu;

import java.util.List;

public class HotelResponse {
    private Long hotelID;
    private String hotelName;
    private String location;
    private Double multiplier;
    private List<Object> items;

    // Private constructor
    private HotelResponse(Builder builder) {
        this.hotelID = builder.hotelID;
        this.hotelName = builder.hotelName;
        this.location = builder.location;
        this.multiplier = builder.multiplier;
        this.items = builder.items;
    }

    // Getters (Optional, but good practice)
    public Long getHotelID() {
        return hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getLocation() {
        return location;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public List<Object> getItems() {
        return items;
    }

    // Static Builder class
    public static class Builder {
        private Long hotelID;
        private String hotelName;
        private String location;
        private Double multiplier;
        private List<Object> items;

        public Builder hotelID(Long hotelID) {
            this.hotelID = hotelID;
            return this;
        }

        public Builder hotelName(String hotelName) {
            this.hotelName = hotelName;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder multiplier(Double multiplier) {
            this.multiplier = multiplier;
            return this;
        }

        public Builder items(List<Object> items) {
            this.items = items;
            return this;
        }

        public HotelResponse build() {
            return new HotelResponse(this);
        }
    }
}
