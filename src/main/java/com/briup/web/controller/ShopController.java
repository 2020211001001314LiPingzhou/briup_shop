package com.briup.web.controller;

import com.briup.bean.Shop;
import com.briup.service.IShopService;
import com.sun.net.httpserver.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ShopController {
    @Autowired
    private IShopService shopService;

    @GetMapping("/toList")
    public String toList(Long categoryId, Model model){
        List<Shop> shopList = shopService.findByCategory(categoryId);
        System.out.println("shopList =========== " + shopList);
        model.addAttribute("shopList", shopList);
        return "list";
    }

    @GetMapping("/searchShop")
    public String searchShop(String shopName, Model model) {
        List<Shop> shopList = shopService.searchShop(shopName);
        model.addAttribute("shopList", shopList);
        return "list";
    }

    @GetMapping("/toViewShop")
    public String toViewShop(Long shopId, Model model){
        Shop shop = shopService.findShopById(shopId);
        System.out.println("shop =====" + shop);
        model.addAttribute("shop", shop);
        return "viewShop";
    }

}
