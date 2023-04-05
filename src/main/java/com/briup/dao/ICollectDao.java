package com.briup.dao;

import com.briup.bean.Collect;

import java.util.List;

public interface ICollectDao{
	List<Collect> findByUserId(long id);
	void deleteByShopIdAndUserId(long shopId, long userId);
    Collect findByUserIdAndShopId(Long userId, Long shopId);
	void addCollect(Long userId, Long shopId);
}
