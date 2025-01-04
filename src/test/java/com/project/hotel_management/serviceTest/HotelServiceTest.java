package com.project.hotel_management.serviceTest;

import com.project.hotel_management.dto.HotelResponse;
import com.project.hotel_management.exception.ResourceConflictException;
import com.project.hotel_management.exception.ResourceNotFoundException;
import com.project.hotel_management.model.Hotel;
import com.project.hotel_management.model.Menu;
import com.project.hotel_management.repository.HotelRepository;
import com.project.hotel_management.repository.MenuRepository;
import com.project.hotel_management.service.impl.HotelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @InjectMocks
    private HotelServiceImpl hotelServiceImpl;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private MenuRepository menuRepository;

    private Hotel hotel1;
    private Hotel hotel2;

    @BeforeEach
    public void setUp() {
        hotel1 = new Hotel(101, "Royal", "Pune", 1.5, "1,2");
        hotel2 = new Hotel(102, "Luxury", "Mumbai", 2.0, "3,4");
    }

    @Test
    public void testGetHotels_Success() {
        when(hotelRepository.findAll()).thenReturn(Arrays.asList(hotel1, hotel2));

        Menu menuItem1 = new Menu(1L, "Dish1", 10, "Description1");
        Menu menuItem2 = new Menu(2L, "Dish2", 12, "Description2");
        Menu menuItem3 = new Menu(3L, "Dish3", 15, "Description3");
        Menu menuItem4 = new Menu(4L, "Dish4", 18, "Description4");

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menuItem1));
        when(menuRepository.findById(2L)).thenReturn(Optional.of(menuItem2));
        when(menuRepository.findById(3L)).thenReturn(Optional.of(menuItem3));
        when(menuRepository.findById(4L)).thenReturn(Optional.of(menuItem4));

        List<HotelResponse> hotelResponses = hotelServiceImpl.getHotels();

        assertNotNull(hotelResponses);
        assertEquals(2, hotelResponses.size());
        assertEquals("Royal", hotelResponses.get(0).getHotelName());
        assertEquals("Luxury", hotelResponses.get(1).getHotelName());

        verify(hotelRepository, times(1)).findAll();
        verify(menuRepository, times(4)).findById(anyLong());
    }

    @Test
    public void testGetHotels_ItemNotFound() {
        when(hotelRepository.findAll()).thenReturn(Arrays.asList(hotel1, hotel2));

        when(menuRepository.findById(1L)).thenReturn(Optional.of(new Menu(1L, "Dish1", 10, "Description1")));
        when(menuRepository.findById(4L)).thenReturn(Optional.of(new Menu(4L, "Dish4", 18, "Description4")));

        when(menuRepository.findById(2L)).thenReturn(Optional.empty());
        when(menuRepository.findById(3L)).thenReturn(Optional.empty());

        List<HotelResponse> hotelResponses = hotelServiceImpl.getHotels();

        assertNotNull(hotelResponses);
        assertEquals(2, hotelResponses.size());

        assertTrue(hotelResponses.get(0).getItems().stream()
                .anyMatch(item -> ((Map<?, ?>) item).containsKey("error")));
    }

    @Test
    public void testFindByHotelID_HotelNotFound() {
        when(hotelRepository.findById(101L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> hotelServiceImpl.findByHotelID(101L));
    }

    @Test
    public void testFindByHotelName_HotelNotFound() {
        when(hotelRepository.findByHotelName("NonExistentHotel")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> hotelServiceImpl.findByHotelName("NonExistentHotel"));
    }

    @Test
    public void testAddHotel_HotelAlreadyExists() {
        Hotel hotel = new Hotel(101, "Royal", "Pune", 1.5, "1,2");

        when(hotelRepository.findByHotelName("Royal")).thenReturn(Optional.of(hotel));

        assertThrows(ResourceConflictException.class, () -> hotelServiceImpl.addHotel(hotel));
    }

    @Test
    public void testUpdateHotel_HotelNotFound() {
        Hotel updatedHotel = new Hotel(101, "Updated Royal", "Pune", 1.8, "1,2");

        when(hotelRepository.findByHotelName("Updated Royal")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> hotelServiceImpl.updateHotel("Updated Royal", updatedHotel));
    }

    @Test
    public void testDeleteHotel_HotelNotFound() {
        when(hotelRepository.findByHotelName("Royal")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> hotelServiceImpl.deleteHotel("Royal"));
    }

    @Test
    public void testAddHotel_Success() {
        Hotel hotel = new Hotel(103, "Grand", "Delhi", 2.5, "5,6");

        when(hotelRepository.findByHotelName("Grand")).thenReturn(Optional.empty());
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Hotel savedHotel = hotelServiceImpl.addHotel(hotel);

        assertNotNull(savedHotel);
        assertEquals("Grand", savedHotel.getHotelName());
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    public void testUpdateHotel_Success() {
        Hotel updatedHotel = new Hotel(101, "Royal Updated", "Pune", 1.8, "2,3");

        when(hotelRepository.findByHotelName("Royal")).thenReturn(Optional.of(hotel1));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(updatedHotel);

        Hotel hotel = hotelServiceImpl.updateHotel("Royal", updatedHotel);

        assertNotNull(hotel);
        assertEquals("Royal Updated", hotel.getHotelName());
        assertEquals("Pune", hotel.getLocation());

        verify(hotelRepository, times(1)).save(any(Hotel.class));
    }

}
