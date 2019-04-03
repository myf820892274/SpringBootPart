package com.yf.utils;

/**
 * 排序的封装类
 */
public class Sorter {
    private String sort;//排序字段
    private String order;//排序方式 desc asc

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
