package com.group2.cafejava.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "product")
public class Product {
    @TableId(type = IdType.AUTO)
    private Integer productId;
    private String productName;
    private String productCategory;
    private String productDescription;
    private String productPicUrl;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPicUrl() {
        return productPicUrl;
    }

    public void setProductPicUrl(String productPicUrl) {
        this.productPicUrl = productPicUrl;
    }

    public Product() {
    }

    public Product(String product_name, String category, String description, String picUrl) {
        this.productName = product_name;
        this.productCategory = category;
        this.productDescription = description;
        this.productPicUrl = picUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + productId +
                ", productName='" + productName + '\'' +
                ", category='" + productCategory + '\'' +
                ", description='" + productDescription + '\'' +
                ", picUrl='" + productPicUrl + '\'' + '}';
    }
}
