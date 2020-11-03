package com.example.springproject.task7HW.repositories;

import com.example.springproject.task7HW.entities.ShopItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopItemRepository extends JpaRepository<ShopItems, Long> {

    List<ShopItems> findAllByPriceGreaterThan(double price);
    List<ShopItems> findAllByInTopPageEquals(boolean isTop);
    ShopItems findByIdAndPriceGreaterThan(Long id, double price);
}
