package com.project.hotel_management.controller;

import com.project.hotel_management.exception.ResourceNotFoundException;
import com.project.hotel_management.model.Hotel;
import com.project.hotel_management.model.Menu;
import com.project.hotel_management.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel-management/menu")
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<Menu> addItemInMenu(@RequestBody Menu menu){
        Menu addItem = menuService.addItem(menu);
        return new ResponseEntity<>(addItem, HttpStatus.CREATED);
    }

    @PutMapping("/{itemName}")
    public ResponseEntity<Menu> updateItem(@PathVariable String itemName, @RequestBody Menu itemDetails){
        try {
            Menu updatedItem = menuService.updateMenu(itemName, itemDetails);
            return new ResponseEntity<>(itemDetails, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllItems(){
        List<Menu> menu = menuService.getAllMenu();
        return new ResponseEntity<>(menu,HttpStatus.OK);
    }

    @GetMapping("/itemname/{itemName}")
    public ResponseEntity<Menu> getItemByName(@PathVariable String itemName){
        try {
            Menu item = menuService.findByItemName(itemName);
            return new ResponseEntity<>(item,HttpStatus.OK);
        }catch (ResourceNotFoundException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/itemid/{itemID}")
    public ResponseEntity<Menu> getItemById(@PathVariable long itemID){
        try {
            Menu item = menuService.findByItemID(itemID);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{itemName}")
    public ResponseEntity<Menu> deleteItem(@PathVariable String itemName){
        try {
            menuService.deleteItem(itemName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
