package com.example.springproject.task7HW.controllers;


import com.example.springproject.task7HW.entities.*;
import com.example.springproject.task7HW.services.ItemService;
import com.example.springproject.task7HW.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.relation.Role;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping(value = "/")
    public String index(Model model) {
        ArrayList<ShopItems> items = (ArrayList<ShopItems>) itemService.getAllItems();
        ArrayList<ShopItems> items1 = new ArrayList<>();
        ArrayList<ShopItems> items2 = new ArrayList<>();
        List<Categories> categoriesList = itemService.getAllCategories();
        model.addAttribute("categories", categoriesList);
        for (ShopItems shopItem : items) {
            if (shopItem.isInTopPage()) {
                items1.add(shopItem);
            } else {
                items2.add(shopItem);
            }
        }

        List<Brands> brandsList = itemService.getAllBrands();
        List<Country> countryList = itemService.getAllCountry();
        model.addAttribute("brands", brandsList);
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("countries", countryList);
        model.addAttribute("IsTopItems", items1);
        model.addAttribute("items", items2);
        return "index";
    }

    @PostMapping(value = "/by")
    public String byCateg(Model model, @RequestParam(name = "idCategoria", defaultValue = "-1") Long categoryID) {
        Categories category = itemService.getCategory(categoryID);
        List<Categories> categoriesList = itemService.getAllCategories();
        model.addAttribute("categories", categoriesList);
        List<Brands> brandsList = itemService.getAllBrands();
        model.addAttribute("brands", brandsList);
        model.addAttribute("currentUser", getUserData());
        if (category != null) {
            List<ShopItems> listCategory = itemService.getAllByCategories(category);
            model.addAttribute("byCategory", listCategory);
            model.addAttribute("category", category);

            return "byCategory";
        }
        return "redirect:/index";
    }

    @PostMapping(value = "/addItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String addItem(@RequestParam(name = "item_name", defaultValue = "No Name") String name,
                          @RequestParam(name = "item_description", defaultValue = "No Description") String description,
                          @RequestParam(name = "item_price", defaultValue = "0") int price,
                          @RequestParam(name = "item_star", defaultValue = "0") int star,
                          @RequestParam(name = "item_date", defaultValue = "1991-02-02") String date,
                          @RequestParam(name = "item_smallPic", defaultValue = "https://www.freeiconspng.com/thumbs/no-image-icon/no-image-icon-6.png") String small_picture,
                          @RequestParam(name = "item_largePic", defaultValue = "https://www.tellerreport.com/images/no-image.png") String large_picture,
                          @RequestParam(name = "brand_id", defaultValue = "0") Long brandId,
                          @RequestParam(name = "category_id", defaultValue = "0") Long categoryId,
                          Model model
//                          @RequestParam(name = "country_id", defaultValue = "0") Long countryId
    ) {
//        Categories cat = itemService.getCategory(categoryId);
//        List<Categories> categoriesList = new ArrayList<>();
        model.addAttribute("currentUser", getUserData());
        ShopItems item = new ShopItems();
        Brands brand = itemService.getBrand(brandId);
//        Country country = itemService.getCountry(countryId);
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setStars(star);
        item.setSmallPicURL(small_picture);
        item.setLargePicURL(large_picture);
        item.setAddedDate(Date.valueOf(date));
        item.setInTopPage(false);
        item.setBrands(brand);
//        item.setCategories(cat);
//        item.getBrands().setCountries(country);
        itemService.addItem(item);

        Categories cat = itemService.getCategory(categoryId);
        if (cat != null) {
            ShopItems items = itemService.getItem(item.getId());
            System.out.println(items);
            if (items != null) {
                List<Categories> categories = items.getCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }
                categories.add(cat);

                items.setCategories(categories);
                itemService.saveItem(items);

                return "redirect:/admin";
            }

        }


