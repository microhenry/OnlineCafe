package com.group2.cafejava.result;

import java.io.Serializable;

public class Result implements Serializable {
    //响应码
    private Integer code;
    //信息
    private String message;
    //返回数据
    private Object data;
    // Length of the records
    private long totalRecords;

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Integer code, String message, Object data, long totalRecords) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.totalRecords = totalRecords;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getTotalRecords() {
        return totalRecords;
    }
    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

}
