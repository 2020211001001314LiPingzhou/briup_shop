package com.briup.dao;

import com.briup.bean.ShopCar;

import java.util.List;
public interface IShopCarDao{
	ShopCar findByShopIdAndUserId(long shopId,long userId);

	void updateShopCar(long id, int num);

	List<ShopCar> findByUserId(long id);

	void deleteById(long id);

	void saveByShopIdUserId(int num, long userId, long shopId);

	// 参数是一个list，要使用到循环标签了
	List<ShopCar> findShopCarByIds(List<Long> list);
	// 查单个购物车
	//Integer findShopNumCarById(long id);

	void deleteShopCarByIds(List<Long> list);

	ShopCar findByUserIdShopId(Long userId, Long shopId);
}
