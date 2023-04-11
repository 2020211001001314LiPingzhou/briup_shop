package com.briup.until;

import com.briup.bean.ShopCar;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ShopCarUntil {

    public static BigDecimal sumPrice(List<ShopCar> shopCarList){
        /*AtomicReference<BigDecimal> sumPrice = new AtomicReference<>(new BigDecimal(0));
        shopCarList.forEach(shopCar -> {
            if (shopCar.getShop().isDiscount()){
                sumPrice.set(sumPrice.get().add(shopCar.getShop().getDiscountPrice()
                        .multiply(new BigDecimal(shopCar.getNum()))));
            }else {
                sumPrice.set(sumPrice.get().add(shopCar.getShop().getSelling_price()
                        .multiply(new BigDecimal(shopCar.getNum()))));
            }
        });

        return sumPrice.get();*/
        // 为什么用shopCarList.forEach(shopCar -> {... sumPrice赋值时会报错，而用普通foreach就不会？
        BigDecimal sumPrice = new BigDecimal(0);
        for (ShopCar shopCar : shopCarList) {
            if (shopCar.getShop().isDiscount()){
                sumPrice = sumPrice.add(shopCar.getShop().getDiscountPrice().multiply(new BigDecimal(shopCar.getNum())));
            }else {
                sumPrice = sumPrice.add(shopCar.getShop().getSelling_price().multiply(new BigDecimal(shopCar.getNum())));
            }
        }

        return sumPrice;
    }
}
