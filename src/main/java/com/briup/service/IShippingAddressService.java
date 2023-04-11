package com.briup.service;


import com.briup.bean.ShippingAddress;

import java.util.List;

public interface IShippingAddressService {
    void saveShippingAddress(ShippingAddress shippingAddress);
    List<ShippingAddress> findUserAllShippingAddress(Long user_id);

    boolean delShippingAddress(Long shippingAddressId, Long userId);

    boolean setDefaultAddress(Long shippingAddressId, Long userId);
}
