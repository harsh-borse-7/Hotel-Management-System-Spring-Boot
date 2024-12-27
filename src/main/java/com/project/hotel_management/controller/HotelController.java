package com.project.hotel_management.controller;

import com.project.hotel_management.dto.HotelResponse;
import com.project.hotel_management.exception.ResourceNotFoundException;
import com.project.hotel_management.model.Hotel;
import com.project.hotel_management.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel-management/hotels")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel){
        Hotel addHotel = hotelService.addHotel(hotel);
        return new ResponseEntity<>(addHotel, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels() {
        List<HotelResponse> hotels = hotelService.getHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("/id/{hotelID}")
    public ResponseEntity<HotelResponse> findByID(@PathVariable long hotelID){
        try {
            HotelResponse hotel = hotelService.findByHotelID(hotelID);
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/hotelname/{hotelName}")
    public ResponseEntity<HotelResponse> findByHotelName(@PathVariable String hotelName){
        try {
            HotelResponse hotel = hotelService.findByHotelName(hotelName);
           return new ResponseEntity<>(hotel,HttpStatus.OK);
        }catch (ResourceNotFoundException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{hotelName}")
    public ResponseEntity<Hotel> updateUser(@PathVariable String hotelName, @RequestBody Hotel hotelDetails) {
        try {
            Hotel updatedHotel = hotelService.updateHotel(hotelName, hotelDetails);
            return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{hotelName}")
    public ResponseEntity<Void> deleteUser(@PathVariable String hotelName) {
        try {
            hotelService.deleteHotel(hotelName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
