package com.briup.service.impl;

import com.briup.bean.Collect;
import com.briup.bean.User;
import com.briup.dao.ICollectDao;
import com.briup.service.ICollectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ICollectServiceImpl implements ICollectService {
    @Resource
    private ICollectDao collectDao;

    @Override
    public List<Collect> findUserAllCollect(Long id) {
        return collectDao.findByUserId(id);
    }

    @Override
    public void deleteCollect(Long userId, Long shopId) {
        collectDao.deleteByShopIdAndUserId(shopId, userId);
    }

    @Override
    public void deleteCollect(Long id) {
    }

    @Override
    public void addCollect(User user, Long shopId) {
        collectDao.addCollect(user.getId(), shopId);
    }

    @Override
    public boolean findCollect(Long userId, Long shopId) {
        Collect collect = collectDao.findByUserIdAndShopId(userId, shopId);
        return collect != null && collect.getId() != null;
    }

    @Override
    public Collect findOne(Long collectId) {
        return null;
    }
}
