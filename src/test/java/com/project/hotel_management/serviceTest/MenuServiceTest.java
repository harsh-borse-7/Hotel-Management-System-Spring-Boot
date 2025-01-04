package com.project.hotel_management.serviceTest;

import com.project.hotel_management.exception.ResourceConflictException;
import com.project.hotel_management.exception.ResourceNotFoundException;
import com.project.hotel_management.model.Menu;
import com.project.hotel_management.repository.MenuRepository;
import com.project.hotel_management.service.impl.MenuServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @InjectMocks
    private MenuServiceImpl menuServiceImpl;

    @Mock
    private MenuRepository menuRepository;

    private Menu menu1;
    private Menu menu2;

    @BeforeEach
    public void setUp() {
        menu1 = new Menu(1L, "Dish1", 10, "Description1");
        menu2 = new Menu(2L, "Dish2", 12, "Description2");
    }

    @Test
    public void testAddItem_Success() {
        when(menuRepository.findByItemName("Dish1")).thenReturn(Optional.empty());
        when(menuRepository.save(menu1)).thenReturn(menu1);

        Menu savedMenu = menuServiceImpl.addItem(menu1);

        assertNotNull(savedMenu);
        assertEquals("Dish1", savedMenu.getItemName());
        verify(menuRepository, times(1)).save(menu1);
    }

    @Test
    public void testAddItem_ItemAlreadyExists() {
        when(menuRepository.findByItemName("Dish1")).thenReturn(Optional.of(menu1));

        assertThrows(ResourceConflictException.class, () -> menuServiceImpl.addItem(menu1));
    }

    @Test
    public void testUpdateMenu_ItemNotFound() {
        when(menuRepository.findByItemName("Dish1")).thenReturn(Optional.empty());

        Menu updatedMenu = new Menu(1L, "Dish1", 15, "Updated Description");

        assertThrows(NoSuchElementException.class, () -> menuServiceImpl.updateMenu("Dish1", updatedMenu));
    }

    @Test
    public void testGetAllMenu() {
        when(menuRepository.findAll()).thenReturn(Arrays.asList(menu1, menu2));

        List<Menu> menuList = menuServiceImpl.getAllMenu();

        assertNotNull(menuList);
        assertEquals(2, menuList.size());
        verify(menuRepository, times(1)).findAll();
    }

    @Test
    public void testFindByItemID_Success() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu1));

        Menu menu = menuServiceImpl.findByItemID(1L);

        assertNotNull(menu);
        assertEquals(1L, menu.getitemID());
        verify(menuRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByItemID_ItemNotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuServiceImpl.findByItemID(1L));
    }

    @Test
    public void testFindByItemName_Success() {
        when(menuRepository.findByItemName("Dish1")).thenReturn(Optional.of(menu1));

        Menu menu = menuServiceImpl.findByItemName("Dish1");

        assertNotNull(menu);
        assertEquals("Dish1", menu.getItemName());
        verify(menuRepository, times(1)).findByItemName("Dish1");
    }

    @Test
    public void testFindByItemName_ItemNotFound() {
        when(menuRepository.findByItemName("Dish1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuServiceImpl.findByItemName("Dish1"));
    }

    @Test
    public void testDeleteItem_Success() {
        when(menuRepository.findByItemName("Dish1")).thenReturn(Optional.of(menu1));

        menuServiceImpl.deleteItem("Dish1");

        verify(menuRepository, times(1)).delete(menu1);
    }

    @Test
    public void testDeleteItem_ItemNotFound() {
        when(menuRepository.findByItemName("Dish1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> menuServiceImpl.deleteItem("Dish1"));
    }
}
