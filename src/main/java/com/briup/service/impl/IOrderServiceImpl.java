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

    @Resource
    private AliPayUtil aliPayUtil;

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

        // 插入成功返回Order
        return order;
    }

    @Override
    public void paySuccess(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public Order findById(String orderId) {
        return orderDao.findOrderByOrderId(orderId);
    }

    @Override
    public String orderPay(Order order) {

        // 调用支付宝
        AliPay aliPay = new AliPay();
        aliPay.setOut_trade_no(order.getId());
        aliPay.setSubject("充值:" + order.getSumPrice());
        aliPay.setTotal_amount(order.getSumPrice().toString());
        String pay = aliPayUtil.pay(aliPay);
        System.out.println("pay:" + pay);
        return pay;
    }
}

