package com.briup.service.impl;

import com.briup.bean.Shop;
import com.briup.dao.IShopDao;
import com.briup.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IShopServiceImpl implements IShopService {

    @Resource
    private IShopDao shopDao;

    @Override
    public List<Shop> findAllShops() {
        return null;
    }

    @Override
    public Shop findShopById(Long id) {
        return shopDao.findShopById(id);
    }

    @Override
    public List<Shop> findDiscount() {
        return null;
    }

    @Override
    public List<Shop> recommendShop(Long userId) {
        return null;
    }

    @Override
    public List<Shop> findByCategory(Long category) {
        return shopDao.findByCategoryId(category);
    }

    @Override
    public List<Shop> searchShop(String shopName) {
        return shopDao.findByNameContaining(shopName);
    }
}
