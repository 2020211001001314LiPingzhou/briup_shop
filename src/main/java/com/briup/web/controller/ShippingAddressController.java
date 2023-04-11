package com.briup.web.controller;

import com.briup.bean.ShippingAddress;
import com.briup.bean.User;
import com.briup.service.IShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShippingAddressController {
    @Autowired
    private IShippingAddressService shippingAddressService;

    @GetMapping("/toAddShippingAddress")
    public String toAddShippingAddress(HttpSession session, Model model){
        Long userId = ((User) session.getAttribute("user")).getId();
        List<ShippingAddress> addressList = shippingAddressService.findUserAllShippingAddress(userId);
        model.addAttribute("addressList", addressList);
        return "addShippingAddress";
    }

    @PostMapping("/addShippingAddress")
    public String addShippingAddress(ShippingAddress shippingAddress, HttpSession session){
        shippingAddress.setUser((User) session.getAttribute("user"));
        shippingAddressService.saveShippingAddress(shippingAddress);
        return "redirect:/toAddShippingAddress";
    }

    @GetMapping("/delShippingAddress")
    @ResponseBody
    public boolean delShippingAddress(Long shippingAddressId, HttpSession session){
        Long userId = ((User) session.getAttribute("user")).getId();
        return shippingAddressService.delShippingAddress(shippingAddressId, userId);
    }

    @GetMapping("/setDefaultAddress")
    @ResponseBody
    public boolean setDefaultAddress(Long shippingAddressId, HttpSession session){
        Long userId = ((User) session.getAttribute("user")).getId();
        return shippingAddressService.setDefaultAddress(shippingAddressId, userId);
    }

}
