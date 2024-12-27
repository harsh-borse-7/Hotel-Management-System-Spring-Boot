package com.project.hotel_management.service;

import com.project.hotel_management.model.Menu;

import java.util.List;

public interface MenuService {

    Menu addItem(Menu menu);
    Menu updateMenu(String itemName, Menu menu);
    List<Menu> getAllMenu();
    Menu findByItemID(long itemID);
    Menu findByItemName(String itemName);
    void deleteItem(String itemName);

}
