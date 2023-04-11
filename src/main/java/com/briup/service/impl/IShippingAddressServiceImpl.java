package com.briup.service.impl;

import com.briup.bean.ShippingAddress;
import com.briup.dao.IShippingAddressDao;
import com.briup.service.IShippingAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IShippingAddressServiceImpl implements IShippingAddressService {
    @Resource
    private IShippingAddressDao shippingAddressDao;

    @Override
    public void saveShippingAddress(ShippingAddress shippingAddress) {
        shippingAddress.setDefaultValue(false);
        shippingAddressDao.saveShippingAddress(shippingAddress);
    }

    @Override
    public List<ShippingAddress> findUserAllShippingAddress(Long user_id) {
        return shippingAddressDao.findByUserId(user_id);
    }

    @Override
    public boolean delShippingAddress(Long shippingAddressId, Long userId) {
        // 仅通过shippingAddressId就能删除数据，但是存在安全问题，还要验证是否为对应用户
        return shippingAddressDao.delShippingAddress(shippingAddressId, userId);
    }

    @Override
    @Transactional
    public boolean setDefaultAddress(Long shippingAddressId, Long userId) {

        // 将原默认地址defaultValue改为false
        if (shippingAddressDao.updateDefaultValue(null, true, false, userId)){
            // 再将新地址defaultValue改为true
            return shippingAddressDao.updateDefaultValue(shippingAddressId, false, true, userId);
        }
        return false;
    }
}
