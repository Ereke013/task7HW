package com.example.springproject.task7HW.controllers;


import com.example.springproject.task7HW.db.DBManager;
import com.example.springproject.task7HW.db.ShopItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class HomeController {
    @GetMapping(value = "/")
    public String index(Model model) {
        ArrayList<ShopItem> items = DBManager.getAllItems();
        model.addAttribute("items", items);
        return "index";
    }

    @PostMapping(value = "/addItem")
    public String addItem(@RequestParam(name = "item_name", defaultValue = "No Name") String name,
                          @RequestParam(name = "item_description", defaultValue = "No Description") String description,
                          @RequestParam(name = "item_price", defaultValue = "0") int price,
                          @RequestParam(name = "item_amount", defaultValue = "0") int amount,
                          @RequestParam(name = "item_star", defaultValue = "0") int star,
                          @RequestParam(name = "item_picture", defaultValue = "data:image/jpeg;base64,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") String picture
                          ){

        DBManager.addItem(new ShopItem(null, name,description,price,amount,star,picture));
        return"redirect:/";
    }
}
