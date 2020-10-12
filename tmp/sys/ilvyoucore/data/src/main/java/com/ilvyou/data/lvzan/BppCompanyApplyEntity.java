package com.ilvyou.data.lvzan;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/17.
 */
@Data
public class BppCompanyApplyEntity {
    private Long id;
    private Integer openid;
    private Integer category;
    private Integer isheadquarter;
    private String companyname;
    private String departmentname;
    private String brandname;
    private Integer provinceid;
    private String provincename;
    private Integer cityid;
    private String cityname;
    private Integer countyid;
    private String countyname;
    private String address;
    private String telnum;
    private String fax;
    private String introduction;
    private String contact;
    private String contactphone;
    private String contactqq;
    private String contactemail;
    private String businessnum;
    private String licensenum;
    private String taxnum;
    private String registrationnum;
    private String bustypename;
    private String bustypecode;
    private String createdname;
    private Timestamp createdon;
    private String createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedip;
    private Integer state;
    private String companyshortname;
    private String kindname;
    private String kindcode;
    private String remarks;
    private String password;
    private String salt;
    private String loginname;
}
