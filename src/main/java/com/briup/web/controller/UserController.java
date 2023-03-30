package com.briup.web.controller;

import com.briup.bean.User;
import com.briup.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@Slf4j
//@Controller("/user")   不是在这里在value路径（太久没写忘记了=-='）
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/toRegister")
    public String toRegister(){
        //System.out.println(userService.findByLoginName("adam"));
        return "register";
    }

    @PostMapping("/register")
    public String register(User user, Model model){
        try {
            userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
            // 注册失败返回错误信息
            model.addAttribute("msg", e.getMessage());
            return "register";
        }
        return "login";
    }

    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String login(String loginName, String password, HttpSession session, Model model) {
        try {
            User user = userService.login(loginName, password);
            session.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", e.getMessage());
            return "login";
        }
        return "index";
    }


    /*<span><a href="user/toOrder">我的订单</a></span>
        <span><a href="user/toCollect">我的收藏</a></span>
        <span><a href="user/exit">退出</a></span>*/

    @GetMapping("/exit")
    public String exit(SessionStatus ss){
        System.out.println("销毁session作用域成功...");
        ss.setComplete();	//销毁session作用域
        return "forward:index";
    }

}
