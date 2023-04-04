package com.briup.until;

import com.briup.bean.ShopCar;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ShopCarUntil {

    public static BigDecimal sumPrice(List<ShopCar> shopCarList){
        AtomicReference<BigDecimal> sumPrice = new AtomicReference<>(new BigDecimal(0));
        shopCarList.forEach(shopCar -> {
            if (shopCar.getShop().isDiscount()){
                sumPrice.set(sumPrice.get().add(shopCar.getShop().getDiscountPrice()
                        .multiply(new BigDecimal(shopCar.getNum()))));
            }else {
                sumPrice.set(sumPrice.get().add(shopCar.getShop().getSelling_price()
                        .multiply(new BigDecimal(shopCar.getNum()))));
            }
        });

        return sumPrice.get();
    }
}
