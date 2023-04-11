package com.briup.dao;


import com.briup.bean.ShippingAddress;

import java.util.List;
public interface IShippingAddressDao{
    List<ShippingAddress> findByUserId(Long userId);

    void saveShippingAddress(ShippingAddress shippingAddress);

    boolean delShippingAddress(Long shippingAddressId, Long userId);

    //ShippingAddress findByUserIdDefaultValue(Long userId, boolean defaultValue);

    boolean updateDefaultValue(Long shippingAddressId, boolean oldDefaultValue, boolean newDefaultValue, Long userId);
}
