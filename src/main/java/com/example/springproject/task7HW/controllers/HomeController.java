package com.example.springproject.task7HW.controllers;


import com.example.springproject.task7HW.db.DBManager;
import com.example.springproject.task7HW.db.ShopItem;
import com.example.springproject.task7HW.entities.ShopItems;
import com.example.springproject.task7HW.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/")
    public String index(Model model) {
        List<ShopItems> items = itemService.getAllItems();
//        List<ShopItems> items1 = null;
//        for(ShopItems shopItem:items){
//            if(shopItem.isInTopPage()) {
//                items1.add(0, shopItem);
//            }
//            else {
//                items1.add(shopItem);
//            }
//        }
        model.addAttribute("items", items);
        return "index";
    }

    @PostMapping(value = "/addItem")
    public String addItem(@RequestParam(name = "item_name", defaultValue = "No Name") String name,
                          @RequestParam(name = "item_description", defaultValue = "No Description") String description,
                          @RequestParam(name = "item_price", defaultValue = "0") int price,
                          @RequestParam(name = "item_star", defaultValue = "0") int star,
                          @RequestParam(name = "item_date", defaultValue = "1991-02-02") String date,
                          @RequestParam(name = "item_smallPic", defaultValue = "https://www.freeiconspng.com/thumbs/no-image-icon/no-image-icon-6.png") String small_picture,
                          @RequestParam(name = "item_largePic", defaultValue = "https://www.tellerreport.com/images/no-image.png") String large_picture
                          ){


        itemService.addItem(new ShopItems(null, name,description,price,star,small_picture, large_picture, Date.valueOf(date), false));
        return"redirect:/";
        }

    @GetMapping(value = "/view/{idshka}")
    public String view( Model model, @PathVariable(name = "idshka") Long id){
//        Items item = DBManager.getItem(id);
        ShopItems item = itemService.getItem(id);
        model.addAttribute("item", item);
        return "view";
    }

    @GetMapping(value = "/details/{idshka}")
    public String details( Model model, @PathVariable(name = "idshka") Long id){
//        Items item = DBManager.getItem(id);
        ShopItems item = itemService.getItem(id);
        model.addAttribute("item", item);
        return "details";
    }

    @PostMapping(value = "/deleteItem")
    public String deleteItem(@RequestParam(name = "id", defaultValue = "0") Long id){
//        DBManager.addItem(new Items(null, name,price));
//        itemService.addItem(new ShopItems(null, name,price,amount));
        System.out.println("keldi");
        ShopItems item = itemService.getItem(id);
        if(item!=null){
            itemService.deleteItem(item);
        }
        return "redirect:/";
    }

    @PostMapping(value = "/saveItem")
    public String saveItem(
            @RequestParam(name = "id", defaultValue = "0") Long id,
                           @RequestParam(name = "item_name", defaultValue = "No Item") String name,
                           @RequestParam(name = "item_description", defaultValue = "No description") String description,
                           @RequestParam(name = "item_price", defaultValue = "0") double price,
                           @RequestParam(name = "item_star", defaultValue = "0") int stars,
                           @RequestParam(name = "item_smallPic", defaultValue = "https://www.freeiconspng.com/thumbs/no-image-icon/no-image-icon-6.png") String smallPic,
                           @RequestParam(name = "item_largePic", defaultValue = "https://tutaki.org.nz/wp-content/uploads/2019/04/no-image-1.png") String largePic,
                           @RequestParam(name = "isTop", defaultValue = "0") boolean isTop){
//        DBManager.addItem(new Items(null, name,price));
//        itemService.addItem(new ShopItems(null, name,price,amount));
        System.out.println("save-ke keldi");
        ShopItems item = itemService.getItem(id);
        if(item!=null){
            item.setName(name);
            item.setDescription(description);
            item.setPrice(price);
            item.setStars(stars);
            item.setSmallPicURL(smallPic);
            item.setLargePicURL(largePic);
            item.setInTopPage(isTop);
            itemService.saveItem(item);
        }
        return "redirect:/";
    }

}
