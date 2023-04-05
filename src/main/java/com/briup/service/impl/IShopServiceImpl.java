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
        // 通过访问量来简单实现发现好物
        return shopDao.findAllShopByVisitVolumeDesc();
    }

    @Override
    public Shop findShopById(Long id) {
        Shop shop = shopDao.findShopById(id);
        // 更新商品的访问量
        shopDao.updateVisitVolume(id, shop.getVisitVolume()+1);
        return shop;
    }

    @Override
    public List<Shop> findDiscount() {
        return shopDao.findByDiscountOrderBySalesVolumeDesc(true);
    }

    @Override
    public List<Shop> recommendShop(Long userId) {
        // 暂时通过访问量来简单模拟推荐
        return shopDao.findAllShopByVisitVolumeDesc();
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
