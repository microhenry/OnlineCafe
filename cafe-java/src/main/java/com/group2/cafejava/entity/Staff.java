package com.group2.cafejava.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "staff")
public class Staff {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String loginName;
    private String userName;
    private String password;
    private String sex;
    private String email;
    private String address;
    private double money;
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Staff() {
    }

    public Staff(String loginName, String userName, String password, String sex, String email, String address) {
        this.loginName = loginName;
        this.userName = userName;
        this.password = password;
        this.sex = sex;
        this.email = email;
        this.address = address;
        this.money=1000.0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", money=" + money +
                '}';
    }
}
