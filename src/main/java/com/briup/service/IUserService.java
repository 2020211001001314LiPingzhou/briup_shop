package com.briup.service;

import com.briup.bean.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;


public interface IUserService {
    /*
    登陆
     */
    User login(String loginName, String passwordMd5)throws Exception;
    /*
    注册
     */
    void register(User user)throws Exception ;

    User findByLoginName(String loginName)throws Exception;

}