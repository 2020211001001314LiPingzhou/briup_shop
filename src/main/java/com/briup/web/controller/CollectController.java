package com.briup.web.controller;

import com.briup.bean.Collect;
import com.briup.bean.User;
import com.briup.service.ICollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CollectController {

    @Autowired
    private ICollectService collectService;

    @GetMapping("/toCollect")
    public String toCollect(HttpSession session, Model model){
        Long userId = ((User) session.getAttribute("user")).getId();
        List<Collect> collects = collectService.findUserAllCollect(userId);
        model.addAttribute("collects", collects);
        System.out.println("collects" + collects);
        return "collect";
    }

    @GetMapping("/addCollect")
    @ResponseBody
    public String addCollect(Long shopId, HttpSession session){
        User user = (User) session.getAttribute("user");
        collectService.addCollect(user, shopId);
        return "success";
    }

    @GetMapping("/delCollect")
    @ResponseBody
    public void delCollect(long shopId, HttpSession session) {
        // 直接通过collectId去删除收藏是不安全的，可能会被他人发送的/delCollect?collectId=..请求恶意删除
        //collectService.deleteCollect(collectId);
        Long userId = ((User) session.getAttribute("user")).getId();
        collectService.deleteCollect(userId, shopId);
    }

    @GetMapping("/findCollect")
    @ResponseBody
    public boolean findCollect(Long shopId, HttpSession session) {
        Long userId = ((User) session.getAttribute("user")).getId();
        boolean hasCollect = collectService.findCollect(userId, shopId);
        if (hasCollect) {
            return hasCollect;
        }

        return hasCollect;
    }
}
