package com.project.hotel_management.repository;

import com.project.hotel_management.model.FoodItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<FoodItem, Long> {
  List<FoodItem> findByHotel_HotelId(Long hotelId);

  Optional<FoodItem> findByItemIdAndHotel_HotelId(Long itemId, Long hotelId);

  Optional<FoodItem> findByItemNameAndHotel_HotelId(String itemName, Long hotel_hotelId);
}
