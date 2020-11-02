package com.example.springproject.task7HW.db;

import java.util.ArrayList;

public class DBManager {
    public static ArrayList<ShopItem> itemsList = new ArrayList<>();

    static {
        itemsList.add(new ShopItem(1L,"Iphone 12 Pro Max", "Iphone 12 pro max, 512 GB", 2499, 10, 5, "https://www.chiptrolls.com/Images/20200513231424.png"));
        itemsList.add(new ShopItem(2L,"Samsung Galaxy A71", "Samsung Galaxy A71 6/128Gb black 2332х1080 pixels", 599, 12, 4, "https://cdn-kaspi.kz/medias/sys_master/images/images/ha7/h28/11693148897310/samsung-galaxy-a71-6-128gb-cernyj-100143638-1.png"));
        itemsList.add(new ShopItem(3L,"SAMSUNG GALAXY 20", "Samsung galaxy 20, galaxy fold 2", 799, 8, 3, "https://www.notebookcheck.net/uploads/tx_nbc2/4_3_Teaser_Samsung_Galaxy_Note20_Ultra_5G_SM-N986B_MysticWhite.jpg"));
        itemsList.add(new ShopItem(4L,"XIAOMI REDMI Note 9 pro", "XIAOMI Redmi note 9 pro jaksy telephon", 250, 15, 5, "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQa-OAl1rvW3c38PwAgW7uhmc4f0wre_oBInw&usqp=CAU"));
    }

    static Long id = 5L;

    public static ArrayList<ShopItem> getAllItems(){
        return itemsList;
    }

    static ShopItem getItem(Long id){
        for(ShopItem item: itemsList){
            if(item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

    public static void addItem(ShopItem item) {
        item.setId(id);
        itemsList.add(item);
        id++;
    }

    public static void deleteItem(Long id) {
        for (int i = 0; i < itemsList.size(); i++) {
            if (itemsList.get(i).getId().equals(id)) {
                itemsList.remove(i);
                break;
            }
        }
    }

    public static void saveItem(ShopItem item) {
        for (ShopItem items : itemsList) {
            if (items.getId() == item.getId()) {
                items.setName(item.getName());
                items.setDescription((item.getDescription()));
                items.setPrice(item.getPrice());
                items.setAmount(item.getAmount());
                items.setStars(item.getStars());
                items.setPictureUrl(item.getPictureUrl());
            }
        }
    }
}
