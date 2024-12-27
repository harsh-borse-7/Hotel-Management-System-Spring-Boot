package com.project.hotel_management.repository;

import com.project.hotel_management.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByItemName(String itemName);
    List<Menu> findAllById(Iterable<Long> ids);
}
