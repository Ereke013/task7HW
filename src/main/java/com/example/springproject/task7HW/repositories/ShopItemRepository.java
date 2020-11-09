package com.example.springproject.task7HW.repositories;

import com.example.springproject.task7HW.db.ShopItem;
import com.example.springproject.task7HW.entities.Brands;
import com.example.springproject.task7HW.entities.ShopItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopItemRepository extends JpaRepository<ShopItems, Long> {

    List<ShopItems> findAllByPriceGreaterThan(double price);
    List<ShopItems> findAllByInTopPageEquals(boolean isTop);
    ShopItems findByIdAndPriceGreaterThan(Long id, double price);
    List<ShopItems> findAllByNameContainingOrderByPriceAsc(String name);
    List<ShopItems> findAllByNameContainingOrderByPriceDesc(String name);
    List<ShopItems>findAllByNameContainingAndPriceIsBetweenOrderByPriceAsc(String name,double price1,double price2);
    List<ShopItems>findAllByNameContainingAndPriceIsBetweenOrderByPriceDesc(String name,double price1,double price2);
    List<ShopItems> findAllByBrands(Brands brands);
    List<ShopItems> findAllByBrandsAndNameContainingOrderByPriceAsc(Brands brands, String name);
    List<ShopItems> findAllByBrandsAndNameContainingOrderByPriceDesc(Brands brands, String name);
    List<ShopItems> findAllByBrandsAndNameContainingAndPriceIsBetweenOrderByPriceAsc(Brands brands, String name, double price1, double price2);
    List<ShopItems> findAllByBrandsAndNameContainingAndPriceIsBetweenOrderByPriceDesc(Brands brands, String name, double price1, double price2);
    List<ShopItems> findAllByBrandsAndPriceIsBetweenOrderByPriceDesc(Brands brands, double price1, double price2);
}