//        itemService.addItem(new ShopItems(null, name, description, price, star, small_picture, large_picture, Date.valueOf(date), false));
        return "redirect:/";
    }

    @GetMapping(value = "/view/{idshka}")
    public String view(Model model, @PathVariable(name = "idshka") Long id) {
//        Items item = DBManager.getItem(id);
        List<Brands> brandsList = itemService.getAllBrands();
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("brands", brandsList);
        List<Categories> categoriesList = itemService.getAllCategories();
        model.addAttribute("categories", categoriesList);

        ShopItems item = itemService.getItem(id);
        model.addAttribute("item", item);
        return "view";
    }

    @GetMapping(value = "/details/{idshka}")
    public String details(Model model, @PathVariable(name = "idshka") Long id) {
//        Items item = DBManager.getItem(id);
        ShopItems item = itemService.getItem(id);
        model.addAttribute("item", item);
        List<Country> countryList = itemService.getAllCountry();
        List<Brands> brandsList = itemService.getAllBrands();
        List<Categories> categoriesList = item.getCategories();

        List<Categories> categoriesList2 = itemService.getAllCategories();
        List<Categories> categoriesList3 = itemService.getAllCategories();
        for (int i = 0; i < categoriesList.size(); i++) {
            Categories category = itemService.getCategory(categoriesList.get(i).getId());
            if (categoriesList2.contains(category)) {
                categoriesList3.remove(category);
            }
//
//            for (int j = 0; j < categoriesList2.size(); j++) {
//                if(categoriesList.get(i).getId() == categoriesList2.get(j).getId()){
//
//                }
//            }
        }
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("countries", countryList);
        model.addAttribute("categories", categoriesList);
        model.addAttribute("categoriesWithout", categoriesList3);
        model.addAttribute("brands", brandsList);
        return "details";
    }

    @PostMapping(value = "/deleteItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String deleteItem(@RequestParam(name = "id", defaultValue = "0") Long id,
                             Model model) {
//        DBManager.addItem(new Items(null, name,price));
//        itemService.addItem(new ShopItems(null, name,price,amount));
        model.addAttribute("currentUser", getUserData());
        System.out.println("keldi");
        ShopItems item = itemService.getItem(id);
        if (item != null) {
            itemService.deleteItem(item);
        }
        return "redirect:/";
    }

    @PostMapping(value = "/saveItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String saveItem(
            @RequestParam(name = "id", defaultValue = "0") Long id,
            @RequestParam(name = "item_name", defaultValue = "No Item") String name,
            @RequestParam(name = "item_description", defaultValue = "No description") String description,
            @RequestParam(name = "item_price", defaultValue = "0") double price,
            @RequestParam(name = "item_star", defaultValue = "0") int stars,
            @RequestParam(name = "item_smallPic", defaultValue = "https://www.freeiconspng.com/thumbs/no-image-icon/no-image-icon-6.png") String smallPic,
            @RequestParam(name = "item_largePic", defaultValue = "https://tutaki.org.nz/wp-content/uploads/2019/04/no-image-1.png") String largePic,
            @RequestParam(name = "country_id", defaultValue = "0") Long country_id,
            @RequestParam(name = "brand_id", defaultValue = "0") Long brand_id,
            @RequestParam(name = "isTop", defaultValue = "0") boolean isTop,
            Model model) {
        System.out.println("save-ke keldi");
        model.addAttribute("currentUser", getUserData());
        ShopItems item = itemService.getItem(id);
        if (item != null) {
//            Country country = itemService.getCountry(country_id);
//            if(country!=null) {
            Brands brand = itemService.getBrand(brand_id);
            if (brand != null) {
                System.out.println("country id:  " + country_id);
//                    System.out.println("brand id:  " + brand_id);
                item.setName(name);
                item.setDescription(description);
                item.setPrice(price);
                item.setStars(stars);
                item.setSmallPicURL(smallPic);
                item.setLargePicURL(largePic);
//                    item.getBrands().setCountries(country);
                item.setBrands(brand);
                item.setInTopPage(isTop);
                itemService.saveItem(item);
            }
//            }
        }
        return "redirect:/admin";
    }

    @GetMapping(value = "/searchItem")
    public String searchItem(Model model,
                             @RequestParam(name = "name", defaultValue = "") String name,
                             @RequestParam(name = "brand_id", defaultValue = "0") Long brand_id,
                             @RequestParam(name = "priceFrom", defaultValue = "0") String priceFrom,
//                         @RequestParam(name = "empty_brand", defaultValue = "0") Long emp_id,
                             @RequestParam(name = "priceTo", defaultValue = "1111111") String priceTo,
                             @RequestParam(name = "order", defaultValue = "asc") String order) {
        System.out.println("name: " + name);


        List<ShopItems> items2 = itemService.getItemsByNamePriceAsc(name);
        Brands brand = itemService.getBrand(brand_id);
        System.out.println("brand_id: " + brand_id);
        model.addAttribute("currentUser", getUserData());
        List<ShopItems> items = itemService.getItemsByNamePriceDesc(name);
//        Items item = DBManager.getItem(id);

//        if (items != null) {'
        System.out.println("asdasdasds");
        System.out.println(items);
        List<Categories> categoriesList = itemService.getAllCategories();
        model.addAttribute("categories", categoriesList);
        model.addAttribute("items", items);
        model.addAttribute("name", name);
        model.addAttribute("order", order);
        model.addAttribute("price_from", priceFrom);
        model.addAttribute("price_to", priceTo);
        model.addAttribute("oneBrand", brand);
        List<Brands> brandsList = itemService.getAllBrands();
        model.addAttribute("brands", brandsList);
        System.out.println(brandsList.size());
        return "search";
//        }
//        return "redirect:/";

    }

    @GetMapping(value = "/search")
    public String search(Model model,
                         @RequestParam(name = "name", defaultValue = "") String name,
                         @RequestParam(name = "brand_id", defaultValue = "0") Long brand_id,
                         @RequestParam(name = "priceFrom", defaultValue = "0") String priceFrom,
                         @RequestParam(name = "brand_name", defaultValue = "") String brandName,
//                         @RequestParam(name = "empty_brand", defaultValue = "0") Long emp_id,
                         @RequestParam(name = "priceTo", defaultValue = "1111111") String priceTo,
                         @RequestParam(name = "order", defaultValue = "asc") String order) {
        System.out.println("name: " + name);

        Brands brand = itemService.getBrand(brand_id);
        List<ShopItems> items2 = itemService.getItemsByNamePriceAsc(name);
        if (!brandName.equals("")) {
            List<Brands> brandsList = itemService.getAllBrands();
            for (Brands brands : brandsList) {
                if (brands.getName().equals(brandName)) {
                    brand = itemService.getBrand(brands.getId());
                    brand_id = brand.getId();
                }
            }
        }

        System.out.println("brand_id: " + brand_id);
        model.addAttribute("currentUser", getUserData());
        List<ShopItems> items;
//        Items item = DBManager.getItem(id);

//        if (name.equals("") && priceFrom.equals("0") && priceTo.equals("1111111")){
//
//        }

        if (priceFrom.equals("0") && priceTo.equals("1111111")) {
            if (order.equals("asc")) {
                if (brand_id == 0) {
                    items = itemService.getItemsByNamePriceAsc(name);
                } else {
                    items = itemService.getItemsByBrandAndByNamePriceAsc(brand, name);
                }
            } else {
                if (brand_id == 0) {
                    items = itemService.getItemsByNamePriceDesc(name);
                } else {
                    items = itemService.getItemsByBrandAndByNamePriceDesc(brand, name);
                }
            }
        } else {
            if (order.equals("asc")) {
                items = itemService.getItemsByBrandsAndByNameAndPriceBetweenOrderByPriceAsc(brand, name, Double.parseDouble(priceFrom), Double.parseDouble(priceTo));
//                items = itemService.getItemsByNameAndPriceBetweenOrderByPriceAsc(name, Double.parseDouble(priceFrom), Double.parseDouble(priceTo));
            } else {
//                items = itemService.getItemsByBrandsAndByNameAndPriceBetweenOrderByPriceDesc(brand, name, Double.parseDouble(priceFrom), Double.parseDouble(priceTo));
                items = itemService.getItemsByBrandsAndByNameAndPriceBetweenOrderByPriceAsc(brand, name, Double.parseDouble(priceFrom), Double.parseDouble(priceTo));

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
        model.addAttribute("oneBrand", brand);
        List<Brands> brandsList = itemService.getAllBrands();
        model.addAttribute("brands", brandsList);
        List<Categories> categoriesList = itemService.getAllCategories();
        model.addAttribute("categories", categoriesList);
        System.out.println(brandsList.size());
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
        model.addAttribute("currentUser", getUserData());
        if (price_from == (0) && price_to == (-1)) {
            List<ShopItems> items = itemService.getItemsByNamePriceAsc(name);
            if (items != null) {
                model.addAttribute("name", name);
                model.addAttribute("items", items);
            }
        } else {
            List<ShopItems> items2 = itemService.getItemsByNameAndPriceBetweenOrderByPriceAsc(name, price_from, price_to);
            if (items2 != null) {
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
        model.addAttribute("currentUser", getUserData());
        if (price_from == (0) && price_to == (-1)) {
            List<ShopItems> items = itemService.getItemsByNamePriceDesc(name);
            if (items != null) {
                model.addAttribute("name", name);
                model.addAttribute("items", items);
            }
        } else {
            List<ShopItems> items2 = itemService.getItemsByNameAndPriceBetweenOrderByPriceDesc(name, price_from, price_to);
            if (items2 != null) {
                model.addAttribute("name", name);
                model.addAttribute("items", items2);
            }
        }
        return "search";
    }

    @GetMapping(value = "/editBrands")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String editBrands(Model model) {
        List<Brands> brandsList = itemService.getAllBrands();
        List<Country> countryList = itemService.getAllCountry();
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("brands", brandsList);
        model.addAttribute("countries", countryList);

        return "editBr";
    }

    @PostMapping(value = "/addBrand")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String addBrand(@RequestParam(name = "brand_name", defaultValue = "") String brand_name,
                           @RequestParam(name = "country_id", defaultValue = "0") Long country_id,
                           Model model) {
        boolean check = true;
        List<Brands> brands = itemService.getAllBrands();
        for (Brands brands1 : brands) {
            if (brands1.getName().toLowerCase().equals(brand_name.toLowerCase())) {
                check = false;
            }
        }
        Country country = itemService.getCountry(country_id);
        if (check) {
            if (country != null) {
                itemService.addBrand(new Brands(null, brand_name, country));
            }
        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/brands";
    }

    @PostMapping(value = "/addCountry")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String addBrand(@RequestParam(name = "country_name", defaultValue = "") String country_name,
                           @RequestParam(name = "country_code", defaultValue = "") String code,
                           Model model) {
        boolean check = true;
        List<Country> countryList = itemService.getAllCountry();
        for (Country country : countryList) {
            if (country.getName().toLowerCase().equals(country_name.toLowerCase())) {
                check = false;
            }
        }
        if (check) {

            itemService.addCountry(new Country(null, country_name, code));

        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/countries";
    }

    @PostMapping(value = "/saveCountry")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String saveCountry(@RequestParam(name = "country_name", defaultValue = "") String country_name,
                              @RequestParam(name = "id", defaultValue = "0") Long id,
                              @RequestParam(name = "country_code", defaultValue = "") String code,
                              Model model) {
        boolean check = true;
        Country country = itemService.getCountry(id);
        List<Country> countryList = itemService.getAllCountry();
        System.out.println("list = " + countryList);
        System.out.println("country = " + country);
        for (Country countrys : countryList) {
            System.out.println("countryInFOr = " + countrys);
            if (countrys.getName().toLowerCase().equals(country_name.toLowerCase())) {
                if (country.getName().equals(country_name)) {
                    continue;
                } else {
                    check = false;
                }
            }
        }
        if (check) {
            country.setName(country_name);
            country.setCode(code);
            itemService.saveCountry(country);
        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/countries";
    }

    //    Admin
    @GetMapping(value = "/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String indexAdmin(Model model) {
        ArrayList<ShopItems> items = (ArrayList<ShopItems>) itemService.getAllItems();

        model.addAttribute("currentUser", getUserData());
        List<Brands> brandsList = itemService.getAllBrands();
        List<Country> countryList = itemService.getAllCountry();
        List<Categories> categoriesList = itemService.getAllCategories();
        model.addAttribute("brands", brandsList);
        model.addAttribute("categories", categoriesList);
        model.addAttribute("countries", countryList);
        model.addAttribute("items", items);
        return "indexAdmin";
    }

    @GetMapping(value = "/categories")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String getCategories(Model model) {
        List<Categories> categoriesList = itemService.getAllCategories();

        model.addAttribute("categories", categoriesList);

        return "categoryAdmin";
    }

    @PostMapping(value = "/addCategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String addCategory(@RequestParam(name = "category_name", defaultValue = "No Name") String categoryName,
                              @RequestParam(name = "logoUrl", defaultValue = "No Logo") String logoUrl,
                              Model model) {

        model.addAttribute("currentUser", getUserData());
        Categories categories = new Categories();
        categories.setName(categoryName);
        categories.setLogoUrl(logoUrl);
        itemService.addCategory(categories);
        return "redirect:/categories";
    }

    @GetMapping(value = "/countries")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String getCountries(Model model) {
        List<Country> countryList = itemService.getAllCountry();

        model.addAttribute("countries", countryList);

        return "countryAdmin";
    }

    @GetMapping(value = "/brands")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String getBrands(Model model) {
        List<Brands> brandsList = itemService.getAllBrands();
        List<Country> countryList = itemService.getAllCountry();

        model.addAttribute("brands", brandsList);
        model.addAttribute("countries", countryList);

        return "brandsAdmin";
    }


//    @PostMapping(value = "/addCountry")
//    public String addCountry(@RequestParam(name = "country_name", defaultValue = "No Name") String countryName,
//                              @RequestParam(name = "code", defaultValue = "No Code") String code) {
//
//
//        Country country = new Country();
//        country.setName(countryName);
//        country.setCode(code);
//        itemService.addCountry(country);
//        return "redirect:/countries";
//    }

    @PostMapping(value = "/assigncategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String assignCategory(@RequestParam(name = "item_id") Long itemId,
                                 @RequestParam(name = "category_id") Long categoryID,
                                 Model model) {
        Categories cat = itemService.getCategory(categoryID);
        if (cat != null) {
            ShopItems item = itemService.getItem(itemId);
            if (item != null) {
                List<Categories> categories = item.getCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }
                categories.add(cat);

                itemService.saveItem(item);
                model.addAttribute("currentUser", getUserData());
                return "redirect:/details/" + itemId + "#categoriesDivP";
            }
        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/admin";
    }

    @PostMapping(value = "/assigncategoryMinus")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String assignCategoryMinus(@RequestParam(name = "item_id") Long itemId,
                                      @RequestParam(name = "category_id") Long categoryID,
                                      Model model) {
        Categories cat = itemService.getCategory(categoryID);
        if (cat != null) {
            ShopItems item = itemService.getItem(itemId);
            if (item != null) {
                List<Categories> categories = item.getCategories();
                if (categories == null) {
                    categories = new ArrayList<>();
                }
                categories.remove(cat);

                itemService.saveItem(item);
                model.addAttribute("currentUser", getUserData());
                return "redirect:/details/" + itemId + "#categoriesDivM";
            }
        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/admin";
    }

    @GetMapping(value = "/editCountry/{idshka}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String detailsCountry(Model model, @PathVariable(name = "idshka") Long id) {
//        Items item = DBManager.getItem(id);
        Country country = itemService.getCountry(id);
        String editName = "country";
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("country", country);
        model.addAttribute("editName", editName);

        return "detailsCountry";
    }

    @GetMapping(value = "/editBrand/{idshka}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String detailsBrand(Model model, @PathVariable(name = "idshka") Long id) {
//        Items item = DBManager.getItem(id);
        Brands brand = itemService.getBrand(id);
        List<Country> countryList = itemService.getAllCountry();
//        String editName = "country";
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("brand", brand);
        model.addAttribute("countries", countryList);
//        model.addAttribute("editName", editName);
        return "detailsBrands";
    }

    @PostMapping(value = "/saveBrand")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String saveBrand(@RequestParam(name = "brand_name", defaultValue = "") String brand_name,
                            @RequestParam(name = "id", defaultValue = "0") Long brandId,
                            @RequestParam(name = "country_id", defaultValue = "0") Long countryId,
                            Model model) {
        Brands brand = itemService.getBrand(brandId);

        List<Country> countryList = itemService.getAllCountry();
        System.out.println("Country id = " + countryId);
        if (brand != null) {
            Country country = itemService.getCountry(countryId);
            if (country != null) {
                brand.setName(brand_name);
                brand.setCountries(country);
                itemService.saveBrand(brand);

            }
        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/brands";
    }

    @GetMapping(value = "/editCategory/{idshka}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String editCategory(Model model, @PathVariable(name = "idshka") Long id) {
        Categories category = itemService.getCategory(id);
        if (category != null) {

            model.addAttribute("category", category);
        }
//        model.addAttribute("editName", editName);
        return "detailsCategory";
    }

    @PostMapping(value = "/saveCategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String saveCategory(@RequestParam(name = "category_name", defaultValue = "no name") String category_name,
                               @RequestParam(name = "id", defaultValue = "0") Long categoryID,
                               @RequestParam(name = "logoUrl", defaultValue = "no url") String logoUrl) {
        Categories category = itemService.getCategory(categoryID);
        if (category != null) {
            category.setName(category_name);
            category.setLogoUrl(logoUrl);
            itemService.saveCategory(category);
        }

        return "redirect:/categories";
    }

    @GetMapping(value = "/viewAdmin/{idshka}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String viewItem(Model model, @PathVariable(name = "idshka") Long id) {
//        Items item = DBManager.getItem(id);
        ShopItems item = itemService.getItem(id);

        model.addAttribute("item", item);
        return "viewItemAdmin";
    }

    @PostMapping(value = "/deleteCountry")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String deleteCountry(Model model, @RequestParam(name = "id", defaultValue = "0") Long id) {
        Country country = itemService.getCountry(id);
        if (country != null) {
            itemService.deleteCountry(country);
        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/countries";
    }

    @PostMapping(value = "/deleteCategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String deleteCategory(Model model, @RequestParam(name = "id", defaultValue = "0") Long id) {
        Categories category = itemService.getCategory(id);
        if (category != null) {
            itemService.deleteCategory(category);
        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/categories";
    }

    @PostMapping(value = "/deleteBrand")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String deleteBrand(Model model, @RequestParam(name = "id", defaultValue = "0") Long id) {
        Brands brand = itemService.getBrand(id);
        if (brand != null) {
            itemService.deleteBrand(brand);
        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/brands";
    }

    private Users getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User secUser = (User) authentication.getPrincipal();
            Users myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model) {
        model.addAttribute("currentUser", getUserData());
        return "403";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("currentUser", getUserData());
        ArrayList<ShopItems> items = (ArrayList<ShopItems>) itemService.getAllItems();
        List<Brands> brandsList = itemService.getAllBrands();
        List<Country> countryList = itemService.getAllCountry();
        List<Categories> categoriesList = itemService.getAllCategories();
        model.addAttribute("brands", brandsList);
        model.addAttribute("categories", categoriesList);
        model.addAttribute("countries", countryList);
        model.addAttribute("items", items);
        return "login";
    }

    @GetMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model) {
        ArrayList<ShopItems> items = (ArrayList<ShopItems>) itemService.getAllItems();
        List<Brands> brandsList = itemService.getAllBrands();
        List<Country> countryList = itemService.getAllCountry();
        List<Categories> categoriesList = itemService.getAllCategories();
        model.addAttribute("brands", brandsList);
        model.addAttribute("categories", categoriesList);
        model.addAttribute("countries", countryList);
        model.addAttribute("items", items);
        model.addAttribute("currentUser", getUserData());
        return "profile";
    }

    @GetMapping(value = "/addItem")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MOERATOR')")
    public String addItem(Model model) {
        model.addAttribute("currentUser", getUserData());
        List<Country> countryList = itemService.getAllCountry();
        model.addAttribute("countries", countryList);
        return "addItem";
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        List<Brands> brandsList = itemService.getAllBrands();
        model.addAttribute("brands", brandsList);
        List<Categories> categoriesList = itemService.getAllCategories();
        model.addAttribute("categories", categoriesList);
        model.addAttribute("currentUser", getUserData());
        return "register";
    }

    @PostMapping(value = "/register")
    public String toRegister(@RequestParam(name = "user_email", defaultValue = "no email") String email,
                             @RequestParam(name = "user_password", defaultValue = "No password") String password,
                             @RequestParam(name = "user_re_password", defaultValue = "0") String re_password,
                             @RequestParam(name = "full_name", defaultValue = "no name") String full_name) {

        if (password.equals(re_password)) {

            Users myUser = new Users();
            myUser.setFullName(full_name);
            myUser.setEmail(email);
            myUser.setPassword(password);
            if (userService.createUser(myUser) != null) {
                return "redirect:/register?success";
            }
        }
        return "redirect:/register?error";
    }

    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getUsers(Model model) {
        List<Users> usersList = userService.getAllUsers();
//        List<Roles> rolesList = userService.getAllRoles();

        model.addAttribute("users", usersList);
//        model.addAttribute("roles", rolesList);

        return "usersAdmin";
    }

    @PostMapping(value = "/addUser")
    public String usertoRegister(@RequestParam(name = "email", defaultValue = "no email") String email,
                                 @RequestParam(name = "password", defaultValue = "No password") String password,
                                 @RequestParam(name = "re_password", defaultValue = "0") String re_password,
                                 @RequestParam(name = "full_name", defaultValue = "no name") String full_name) {

        if (password.equals(re_password)) {

            Users myUser = new Users();
            myUser.setFullName(full_name);
            myUser.setEmail(email);
            myUser.setPassword(password);
            if (userService.createUser(myUser) != null) {
                return "redirect:/users?success";
            }
        }
        return "redirect:/users?error";
    }

    @GetMapping(value = "/editUser/{idshka}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editUser(Model model, @PathVariable(name = "idshka") Long id) {
//        Items item = DBManager.getItem(id);

        Users users = userService.getUsers(id);
        List<Roles> rolesList = userService.getAllRoles();
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("users", users);
        model.addAttribute("roles", rolesList);

        List<Roles> roless = users.getRoles();

        List<Roles> rolesList2 = userService.getAllRoles();
        List<Roles> rolesList3 = userService.getAllRoles();
        for (int i = 0; i < roless.size(); i++) {
            Roles role = userService.getRoles(roless.get(i).getId());
            if (rolesList2.contains(role)) {
                rolesList3.remove(role);
            }
        }
        model.addAttribute("roli", roless);
        model.addAttribute("roleWithout", rolesList3);

        return "editUser";
    }

    @PostMapping(value = "/saveUser")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String saveUser(@RequestParam(name = "full_name", defaultValue = "no name") String full_name,
                           @RequestParam(name = "id", defaultValue = "0") Long userID,
                           @RequestParam(name = "email", defaultValue = "no email") String email) {
        Users users = userService.getUsers(userID);
        if (users != null) {
            users.setFullName(full_name);
            users.setEmail(email);
            userService.saveUser(users);
        }

        return "redirect:/users";
    }

    @PostMapping(value = "/saveUserPassword")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String saveUserPassword(@RequestParam(name = "user_old_password", defaultValue = " ") String user_old_password,
                           @RequestParam(name = "id", defaultValue = "0") Long userID,
                           @RequestParam(name = "new_password", defaultValue = " ") String new_password,
                           @RequestParam(name = "re_new_password", defaultValue = "no email") String re_new_password) {
        Users myUser = userService.getUsers(userID);
        if (new_password.equals(re_new_password)) {

            if(passwordEncoder.matches(user_old_password,myUser.getPassword())){
                myUser.setPassword(new_password);
                userService.updatePassword(myUser);
                return "redirect:/users?success";
            }
        }
        return "redirect:/users?error";
    }

    @PostMapping(value = "/assignRole")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String assignRole(@RequestParam(name = "user_id") Long userId,
                             @RequestParam(name = "role_id") Long roleID,
                             Model model) {
        Roles role = userService.getRoles(roleID);
        if (role != null) {
            Users user = userService.getUsers(userId);
            if (user != null) {
                List<Roles> roles = user.getRoles();
                if (roles == null) {
                    roles = new ArrayList<>();
                }
                roles.add(role);

                userService.saveUser(user);
                model.addAttribute("currentUser", getUserData());
                return "redirect:/editUser/" + userId + "#rolesDivP";
            }
        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/users";
    }

    @PostMapping(value = "/assignRoleMinus")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String assignRoleMinus(@RequestParam(name = "user_id") Long userId,
                                  @RequestParam(name = "role_id") Long roleID,
                                  Model model) {
        Roles role = userService.getRoles(roleID);
        if (role != null) {
            Users user = userService.getUsers(userId);
            if (user != null) {
                List<Roles> roles = user.getRoles();
                if (roles == null) {
                    roles = new ArrayList<>();
                }
                roles.remove(role);

                userService.saveUser(user);
                model.addAttribute("currentUser", getUserData());
                return "redirect:/editUser/" + userId + "#roleDivM";
            }
        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/users";
    }

    @PostMapping(value = "/deleteUser")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteUser(Model model, @RequestParam(name = "id", defaultValue = "0") Long id,
                             @RequestParam(name = "full_name", defaultValue = "no name") String full_name,
                             @RequestParam(name = "email", defaultValue = "no email") String email) {
        Users user = userService.getUsers(id);
        if (user != null) {
            userService.deleteUsers(user);
        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/users";
    }

    @GetMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String getRoles(Model model) {
        List<Roles> rolesList = userService.getAllRoles();
//        List<Roles> rolesList = userService.getAllRoles();

//        model.addAttribute("users", usersList);
        model.addAttribute("roles", rolesList);

        return "rolesAdmin";
    }

    @PostMapping(value = "/addRole")
    public String usertoRegister(@RequestParam(name = "role_name", defaultValue = "no name") String role_name) {

        Roles myRole = new Roles();
        myRole.setRole(role_name);
        if (userService.createRoles(myRole) != null) {
            return "redirect:/roles?success";
        }
        return "redirect:/roles?error";
    }

    @GetMapping(value = "/editRole/{idshka}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String editRole(Model model, @PathVariable(name = "idshka") Long id) {
//        Items item = DBManager.getItem(id);

        Roles roles = userService.getRoles(id);
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("role", roles);

        return "editRole";
    }

    @PostMapping(value = "/saveRole")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String saveRole(@RequestParam(name = "role_name", defaultValue = "no name") String role_name,
                           @RequestParam(name = "id", defaultValue = "0") Long roleID) {
        Roles roles = userService.getRoles(roleID);
        if (roles != null) {
            roles.setRole(role_name);
            userService.saveRole(roles);
        }

        return "redirect:/roles";
    }

    @PostMapping(value = "/deleteRole")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String deleteRole(Model model, @RequestParam(name = "id", defaultValue = "0") Long id) {
        Roles role = userService.getRoles(id);
        if (role != null) {
            userService.deleteRole(role);
        }
        model.addAttribute("currentUser", getUserData());
        return "redirect:/roles";
    }

    @PostMapping(value = "/profileSave")
    @PreAuthorize("isAuthenticated()")
    public String profileSave(@RequestParam(name = "user_old_password", defaultValue = " ") String user_old_password,
                                   @RequestParam(name = "id", defaultValue = "0") Long userID,
                                   @RequestParam(name = "new_password", defaultValue = " ") String new_password,
                                   @RequestParam(name = "re_new_password", defaultValue = "no email") String re_new_password) {
        Users myUser = userService.getUsers(userID);
        if (new_password.equals(re_new_password)) {

            if(passwordEncoder.matches(user_old_password,myUser.getPassword())){
                myUser.setPassword(new_password);
                userService.updatePassword(myUser);
                return "redirect:/profile?success";
            }
        }
        return "redirect:/profile?error";
    }

    @PostMapping(value = "/saveUserProfile")
    public String saveUserProfile(@RequestParam(name = "full_name", defaultValue = "no name") String full_name,
                           @RequestParam(name = "id", defaultValue = "0") Long userID,
                           @RequestParam(name = "email", defaultValue = "no email") String email) {
        Users users = userService.getUsers(userID);
        if (users != null) {
            users.setFullName(full_name);
            users.setEmail(email);
            userService.saveUser(users);
        }

        return "redirect:/profile";
    }
}
