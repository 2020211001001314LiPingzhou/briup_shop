package com.briup.dao;

import com.briup.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface IUserDao{
    User findByLoginName(String loginName);

    boolean saveUser(User user);

    User findByUNP(String loginName, String passwordMd5);
}
