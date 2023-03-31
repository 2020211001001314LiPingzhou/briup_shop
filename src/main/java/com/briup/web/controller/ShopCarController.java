package com.briup.web.controller;

import com.briup.bean.ShopCar;
import com.briup.bean.User;
import com.briup.service.IShopCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShopCarController {

    @Autowired
    private IShopCarService shopCarService;

    @GetMapping("/addShopCar")
    @ResponseBody
    public String addShopCar(Long shopId, HttpSession session){
        Long userId = ((User) session.getAttribute("user")).getId();

        shopCarService.saveShopCar(1, userId, shopId);
        // 成功，返回信息
        return "";
    }

    @GetMapping("/toShopCar")
    public String toShopCar(HttpSession session, Model model){
        Long userId = ((User) session.getAttribute("user")).getId();
        List<ShopCar> shopCarList = shopCarService.findUserAllShopCar(userId);
        System.out.println("shopCarList =============" + shopCarList);
        model.addAttribute("shopCarList", shopCarList);
        return "shopCar";
    }


    @GetMapping("/updateShopCar")
    @ResponseBody
    public void updateShopCar(Long shopCarId, Integer num) {
        // 需要检验数量，小于等于0则不能再减少
        if (num <= 0)
            return;

        // 更新购物车数量
        shopCarService.updateShopCar(shopCarId, num);
    }


    @GetMapping("/delShopCar")
    @ResponseBody
    public void delShopCar(Long shopCarId){
        shopCarService.deleteShopCar(shopCarId);
    }
}
