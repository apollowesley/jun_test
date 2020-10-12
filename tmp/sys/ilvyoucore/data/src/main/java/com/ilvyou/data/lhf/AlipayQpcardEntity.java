package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/17.
 */
@Data
public class AlipayQpcardEntity {
    private Long pkid;
    private Long actid;
    private String actname;
    private String realname;
    private Integer bankid;
    private String bankcode;
    private String bankname;
    private String banknumber;
    private String banktype;
    private String duemonth;
    private String dueyear;
    private String authcode;
    private String credtype;
    private String credcode;
    private String buyertel;
    private String buyername;
    private String companyid;
    private String companycode;
    private String companyname;
    private String companyfname;
    private String companyaccount;
    private String companymobile;
    private String identityid;
    private String identitytype;
    private Short enableflag;
    private Short deleteflag;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
    private String channletype;
}
