package com.briup.service.impl;

import com.briup.bean.User;
import com.briup.dao.IUserDao;
import com.briup.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class IUserServiceImpl implements IUserService {
    @Resource
    private IUserDao userDao;

    @Override
    public User login(String loginName, String passwordMd5) throws Exception {
        // 获取用户（后端查可以只用登录名即可，它是唯一标识，不用将密码一传入去查，多余了）
        User user = userDao.findByLoginName(loginName);
        //User user = userDao.findByUNP();
        // 判断登录名是否存在
        if (user == null) {
            throw new Exception("登录名不存在");
        }
        if (user.isLock()) {
            throw new Exception("用户被锁定，请联系管理员");
        }
        // 对登录密码进行Md5转换
        passwordMd5 = DigestUtils.md5DigestAsHex(passwordMd5.getBytes());

        // 判断密码是否正确
        if (!user.getPasswordMd5().equals(passwordMd5)) {
            throw new Exception("密码错误");
        }
        return user;
    }

    @Override
    public void register(User user) throws Exception {
        // 1.判断登录名是否已存在
        User byLoginName = userDao.findByLoginName(user.getLoginName());
        if (byLoginName != null) {
            throw new Exception("登录名已存在");
        }

        // 2.注册用户
        // 密码加密
        user.setPasswordMd5(DigestUtils.md5DigestAsHex(user.getPasswordMd5().getBytes()));
        boolean success = userDao.saveUser(user);
        if (!success) {
            throw new Exception("注册失败");
        }
    }

    @Override
    public User findByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }

    @Override
    public void exit(HttpSession session) throws Exception {
        session.invalidate();
    }
}
