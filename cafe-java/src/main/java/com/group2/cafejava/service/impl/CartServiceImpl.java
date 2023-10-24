package com.group2.cafejava.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group2.cafejava.dto.QueryDTO;
import com.group2.cafejava.entity.Cart;
import com.group2.cafejava.entity.Product;
import com.group2.cafejava.entity.User;
import com.group2.cafejava.mapper.CartMapper;
import com.group2.cafejava.mapper.UserMapper;
import com.group2.cafejava.service.CartService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.cafejava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;

    @Override
    public Integer addCart(Cart cart) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("cart_id", cart.getCartId());
        Cart cart1=cartMapper.selectOne(wrapper);
        if (cart1==null){return cartMapper.insert(cart);}
        else{
            return -1;
        }
    }

    @Override
    public Integer updateCart(Cart cart) {
        return cartMapper.updateById(cart);
    }
    @Override
    public Integer deleteCart(Integer id) {
        return cartMapper.deleteById(id);
    }
    @Override
    public void batchDelete(List<Integer> ids) {
        cartMapper.deleteBatchIds(ids);
    }
    @Override
    public IPage<Cart> selectCartPage(QueryDTO queryDTO) {
        Page<Cart> page=new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return cartMapper.selectCartPage(page,queryDTO.getKeyword());
    }
    @Override
    public IPage<Cart> selectCart(QueryDTO queryDTO) {
        Page<Cart> page=new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return cartMapper.selectCart(page,queryDTO.getKeyword());
    }





}
