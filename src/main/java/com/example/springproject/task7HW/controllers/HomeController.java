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
        ArrayList<ShopItems> items = (ArrayList<ShopItems>) itemService.getAllItems();
        ArrayList<ShopItems> items1 = new ArrayList<>();
        ArrayList<ShopItems> items2 = new ArrayList<>();
        for (ShopItems shopItem : items) {
            if (shopItem.isInTopPage()) {
                items1.add(shopItem);
            } else {
                items2.add(shopItem);
            }
        }
        model.addAttribute("IsTopItems", items1);
        model.addAttribute("items", items2);
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
    ) {


        itemService.addItem(new ShopItems(null, name, description, price, star, small_picture, large_picture, Date.valueOf(date), false));
        return "redirect:/";
    }

    @GetMapping(value = "/view/{idshka}")
    public String view(Model model, @PathVariable(name = "idshka") Long id) {
//        Items item = DBManager.getItem(id);
        ShopItems item = itemService.getItem(id);
        model.addAttribute("item", item);
        return "view";
    }

    @GetMapping(value = "/details/{idshka}")
    public String details(Model model, @PathVariable(name = "idshka") Long id) {
//        Items item = DBManager.getItem(id);
        ShopItems item = itemService.getItem(id);
        model.addAttribute("item", item);
        return "details";
    }

    @PostMapping(value = "/deleteItem")
    public String deleteItem(@RequestParam(name = "id", defaultValue = "0") Long id) {
//        DBManager.addItem(new Items(null, name,price));
//        itemService.addItem(new ShopItems(null, name,price,amount));
        System.out.println("keldi");
        ShopItems item = itemService.getItem(id);
        if (item != null) {
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
            @RequestParam(name = "isTop", defaultValue = "0") boolean isTop) {
//        DBManager.addItem(new Items(null, name,price));
//        itemService.addItem(new ShopItems(null, name,price,amount));
        System.out.println("save-ke keldi");
        ShopItems item = itemService.getItem(id);
        if (item != null) {
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

    @GetMapping(value = "/searchItem")
    public String searchItem(Model model, @RequestParam(name = "search") String name) {
        System.out.println("name: " + name);
        List<ShopItems> items = itemService.getItemsByNamePriceAsc(name);
//        Items item = DBManager.getItem(id);
        if (items != null) {
            model.addAttribute("name", name);
            model.addAttribute("items", items);
            return "search";
        }
        return "redirect:/";

    }

    @GetMapping(value = "/search")
    public String search(Model model,
                         @RequestParam(name = "name", defaultValue = "") String name,
                         @RequestParam(name = "priceFrom", defaultValue = "0") String priceFrom,
                         @RequestParam(name = "priceTo", defaultValue = "1111111") String priceTo,
                         @RequestParam(name = "order", defaultValue = "asc") String order) {
        System.out.println("name: " + name);

        List<ShopItems> items2 = itemService.getItemsByNamePriceAsc(name);
        List<ShopItems> items ;
//        Items item = DBManager.getItem(id);
        if(priceFrom.equals("0")&&priceTo.equals("1111111")){
            if(order.equals("asc")){
                items = itemService.getItemsByNamePriceAsc(name);
            }
            else {
                items = itemService.getItemsByNamePriceDesc(name);
            }
        }
        else {
            if(order.equals("asc")){
                items = itemService.getItemsByNameAndPriceBetweenOrderByPriceAsc(name, Double.parseDouble(priceFrom), Double.parseDouble(priceTo));
            }
            else {
                items = itemService.getItemsByNameAndPriceBetweenOrderByPriceAsc(name, Double.parseDouble(priceFrom), Double.parseDouble(priceTo));

            }
        }
//        if (items != null) {'
        System.out.println("asdasdasds");
        System.out.println(items);
            model.addAttribute("items", items);
            model.addAttribute("name", name);
            model.addAttribute("order", order);
            model.addAttribute("price_from", priceFrom);
            model.addAttribute("price_to", priceTo);
            return "search";
//        }
//        return "redirect:/";

    }
    @GetMapping(value = "/filterAsc")
    public String filteringAsc(Model model,
                               @RequestParam(name = "search_name") String name,
                               @RequestParam(name = "search_price_from", defaultValue = "0") double price_from,
                               @RequestParam(name = "search_price_to", defaultValue = "-1") double price_to
    ) {
        if (price_from == (0) && price_to == (-1)) {
            List<ShopItems> items = itemService.getItemsByNamePriceAsc(name);
            if(items!=null){
                model.addAttribute("name", name);
                model.addAttribute("items", items);
            }
        }
        else {
            List<ShopItems> items2 = itemService.getItemsByNameAndPriceBetweenOrderByPriceAsc(name,price_from,price_to);
            if(items2!=null){
                model.addAttribute("name", name);
                model.addAttribute("items", items2);
            }
        }
        return "search";
    }

    @GetMapping(value = "/filterDesc")
    public String filterDesc(Model model,
                               @RequestParam(name = "search_name") String name,
                               @RequestParam(name = "search_price_from", defaultValue = "0") double price_from,
                               @RequestParam(name = "search_price_to", defaultValue = "-1") double price_to
    ) {
        if (price_from == (0) && price_to == (-1)) {
            List<ShopItems> items = itemService.getItemsByNamePriceDesc(name);
            if(items!=null){
                model.addAttribute("name", name);
                model.addAttribute("items", items);
            }
        }
        else {
            List<ShopItems> items2 = itemService.getItemsByNameAndPriceBetweenOrderByPriceDesc(name,price_from,price_to);
            if(items2!=null){
                model.addAttribute("name", name);
                model.addAttribute("items", items2);
            }
        }
        return "search";
    }

}
