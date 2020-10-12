package com.mkfree.hbase.example.domain;


import org.apache.hadoop.hbase.Cell;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class HBaseOrder {

    private String ID;
    private Long createdAt;
    private Long updatedAt;

    private String orderNum;
    private Double payTotalPrice;
    private Integer orderType;
    private Integer source;
    private Integer status;
    private Long customerId;
    private String customerMobile;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Double getPayTotalPrice() {
        return payTotalPrice;
    }

    public void setPayTotalPrice(Double payTotalPrice) {
        this.payTotalPrice = payTotalPrice;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }


    @Override
    public String toString() {
        return "HBaseOrder{" +
                "ID='" + ID + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", orderNum='" + orderNum + '\'' +
                ", payTotalPrice=" + payTotalPrice +
                ", orderType=" + orderType +
                ", source=" + source +
                ", status=" + status +
                ", customerId=" + customerId +
                ", customerMobile='" + customerMobile + '\'' +
                '}';
    }

}
