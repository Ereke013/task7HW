package com.example.springproject.task7HW.services;

import com.example.springproject.task7HW.entities.Brands;
import com.example.springproject.task7HW.entities.Country;
import com.example.springproject.task7HW.entities.ShopItems;

import java.util.List;

public interface ItemService {
    ShopItems addItem(ShopItems items);
    List<ShopItems> getAllItems();
    ShopItems getItem(Long id);
    void deleteItem(ShopItems items);
    ShopItems saveItem(ShopItems items);
    List<ShopItems> getItemsInTop(boolean top);
    List<ShopItems> getItemsByNamePriceAsc(String name);
    List<ShopItems> getItemsByNamePriceDesc(String name);
    List<ShopItems> getItemsByNameAndPriceBetweenOrderByPriceDesc(String name,double price1,double price2);
    List<ShopItems> getItemsByNameAndPriceBetweenOrderByPriceAsc(String name,double price1,double price2);
    List<ShopItems> getItemsByBrand(Brands brand);
    List<ShopItems> getItemsByBrandAndByNamePriceAsc(Brands brand, String name);
    List<ShopItems> getItemsByBrandAndByNamePriceDesc(Brands brand, String name);
    List<ShopItems> getItemsByBrandsAndByNameAndPriceBetweenOrderByPriceDesc(Brands brand, String name,double price1,double price2);
    List<ShopItems> getItemsByBrandsAndByNameAndPriceBetweenOrderByPriceAsc(Brands brand, String name,double price1,double price2);

    Country addCountry(Country country);
    List<Country> getAllCountry();
    Country getCountry(Long id);
    Country saveCountry(Country country);

    List<Brands> getAllBrands();
    Brands saveBrand(Brands brand);
    Brands getBrand(Long id);
    Brands addBrand(Brands brand);

}
