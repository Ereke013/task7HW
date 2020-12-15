package com.example.springproject.task7HW.services;

import com.example.springproject.task7HW.db.ShopItem;
import com.example.springproject.task7HW.entities.*;

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
    List<ShopItems> getAllByCategories(Categories category);

    Country addCountry(Country country);
    List<Country> getAllCountry();
    Country getCountry(Long id);
    void deleteCountry(Country country);
    Country saveCountry(Country country);

    List<Brands> getAllBrands();
    Brands saveBrand(Brands brand);
    Brands getBrand(Long id);
    void deleteBrand(Brands brand);
    Brands addBrand(Brands brand);

    List<Categories> getAllCategories();
    Categories saveCategory(Categories category);
    Categories getCategory(Long id);
    void deleteCategory(Categories category);
    Categories addCategory(Categories category);

    List<Pictures> getAllPictures();
    Pictures savePicture(Pictures picture);
    Pictures getPicture(Long id);
    void deletePicture(Pictures picture);
    Pictures addPicture(Pictures picture);

    List<Pictures> findAllByItems(ShopItems items);
    List<Pictures> addItemListPic(List<Pictures> pictures);
    List<Pictures> getItemPictures(ShopItems item);

    List<Baskets> getAllBasket();
    Baskets saveBasket(Baskets basket);
    Baskets getBaskets(Long id);
    void deleteBaskets(Baskets basket);
    Baskets addBaskets(Baskets basket);
}
