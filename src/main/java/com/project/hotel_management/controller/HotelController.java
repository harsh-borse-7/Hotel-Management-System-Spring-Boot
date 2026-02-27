package com.project.hotel_management.controller;

import com.project.hotel_management.dto.HotelDto;
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
  public ResponseEntity<HotelDto> addHotel(@RequestBody HotelDto hotel) {
    return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.addHotel(hotel));
  }

  @GetMapping
  public ResponseEntity<List<HotelDto>> getAllHotels(
      @RequestParam(required = false) Long hotelId,
      @RequestParam(required = false) String hotelName) {
    return ResponseEntity.ok(hotelService.getHotels(hotelId, hotelName));
  }

  @PatchMapping("/{hotelId}")
  public ResponseEntity<HotelDto> updateHotel(
      @PathVariable Long hotelId, @RequestBody HotelDto hotelDetails) {
    return ResponseEntity.ok(hotelService.updateHotel(hotelId, hotelDetails));
  }

  @DeleteMapping("/{hotelId}")
  public ResponseEntity<Void> deleteHotel(@PathVariable Long hotelId) {
    hotelService.deleteHotel(hotelId);
    return ResponseEntity.noContent().build();
  }
}
