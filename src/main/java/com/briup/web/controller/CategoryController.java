package com.briup.web.controller;

import com.briup.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;



}
