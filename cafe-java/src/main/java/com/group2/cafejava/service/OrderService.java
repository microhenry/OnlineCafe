package com.group2.cafejava.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cafejava.dto.QueryDTO;
import com.group2.cafejava.entity.Cart;
import com.group2.cafejava.entity.Order;

import java.util.List;

public interface OrderService {



    Integer addOrder(Order order);

    Integer updateOrder(Order order);


    Integer deleteOrder(Integer id);

    void batchDelete(List<Integer> ids);
    IPage<Order> selectOrderPage(QueryDTO queryDTO);
    IPage<Order> selectOrderPageOneCustomer(QueryDTO queryDTO);
    IPage<Order> selectOrder(QueryDTO queryDTO);
}
