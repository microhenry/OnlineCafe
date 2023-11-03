package com.group2.cafejava.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.cafejava.dto.QueryDTO;
import com.group2.cafejava.entity.Product;
import com.group2.cafejava.mapper.ProductMapper;
import com.group2.cafejava.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public IPage<Product> selectProductPage(QueryDTO queryDTO) {
        Page<Product> page=new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return productMapper.selectProductPage(page,queryDTO.getKeyword());
    }
    @Override
    public IPage<Product> selectProduct(String productname) {
        Page<Product> page=new Page<>(1,1);
        return productMapper.selectProduct(page,productname);
    }

    @Override
    public IPage<Product> selectProductRough(String productname) {
        Page<Product> page=new Page<>(1,100);
        return productMapper.selectProduct(page,productname);

    }

    public IPage<Product> selectProductById(QueryDTO queryDTO) {
        Page<Product> page=new Page<>(queryDTO.getPageNo(),queryDTO.getPageSize());
        return productMapper.selectProductById(page,queryDTO.getKeyword());
    }

}
