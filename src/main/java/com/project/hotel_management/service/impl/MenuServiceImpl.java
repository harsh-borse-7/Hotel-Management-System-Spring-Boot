package com.project.hotel_management.service.impl;

import com.project.hotel_management.exception.ResourceConflictException;
import com.project.hotel_management.exception.ResourceNotFoundException;
import com.project.hotel_management.model.Menu;
import com.project.hotel_management.repository.MenuRepository;
import com.project.hotel_management.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    @Override
    public Menu addItem(Menu menu) {
        Optional<Menu> findItem = menuRepository.findByItemName(menu.getItemName());
        if (findItem.isPresent()) {
            throw new ResourceConflictException("ItemName " + menu.getItemName() + " already exists");
        }
        return menuRepository.save(menu);
    }

    @Override
    public Menu updateMenu(String itemName, Menu menu) {
        Optional<Menu> itemOptional = menuRepository.findByItemName(itemName);
        if(itemOptional!=null){
            Menu updateItem = itemOptional.get();
            updateItem.setbasePrice(menu.getbasePrice());
            updateItem.setDescription(menu.getDescription());
            return menuRepository.save(updateItem);
        }
        else{
            throw new ResourceNotFoundException("Item name " + itemName + " not found");
        }
    }

    @Override
    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    @Override
    public Menu findByItemID(long itemID) {
        return menuRepository.findById(itemID)
                .orElseThrow(() -> new ResourceNotFoundException("Item Id "+ itemID + " not found"));

    }

    @Override
    public Menu findByItemName(String itemName) {
        return menuRepository.findByItemName(itemName)
                .orElseThrow(() -> new ResourceNotFoundException("Item name  " + itemName + " not found"));

    }

    @Override
    public void deleteItem(String itemName) {
        Menu item= menuRepository.findByItemName(itemName)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + itemName));
        menuRepository.delete(item);
    }
}
