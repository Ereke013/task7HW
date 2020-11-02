package com.example.springproject.task7HW.services;

import com.example.springproject.task7HW.entities.ShopItems;

import java.util.List;

public interface ItemService {
    ShopItems addItem(ShopItems items);
    List<ShopItems> getAllItems();
    ShopItems getItem(Long id);
    void deleteItem(ShopItems items);
    ShopItems saveItem(ShopItems items);
}
