package com.group2.cafejava.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.cafejava.entity.Cart;

public interface CartMapper extends BaseMapper<Cart> {
    IPage<Cart> selectCartPage(Page<Cart> page,String keyword);
    IPage<Cart> selectCart(Page<Cart> page,String keyword);

    IPage<Cart> selectCartWithSameProductId(Page<Cart> page,String keyword);
}
