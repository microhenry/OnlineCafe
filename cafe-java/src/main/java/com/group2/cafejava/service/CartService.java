package com.group2.cafejava.service;

import com.group2.cafejava.dto.QueryDTO;
import com.group2.cafejava.entity.Cart;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface CartService {
    IPage<Cart> selectCartPage(QueryDTO queryDTO);
    IPage<Cart> selectCart(QueryDTO queryDTO);

    IPage<Cart> selectCartWithSameProductId(QueryDTO queryDTO);
    void batchDelete(List<Integer> ids);
    Integer addCart(Cart cart);

    Integer updateCart(Cart cart);

    Integer deleteCart(Integer id);


}
