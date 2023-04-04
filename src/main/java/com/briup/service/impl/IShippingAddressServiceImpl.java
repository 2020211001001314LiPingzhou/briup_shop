package com.briup.service.impl;

import com.briup.bean.ShippingAddress;
import com.briup.dao.IShippingAddressDao;
import com.briup.service.IShippingAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IShippingAddressServiceImpl implements IShippingAddressService {
    @Resource
    private IShippingAddressDao shippingAddressDao;

    @Override
    public void saveShippingAddress(ShippingAddress shippingAddress) {

    }

    @Override
    public List<ShippingAddress> findUserAllShippingAddress(Long user_id) {
        return shippingAddressDao.findByUserId(user_id);
    }
}
