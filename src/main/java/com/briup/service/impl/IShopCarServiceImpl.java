package com.briup.service.impl;

import com.briup.bean.Shop;
import com.briup.bean.ShopCar;
import com.briup.dao.IShopCarDao;
import com.briup.service.IShopCarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class IShopCarServiceImpl implements IShopCarService {

    @Resource
    private IShopCarDao shopCarDao;

    @Override
    public List<ShopCar> findUserAllShopCar(Long userId) {
        return shopCarDao.findByUserId(userId);
    }

    @Override
    public void saveShopCar(int num, Long userId, Long shopId) {
        ShopCar shopCar = shopCarDao.findByUserIdShopId(userId, shopId);
        // 判断购物车中是否已存在该商品
        if (shopCar == null || shopCar.getId() == null) {
            // 不存在，添加商品到购物车
            shopCarDao.saveByShopIdUserId(num, userId, shopId);
        }else {
            // 已存在，则+1
            shopCarDao.updateShopCar(shopCar.getId(), shopCar.getNum()+1);
        }

    }

    @Override
    public void deleteShopCar(Long id) {
        shopCarDao.deleteById(id);
    }

    @Override
    public void updateShopCar(Long id, int num) {
        shopCarDao.updateShopCar(id, num);
    }

    @Override
    public List<ShopCar> findShopCars(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        //System.out.println("list ==== " + list);
        return shopCarDao.findShopCarByIds(list);
    }


    public boolean isShopCarExit(Long userId, Long shopId) {
        ShopCar shopCar = shopCarDao.findByUserIdShopId(userId, shopId);
        if (shopCar == null || shopCar.getId() == null)
            return false;
        return true;
    }

    /*@Override
    public Integer findShopCarNumById(Long id) {
        return shopCarDao.findShopNumCarById(id);
    }*/
}
