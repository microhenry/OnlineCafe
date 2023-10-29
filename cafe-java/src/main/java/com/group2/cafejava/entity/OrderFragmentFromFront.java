package com.group2.cafejava.entity;

import java.util.List;


public class OrderFragmentFromFront {
    private List<Integer> cartIds;
    private Integer userId;
    private String orderComment;
    private String orderAddress;

    public List<Integer> getCartIds() {
        return cartIds;
    }

    public void setCartIds(List<Integer> cartIds) {
        this.cartIds = cartIds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }



    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public OrderFragmentFromFront(List<Integer> cartIds, Integer userId, String orderComment, String orderAddress) {
        this.cartIds = cartIds;
        this.userId = userId;
        this.orderComment = orderComment;
        this.orderAddress = orderAddress;
    }
}
