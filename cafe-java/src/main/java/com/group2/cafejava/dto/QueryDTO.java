package com.group2.cafejava.dto;

// Used to pass query parameters to the controller
public class QueryDTO {
    private Integer pageNo;
    private Integer pageSize;
    private String keyword;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public QueryDTO(Integer pageNo, Integer pageSize, String keyword) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.keyword = keyword;
    }

    public QueryDTO() {
    }
}
