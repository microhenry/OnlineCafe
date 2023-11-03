package com.group2.cafejava.vo;

import com.group2.cafejava.entity.Staff;

import java.io.Serializable;

public class LoginStaffVO implements Serializable {
    private Integer id;
    private String token;
    private Staff staff;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
