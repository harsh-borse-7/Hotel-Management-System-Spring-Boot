package com.project.hotel_management.controller;

import com.project.hotel_management.model.Hotel;
import com.project.hotel_management.service.HotelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {

  private final HotelService hotelService;

  @PostMapping
  public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
    return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.addHotel(hotel));
  }

  @GetMapping
  public ResponseEntity<List<Hotel>> getAllHotels() {
    return ResponseEntity.ok(hotelService.getHotels());
  }

  @GetMapping("/{hotelId}")
  public ResponseEntity<Hotel> findByID(@PathVariable Long hotelId) {
    return ResponseEntity.ok(hotelService.findByHotelID(hotelId));
  }

  @GetMapping("/by-name")
  public ResponseEntity<Hotel> findByHotelName(@RequestParam String name) {
    return ResponseEntity.ok(hotelService.findByHotelName(name));
  }

  @PutMapping("/{hotelId}")
  public ResponseEntity<Hotel> updateHotel(
      @PathVariable Long hotelId, @RequestBody Hotel hotelDetails) {
    return ResponseEntity.ok(hotelService.updateHotel(hotelId, hotelDetails));
  }

  @DeleteMapping("/{hotel_id}")
  public ResponseEntity<Void> deleteHotel(@PathVariable Long hotel_id) {
    hotelService.deleteHotel(hotel_id);
    return ResponseEntity.noContent().build();
  }
}
