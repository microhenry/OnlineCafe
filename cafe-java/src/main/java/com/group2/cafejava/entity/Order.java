package com.group2.cafejava.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName(value = "order1")
public class Order {


    @TableId(type = IdType.AUTO)
    private Integer orderId;
    private Integer orderCustomerId;
    private Double orderPrice;
    private String orderComment;
    private String orderTime;
    private String orderInfoName;
    private String orderInfoSize;
    private String orderInfoNumber;
    private String orderInfoPrice;
    private String orderAddress;
    private String orderStatus;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }



    public Integer getOrderCustomerId() {
        return orderCustomerId;
    }

    public void setOrderCustomerId(Integer orderCustomerId) {
        this.orderCustomerId = orderCustomerId;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderInfoName() {
        return orderInfoName;
    }

    public void setOrderInfoName(String orderInfoName) {
        this.orderInfoName = orderInfoName;
    }

    public String getOrderInfoSize() {
        return orderInfoSize;
    }

    public void setOrderInfoSize(String orderInfoSize) {
        this.orderInfoSize = orderInfoSize;
    }

    public String getOrderInfoNumber() {
        return orderInfoNumber;
    }

    public void setOrderInfoNumber(String orderInfoNumber) {
        this.orderInfoNumber = orderInfoNumber;
    }

    public String getOrderInfoPrice() {
        return orderInfoPrice;
    }

    public void setOrderInfoPrice(String orderInfoPrice) {
        this.orderInfoPrice = orderInfoPrice;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Order() {
    }

    public Order(Integer orderId,  Integer orderCustomerId, Double orderPrice, String orderComment, String orderTime, String orderInfoName, String orderInfoSize, String orderInfoNumber, String orderInfoPrice, String orderAddress, String orderStatus) {
        this.orderId = orderId;
        this.orderCustomerId = orderCustomerId;
        this.orderPrice = orderPrice;
        this.orderComment = orderComment;
        this.orderTime = orderTime;
        this.orderInfoName = orderInfoName;
        this.orderInfoSize = orderInfoSize;
        this.orderInfoNumber = orderInfoNumber;
        this.orderInfoPrice = orderInfoPrice;
        this.orderAddress = orderAddress;
        this.orderStatus = orderStatus;
    }
}
