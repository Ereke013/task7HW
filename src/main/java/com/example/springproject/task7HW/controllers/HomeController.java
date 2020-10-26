package com.example.springproject.task7HW.controllers;


import com.example.springproject.task7HW.db.DBManager;
import com.example.springproject.task7HW.db.ShopItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class HomeController {
    @GetMapping(value = "/")
    public String index(Model model) {
        ArrayList<ShopItem> items = DBManager.getAllItems();
        model.addAttribute("items", items);
        return "index";
    }
}
