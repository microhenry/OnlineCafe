package com.group2.cafejava.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group2.cafejava.entity.Product;
import com.group2.cafejava.mapper.ProductMapper;
import com.group2.cafejava.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Integer addProduct(Product product) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("product_name", product.getProductName());
        Product product1=productMapper.selectOne(wrapper);
        if (product1==null){return productMapper.insert(product);}
        else{
            return -1;
        }
    }

    @Override
    public Integer updateProduct(Product product) {
        return productMapper.updateById(product);
    }


    @Override
    public Integer deleteProduct(Integer id) {
        return productMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        productMapper.deleteBatchIds(ids);
    }



}
