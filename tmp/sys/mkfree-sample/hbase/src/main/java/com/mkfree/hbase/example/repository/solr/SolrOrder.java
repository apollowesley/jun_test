package com.mkfree.hbase.example.repository.solr;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.math.BigDecimal;

@SolrDocument(collection = "t_order_collection")
public class SolrOrder {

    @Indexed(name = "id", type = "string")
    private String id;
    @Indexed(name = "ID", type = "string")
    private String ID;
    @Indexed(name = "customerId", type = "long")
    private Long customerId;
    @Indexed(name = "customerMobile", type = "string")
    private String customerMobile;
    @Indexed(name = "payTotalPrice", type = "string")
    private Double payTotalPrice;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Double getPayTotalPrice() {
        return payTotalPrice;
    }

    public void setPayTotalPrice(Double payTotalPrice) {
        this.payTotalPrice = payTotalPrice;
    }

    @Override
    public String toString() {
        return "SolrOrder{" +
                "id='" + id + '\'' +
                ", ID='" + ID + '\'' +
                ", customerId=" + customerId +
                ", customerMobile='" + customerMobile + '\'' +
                ", payTotalPrice=" + payTotalPrice +
                '}';
    }
}
