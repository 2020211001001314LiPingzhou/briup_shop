package com.briup.service.impl;

import com.alipay.api.AlipayApiException;
import com.briup.bean.*;
import com.briup.dao.IOrderDao;
import com.briup.dao.IOrderItemDao;
import com.briup.dao.IShippingAddressDao;
import com.briup.dao.IShopCarDao;
import com.briup.service.IOrderService;
import com.briup.until.AliPayUtil;
import com.briup.until.ShopCarUntil;
import com.briup.until.AliPayUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class IOrderServiceImpl implements IOrderService {

    @Resource
    private IOrderDao orderDao;

    @Resource
    private IShopCarDao shopCarDao;

    @Resource
    private IOrderItemDao orderItemDao;

    @Override
    public List<Order> findUserAllOrders(Long userId) {
        return orderDao.findByUserId(userId);
    }

    @Override
    @Transactional
    public Order saveOrder(Long[] shopCarIds, User user, Long addressId) {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(addressId);

        Order order = new Order(user, shippingAddress);

        // 设置ShopItem，里面包含了计算总价
        List<Long> shopCarIdList = Arrays.asList(shopCarIds);
        List<ShopCar> shopCarList = shopCarDao.findShopCarByIds(shopCarIdList);
        List<OrderItem> orderItemList = new ArrayList<>();
        // shopCarList中shopCar的shop、num属性封装到orderItemList，orderItem的构造方法解析了shopCar的属性
        shopCarList.forEach(shopCar -> orderItemList.add(new OrderItem(shopCar)));
        order.setOrderItems(orderItemList);

        // 设置订单状态
        order.setStatus(1);
        System.out.println("order ====" + order);

        // 插入订单 和 订单项，顺序不能反，因为订单项的order_id外键依赖订单id
        orderDao.saveOrder(order);
        orderItemList.forEach(orderItem -> {
            orderItem.setOrder(order);
            orderItemDao.saveOrderItem(orderItem);
        });

        // 将购物车中相应的商品记录删除
        shopCarDao.deleteShopCarByIds(shopCarIdList);

        // 插入成功返回Order
        return order;
    }

    @Override
    public Order paySuccess(String orderId) {
        Order order = orderDao.findOrderByOrderId(orderId);
        order.setStatus(2);
        orderDao.updateOrder(order);
        return order;
    }

    @Override
    public Order findById(String orderId) {
        return orderDao.findOrderByOrderId(orderId);
    }




    @Override
    @Transactional
    public void deleteOrder(String orderId, Long userId) {
        // 查询order
        Order order = orderDao.findOrderByOrderId(orderId);
        // 判断订单是否存在
        if (order == null || order.getId() == null) {
            return;
        }
        // 判断order是否为该用户的order
        if (!userId.equals(order.getUser().getId())) {
            return;
        }

        // 删除orderItem
        List<OrderItem> orderItems = order.getOrderItems();
        orderItemDao.deleteOrderItem(orderItems);
        // 删除order
        orderDao.deleteOrder(orderId);
    }
}

