package com.briup.dao;


import com.briup.bean.OrderItem;

import java.util.List;

public interface IOrderItemDao{
    void saveOrderItem(OrderItem orderItem);

    void deleteOrderItem(List<OrderItem> orderItemList);
}
