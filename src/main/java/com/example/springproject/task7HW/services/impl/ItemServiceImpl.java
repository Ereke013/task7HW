package com.example.springproject.task7HW.services.impl;

import com.example.springproject.task7HW.entities.ShopItems;
import com.example.springproject.task7HW.repositories.ShopItemRepository;
import com.example.springproject.task7HW.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ShopItemRepository shopItemRepository;

    @Override
    public ShopItems addItem(ShopItems items) {
        return shopItemRepository.save(items);
    }

    @Override
    public List<ShopItems> getAllItems() {
        return shopItemRepository.findAllByPriceGreaterThan(0);
    }

    @Override
    public ShopItems getItem(Long id) {
        return shopItemRepository.findByIdAndPriceGreaterThan(id, 0);
    }

    @Override
    public void deleteItem(ShopItems items) {
        shopItemRepository.delete(items);
    }

    @Override
    public ShopItems saveItem(ShopItems items) {
        return shopItemRepository.save(items);
    }

    @Override
    public List<ShopItems> getItemsInTop(boolean top) {
        return shopItemRepository.findAllByInTopPageEquals(top);
    }

    @Override
    public List<ShopItems> getItemsByNamePriceAsc(String name) {
        return shopItemRepository.findAllByNameContainingOrderByPriceAsc(name);
    }

    @Override
    public List<ShopItems> getItemsByNamePriceDesc(String name) {
        return shopItemRepository.findAllByNameContainingOrderByPriceDesc(name);
    }

    @Override
    public List<ShopItems> getItemsByNameAndPriceBetweenOrderByPriceDesc(String name, double price1, double price2) {
        return shopItemRepository.findAllByNameContainingAndPriceIsBetweenOrderByPriceDesc(name, price1, price2);
    }

    @Override
    public List<ShopItems> getItemsByNameAndPriceBetweenOrderByPriceAsc(String name, double price1, double price2) {
        return shopItemRepository.findAllByNameContainingAndPriceIsBetweenOrderByPriceAsc(name, price1, price2);
    }
}
