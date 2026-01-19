package com.project.hotel_management.repository;

import com.project.hotel_management.model.Hotel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
  Optional<Hotel> findByHotelName(String hotelName);
}
