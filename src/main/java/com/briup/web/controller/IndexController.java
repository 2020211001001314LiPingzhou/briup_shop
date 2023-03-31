package com.briup.web.controller;

import com.briup.bean.Shop;
import com.briup.bean.User;
import com.briup.bean.vo.CategoryVO;
import com.briup.service.ICategoryService;
import com.briup.service.IShopCarService;
import com.briup.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IShopService shopService;

    @GetMapping(value = {"/","/index","toIndex"})
    public String toIndex(HttpSession session){
        ServletContext application = session.getServletContext();
        // 这块后面考虑放入redis中
        // 分类商品
        List<CategoryVO> categories = categoryService.findAllCategory();
        // 特价商品
        List<Shop> discountList = shopService.findDiscount();
        // 发现好物
        List<Shop> shops = shopService.findAllShops();

        application.setAttribute("categories", categories);
        application.setAttribute("discountList", discountList);
        application.setAttribute("shops", shops);
        return "index";
    }

    @GetMapping("/error")
    public String toError(){
        return "error";
    }
}
