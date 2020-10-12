package com.dtdream.rdic.saas.service;

/**
 * Created by Ozz8 on 2015/06/26.
 */
public class ServiceList<T> {
    public Long total;
    public T data;

    public ServiceList () {
    }

    public ServiceList (Long total, T data) {
        this.total = total;
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
