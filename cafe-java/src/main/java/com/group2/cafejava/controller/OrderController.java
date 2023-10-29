package com.group2.cafejava.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cafejava.dto.QueryDTO;
import com.group2.cafejava.entity.*;
import com.group2.cafejava.mapper.CartMapper;
import com.group2.cafejava.mapper.OrderMapper;
import com.group2.cafejava.mapper.ProductMapper;
import com.group2.cafejava.mapper.UserMapper;
import com.group2.cafejava.result.Result;
import com.group2.cafejava.service.CartService;
import com.group2.cafejava.service.OrderService;
import com.group2.cafejava.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;


@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UserMapper userMapper;


    //全部order(管理员)
    @PostMapping("/api/user/order/list")
    public Result orderList(@RequestBody QueryDTO queryDTO) {
        IPage<Order> orderPage = orderService.selectOrderPage(queryDTO);
        List<Order> orderSelect = orderPage.getRecords();
        List<OrderFragmentToFront> orderFragmentToFronts=convertToFront(orderSelect);
        return new Result(200, "", orderFragmentToFronts);
    }
    //单人order（顾客）
    @PostMapping("/api/order/list")
    public Result orderOneCustomer(@RequestBody QueryDTO queryDTO) {
        IPage<Order> orderPage = orderService.selectOrderPageOneCustomer(queryDTO);
        List<Order> orderSelect = orderPage.getRecords();
        List<OrderFragmentToFront> orderFragmentToFronts=convertToFront(orderSelect);
        return new Result(200, "", orderFragmentToFronts);
    }
//单个order细节（全部都可用）
    @PostMapping("/api/order/list/detail")
    public Result orderDetail(@RequestBody QueryDTO queryDTO) {
        //QueryDTO queryDTO=new QueryDTO(1,10,orderId);
        IPage<Order> orderPage = orderService.selectOrder(queryDTO);
        List<Order> orderSelect = orderPage.getRecords();
        OrderFragmentToFront orderFragmentToFronts=convertToFront(orderSelect).get(0);
        return new Result(200, "", orderFragmentToFronts);
    }


    /**
     * 添加
     */

    @PostMapping("/api/order/add")
    public Result addOrder(@RequestBody OrderFragmentFromFront orderFragmentFromFront) {
        List<Integer> ids= orderFragmentFromFront.getCartIds();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
       // sdf.applyPattern();
        Date date = new Date();// 获取当前时间
        Order order = new Order();
        order.setOrderCustomerId(orderFragmentFromFront.getUserId());
        order.setOrderComment(orderFragmentFromFront.getOrderComment());
        order.setOrderTime(sdf.format(date));
        order.setOrderAddress(orderFragmentFromFront.getOrderAddress());
        order.setOrderStatus("In Process");

        StringBuilder orderInfoName= new StringBuilder();
        StringBuilder orderInfoSize=new StringBuilder();
        StringBuilder orderInfoNumber=new StringBuilder();
        StringBuilder orderInfoPrice=new StringBuilder();
        int count=1;
        double orderPrice=0;
        for (Integer i:ids){
            Cart cart=cartMapper.selectById(i);
            Integer productid=cart.getProductId();
            Double price=productMapper.selectById(productid).getProductPrice();
           switch (cart.getProductSize()){
               case "M":
                   price+=2;
                   break;
               case "L":
                   price+=4;
                   break;
               default:
                   break;
            }
            orderInfoName.append(cart.getProductName());
            orderInfoSize.append(cart.getProductSize());
            orderInfoNumber.append(cart.getProductNum());
            orderInfoPrice.append(price.toString());
            orderPrice+=price*cart.getProductNum();
            if (count!=ids.size()){
                count++;
                orderInfoName.append(",");
                orderInfoSize.append(",");
                orderInfoNumber.append(",");
                orderInfoPrice.append(",");
            }
            cartMapper.deleteById(cart.getCartId());
        }
        order.setOrderPrice(orderPrice);
        order.setOrderInfoName(String.valueOf(orderInfoName));
        order.setOrderInfoSize(String.valueOf(orderInfoSize));
        order.setOrderInfoNumber(String.valueOf(orderInfoNumber));
        order.setOrderInfoPrice(String.valueOf(orderInfoPrice));
        User user=userMapper.selectById(orderFragmentFromFront.getUserId());
        Double money=user.getMoney();
        money-=orderPrice;
        if (money<0){
            return new Result(200, "not enough money", -1);
        }
        else {
            user.setMoney(money);
            userMapper.updateById(user);
        return new Result(200, "", orderService.addOrder(order));}

    }

