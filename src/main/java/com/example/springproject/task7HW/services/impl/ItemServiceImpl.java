package com.example.springproject.task7HW.services.impl;

import com.example.springproject.task7HW.entities.*;
import com.example.springproject.task7HW.repositories.*;
import com.example.springproject.task7HW.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ShopItemRepository shopItemRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private BrandsRepository brandsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private BasketsRepository basketsRepository;

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

    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public List<Country> getAllCountry() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountry(Long id) {
        return countryRepository.getOne(id);
    }

    @Override
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public List<Brands> getAllBrands() {
        return brandsRepository.findAll();
    }

    @Override
    public Brands saveBrand(Brands brand) {
        return brandsRepository.save(brand);
    }

    @Override
    public Brands getBrand(Long id) {
        return brandsRepository.getOne(id);
    }

    @Override
    public Brands addBrand(Brands brand) {
        return brandsRepository.save(brand);
    }

    @Override
    public List<ShopItems> getItemsByBrand(Brands brand) {
        return shopItemRepository.findAllByBrands(brand);
    }

    @Override
    public List<ShopItems> getItemsByBrandAndByNamePriceAsc(Brands brand, String name) {
        return shopItemRepository.findAllByBrandsAndNameContainingOrderByPriceAsc(brand,name);
    }

    @Override
    public List<ShopItems> getItemsByBrandAndByNamePriceDesc(Brands brand, String name) {
        return shopItemRepository.findAllByBrandsAndNameContainingOrderByPriceDesc(brand,name);
    }

    @Override
    public List<ShopItems> getItemsByBrandsAndByNameAndPriceBetweenOrderByPriceDesc(Brands brand, String name, double price1, double price2) {
        return shopItemRepository.findAllByBrandsAndNameContainingAndPriceIsBetweenOrderByPriceDesc(brand,name,price1,price2);
    }

    @Override
    public List<ShopItems> getItemsByBrandsAndByNameAndPriceBetweenOrderByPriceAsc(Brands brand, String name, double price1, double price2) {
        return shopItemRepository.findAllByBrandsAndNameContainingAndPriceIsBetweenOrderByPriceAsc(brand,name,price1,price2);
    }

    @Override
    public Categories addCategory(Categories category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Categories getCategory(Long id) {
        return categoryRepository.getOne(id);
    }

    @Override
    public Categories saveCategory(Categories category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<ShopItems> getAllByCategories(Categories category) {
        return shopItemRepository.findAllByCategories(category);
    }

    @Override
    public void deleteCountry(Country country) {
        countryRepository.delete(country);
    }

    @Override
    public void deleteBrand(Brands brand) {
        brandsRepository.delete(brand);
    }

    @Override
    public void deleteCategory(Categories category) {
        categoryRepository.delete(category);
    }

    @Override
    public List<Pictures> getAllPictures() {
        return pictureRepository.findAll();
    }

    @Override
    public Pictures savePicture(Pictures picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public Pictures getPicture(Long id) {
        return pictureRepository.getOne(id);
    }

    @Override
    public void deletePicture(Pictures picture) {
        pictureRepository.delete(picture);
    }

    @Override
    public Pictures addPicture(Pictures picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public List<Pictures> findAllByItems(ShopItems items) {
        return pictureRepository.findAllByItems(items);
    }

    @Override
    public List<Pictures> addItemListPic(List<Pictures> pictures) {
        return pictureRepository.saveAll(pictures);
    }

    @Override
    public List<Pictures> getItemPictures(ShopItems item) {
        return pictureRepository.findAllByItems(item);
    }

    @Override
    public List<Baskets> getAllBasket() {
        return basketsRepository.findAll();
    }

    @Override
    public Baskets saveBasket(Baskets basket) {
        return basketsRepository.save(basket);
    }

    @Override
    public Baskets getBaskets(Long id) {
        return basketsRepository.getOne(id);
    }

    @Override
    public void deleteBaskets(Baskets basket) {
        basketsRepository.delete(basket);
    }

    @Override
    public Baskets addBaskets(Baskets basket) {
        return basketsRepository.save(basket);
    }
}
