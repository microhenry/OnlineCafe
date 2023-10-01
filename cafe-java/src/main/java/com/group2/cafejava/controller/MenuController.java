package com.group2.cafejava.controller;


import com.group2.cafejava.dto.QueryDTO;
import com.group2.cafejava.result.Result;
import com.group2.cafejava.service.MenuService;
import com.group2.cafejava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/api/menu/{category}")
    public Result ShowMenu(@PathVariable String category){
        QueryDTO queryDTO=new QueryDTO();
        queryDTO.setPageNo(1);
        queryDTO.setPageSize(15);   //TODO: PageSize和PageNo的值不应该为硬编码，可能需要从前端获取
        queryDTO.setKeyword(category);
        if (Objects.equals(category, "All")){queryDTO.setKeyword("");}
        return new Result(200,"",menuService.selectProductPage(queryDTO));
    }
}