//    /**
//     * 更新
//     *
//     * @param product
//     * @return
//     */
    @PostMapping("/api/order/update")
    public Result updateOrder(@RequestBody Order order) {
        Order order1=orderMapper.selectById(order.getOrderId());
        if (order1.getOrderStatus().equals("In Process")){
        if (!order.getOrderAddress().equals("null")){order1.setOrderAddress(order.getOrderAddress());}
        if (!order.getOrderComment().equals("null")){order1.setOrderComment(order.getOrderComment());}}

        if (!order.getOrderStatus().equals("null")){order1.setOrderStatus(order.getOrderStatus());}
        return new Result(200, "", orderService.updateOrder(order1));
    }
//
//    /**
//     * 删除
//     *
//     * @param id
//     * @return
//     */
    @PostMapping("/api/order/delete")
    public Result deleteOrder(Integer id) {
        return new Result(200, "", orderService.deleteOrder(2));
    }
//
//    /**
//     * 批量删除
//     *
//     * @param ids
//     * @return
//     */
    @PostMapping("/api/order/delete/batch")
    public Result batchDeleteProduct(@RequestBody List<Integer> ids) {
        orderService.batchDelete(ids);
        return new Result(200, "", "");
    }
    public List<OrderFragmentToFront> convertToFront(List<Order> orders){
if (orders.isEmpty()){
    return null;
}
        List<OrderFragmentToFront> convertedList= new ArrayList<>();

        for (Order orderTmp: orders){
            OrderFragmentToFront orderFragmentToFront=new OrderFragmentToFront();
            List<OrderInfo> orderInfos= new ArrayList<>();
            String[] splitInfoName=orderTmp.getOrderInfoName().split(",");
            String[] splitInfoSize=orderTmp.getOrderInfoSize().split(",");
            String[] splitInfoNumber=orderTmp.getOrderInfoNumber().split(",");
            String[] splitInfoPrice=orderTmp.getOrderInfoPrice().split(",");
            int count=0;
            while (count!=splitInfoPrice.length){
                OrderInfo orderInfo=new OrderInfo();
                orderInfo.setName(splitInfoName[count]);
                orderInfo.setSize(splitInfoSize[count]);
                orderInfo.setNumber(Integer.parseInt(splitInfoNumber[count]));
                orderInfo.setPrice(Double.parseDouble(splitInfoPrice[count]));
                orderInfos.add(orderInfo);
                count++;
            }
            orderFragmentToFront.setOrderInfos(orderInfos);
            orderFragmentToFront.setOrderId(orderTmp.getOrderId());
            orderFragmentToFront.setOrderCustomerId(orderTmp.getOrderCustomerId());
            orderFragmentToFront.setOrderPrice(orderTmp.getOrderPrice());
            orderFragmentToFront.setOrderComment(orderTmp.getOrderComment());
            orderFragmentToFront.setOrderTime(orderTmp.getOrderTime());
            orderFragmentToFront.setOrderAddress(orderTmp.getOrderAddress());
            orderFragmentToFront.setOrderStatus(orderTmp.getOrderStatus());
            convertedList.add(orderFragmentToFront);
        }
        return convertedList;
    }
}

