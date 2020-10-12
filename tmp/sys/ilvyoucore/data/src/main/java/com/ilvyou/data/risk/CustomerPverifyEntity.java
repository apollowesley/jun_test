package com.ilvyou.data.risk;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by admin on 2016/10/31.
 */
@Data
public class CustomerPverifyEntity {
    private long companyid;
    private String companyname;
    private String companycode;
    private long storeid;
    private String storename;
    private String storecode;
    private Integer agentcategory;
    private long userid;
    private String username;
    private String usercode;
    private Integer totalcheckorder;
    private Integer totalpassorder;
    private double passratio;
    private Integer totalorderperson;
    private Integer totalcheckperson;
    private Integer totalrealperson;
    private double realratio;
    private double cost;
    private String createdname;
    private Timestamp createdon;
    private String createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedip;
}
