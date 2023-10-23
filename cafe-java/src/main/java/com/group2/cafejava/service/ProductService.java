package com.group2.cafejava.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cafejava.dto.QueryDTO;
import com.group2.cafejava.entity.Product;
import com.group2.cafejava.entity.User;

import java.util.List;

public interface ProductService {



    Integer addProduct(Product product);

    Integer updateProduct(Product product);

    Integer deleteProduct(Integer id);

    void batchDelete(List<Integer> ids);

}
