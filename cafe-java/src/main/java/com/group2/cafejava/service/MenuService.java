package com.group2.cafejava.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cafejava.dto.LoginDTO;
import com.group2.cafejava.dto.QueryDTO;
import com.group2.cafejava.entity.Product;
import com.group2.cafejava.entity.User;
import com.group2.cafejava.result.Result;

public interface MenuService {
    IPage<Product> selectProductPage(QueryDTO queryDTO);
}
