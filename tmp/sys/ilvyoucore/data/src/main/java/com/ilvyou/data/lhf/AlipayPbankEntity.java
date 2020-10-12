package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/17.
 */
@Data
public class AlipayPbankEntity {
    private Long actid;
    private String actname;
    private String realname;
    private Integer bankid;
    private String bankcode;
    private String bankname;
    private String banknumber;
    private String banktype;
    private String provincename;
    private String cityname;
    private String bankbranch;
    private String duemonth;
    private String dueyear;
    private String authcode;
    private Short accounttype;
    private String pname;
    private String pidcard;
    private String pmobile;
    private String companyid;
    private String companycode;
    private String companyname;
    private String companyfname;
    private String companyaccount;
    private String companymobile;
    private Short defaultflag;
    private Short enableflag;
    private Short lockflag;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
    private Integer rechargeflag;
}
