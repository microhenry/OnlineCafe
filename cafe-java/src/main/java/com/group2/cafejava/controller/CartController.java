package com.group2.cafejava.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cafejava.dto.QueryDTO;
import com.group2.cafejava.entity.Cart;
import com.group2.cafejava.entity.CartNew;
import com.group2.cafejava.entity.Product;
import com.group2.cafejava.result.Result;
import com.group2.cafejava.service.CartService;
import com.group2.cafejava.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private MenuService menuService;

    /**
     * 分页查询
     * @param queryDTO
     * @return
     */
    @PostMapping("/api/cart/list")
    public Result cartList(@RequestBody QueryDTO queryDTO){
//        return new Result(200,"",cartService.selectCartPage(queryDTO));
        IPage<Cart> cartPage = cartService.selectCartPage(queryDTO);
        List<Cart> cartSelect = cartPage.getRecords();
        long totalRecords = cartPage.getTotal(); // Get the number of total records
        Result result = new Result(200, "", cartSelect, totalRecords);   // Set total records to result
        return result;  // Return result with code, message, data, and total records
    }

    @PostMapping("/api/cartNew/list")
    public Result cartNewList(@RequestBody QueryDTO queryDTO){


        IPage<Cart> cartPage = cartService.selectCartPage(queryDTO);
        List<Cart> cartSelect = cartPage.getRecords();
        long totalRecords = cartPage.getTotal(); // Get the number of total records
//        List<CartNew> cartNewSelect = new ArrayList<>(cartSelect.size());
        List<CartNew> cartNewSelect = new ArrayList<>();

        for (int i = 0; i < cartSelect.size(); i++) {
            QueryDTO queryProductDTO = new QueryDTO();
            queryProductDTO.setPageNo(1);
            queryProductDTO.setPageSize(1000);
            String productId = String.valueOf(cartSelect.get(i).getProductId());
            queryProductDTO.setKeyword(productId);


            IPage<Product> productPage = menuService.selectProductById(queryProductDTO);
            List<Product> productSelect = productPage.getRecords();
            Product product = productSelect.get(0);

            // Add new cart item to cartNewSelect
            cartNewSelect.add(new CartNew(cartSelect.get(i).getCartId(),
                    cartSelect.get(i).getProductId(),
                    cartSelect.get(i).getProductNum(),
                    cartSelect.get(i).getUserId(),
                    product.getProductName(),
                    cartSelect.get(i).getProductSize(),
                    product.getProductPrice()));
        }
        Result result = new Result(200, "", cartNewSelect, totalRecords);
        return result;


    }


    @PostMapping("/api/cart/cartNumber/{userId}")
    public Result cartNumber(@PathVariable Integer userId){
        QueryDTO queryDTO=new QueryDTO();
        queryDTO.setPageNo(1);
        queryDTO.setPageSize(1000);
        queryDTO.setKeyword(userId.toString());
        IPage<Cart> cartPage = cartService.selectCartPage(queryDTO);
        long totalRecords = cartPage.getTotal(); // Get the number of total records
        return new Result(200, "",totalRecords);  // Return result with code, message, data, and total records
    }





//    @PostMapping("/api/user/detail/{loginName}")
//    public Result userDetail(@PathVariable String loginName){
//        QueryDTO queryDTO=new QueryDTO();
//        queryDTO.setPageNo(1);
//        queryDTO.setPageSize(100);
//        queryDTO.setKeyword(loginName);
//        List<User> userSelect=userService.selectUser(queryDTO).getRecords();
//        User select=new User();
//        for (User i:userSelect){
//            if (i.getLoginName().equals(loginName)){
//                select=i;
//                break;
//            }
//        }
//        return new Result(200,"",select);
//    }
    /**
     * 添加
     * @param cart
     * @return
     */
    @PostMapping("/api/cart/add")
    public Result addCart(@RequestBody Cart cart){

        return new Result(200,"",cartService.addCart(cart));
    }

    /**
     * 更新
     * @param cart
     * @return
     */
    @PostMapping("/api/cart/update")
    public Result updateCart(@RequestBody Cart cart){
        return new Result(200,"",cartService.updateCart(cart));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/api/cart/delete")
    public Result deleteCart(Integer id){
        return new Result(200,"",cartService.deleteCart(id));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/api/cart/delete/batch")
    public Result batchDeleteCart(@RequestBody List<Integer> ids){
        cartService.batchDelete(ids);
        return new Result(200,"","");
    }

}