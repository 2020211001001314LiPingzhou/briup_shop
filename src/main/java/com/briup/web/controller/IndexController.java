package com.briup.web.controller;

import com.briup.bean.vo.CategoryVO;
import com.briup.service.ICategoryService;
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

    @GetMapping(value = {"/","/index","toIndex"})
    public String toIndex(HttpSession session){
        ServletContext application = session.getServletContext();
        // 这块后面考虑放入redis中
        List<CategoryVO> categories = categoryService.findAllCategory();
        application.setAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/error")
    public String toError(){
        return "error";
    }
}
