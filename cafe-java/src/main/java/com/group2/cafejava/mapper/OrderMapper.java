package com.group2.cafejava.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.cafejava.entity.Order;
import com.group2.cafejava.entity.Product;

public interface OrderMapper extends BaseMapper<Order> {
    IPage<Order> selectOrderPage(Page<Order> page,String keyword);
    IPage<Order> selectOrderPageOneCustomer(Page<Order> page,String keyword);
    IPage<Order> selectOrder(Page<Order> page,String keyword);
    IPage<Order> selectOrderById(Page<Order> page,String keyword);
}
