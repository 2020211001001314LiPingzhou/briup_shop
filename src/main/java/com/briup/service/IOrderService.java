package com.briup.service;

import com.alipay.api.AlipayApiException;
import com.briup.bean.Order;
import com.briup.bean.User;

import java.math.BigDecimal;
import java.util.List;
public interface IOrderService {

    List<Order> findUserAllOrders(Long userId);
    Order saveOrder(Long[] shopCarIds, User user, Long addressId);

    Order paySuccess(String orderId);

    Order findById(String orderId);

    void deleteOrder(String orderId, Long userId);
}
