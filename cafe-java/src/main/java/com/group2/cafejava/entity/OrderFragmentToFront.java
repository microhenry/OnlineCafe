package com.group2.cafejava.entity;

import java.util.Date;
import java.util.List;


public class OrderFragmentToFront {
    private Integer orderId;
    private Integer orderCustomerId;
    private Double orderPrice;
    private String orderComment;
    private String orderTime;
    private String orderAddress;
    private String orderStatus;
    private List<OrderInfo> orderInfos;

    public OrderFragmentToFront() {

    }

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

    public List<OrderInfo> getOrderInfos() {
        return orderInfos;
    }

    public void setOrderInfos(List<OrderInfo> orderInfos) {
        this.orderInfos = orderInfos;
    }

    public OrderFragmentToFront(Integer orderId, Integer orderCustomerId, Double orderPrice, String orderComment, String orderTime, String orderAddress, String orderStatus, List<OrderInfo> orderInfos) {
        this.orderId = orderId;
        this.orderCustomerId = orderCustomerId;
        this.orderPrice = orderPrice;
        this.orderComment = orderComment;
        this.orderTime = orderTime;
        this.orderAddress = orderAddress;
        this.orderStatus = orderStatus;
        this.orderInfos = orderInfos;
    }
}
