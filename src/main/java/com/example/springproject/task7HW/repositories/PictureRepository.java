package com.example.springproject.task7HW.repositories;

import com.example.springproject.task7HW.entities.Brands;
import com.example.springproject.task7HW.entities.Categories;
import com.example.springproject.task7HW.entities.Pictures;
import com.example.springproject.task7HW.entities.ShopItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepository extends JpaRepository<Pictures, Long> {
    List<Pictures> findAllByItems(ShopItems items);
}
