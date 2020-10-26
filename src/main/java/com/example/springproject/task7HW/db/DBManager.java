package com.example.springproject.task7HW.db;

import java.util.ArrayList;

public class DBManager {
    public static ArrayList<ShopItem> itemsList = new ArrayList<>();

    static {
        itemsList.add(new ShopItem(1L,"Iphone 12 Pro Max", "Iphone 12 pro max, 512 GB", 2499, 10, 5, "https://www.chiptrolls.com/Images/20200513231424.png"));
        itemsList.add(new ShopItem(2L,"MEIZU 16 Pro", "MEIZU 16 pro, screen 6.2, 2332х1080 pixels", 599, 12, 4, "data:image/jpeg;base64,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"));
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
