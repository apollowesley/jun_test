package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/8.
 */
@Data
public class ScrUserEntity {
    private long companyid;
    private String companycode;
    private String companyname;
    private long userid;
    private String usecode;
    private String loginname;
    private String usename;
    private String realname;
    private String lpemail;
    private String lpmobile;
    private String lpidcard;
    private String financialmobile;
    private String commonmobile;
    private String licnumber;
    private String industrynumber;
    private String taxnumber;
    private String createdname;
    private Timestamp createdon;
    private long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private long lastedby;
    private String lastedip;
    private Short bppagentcategory;
    private String registrationnum;
    private String bppcompanyfname;
    private Integer provinceid;
    private String provincename;
    private Integer cityid;
    private String cityname;
    private String address;
    private String tel;
    private String fax;
    private Integer areaid;
    private String areaname;
    private Short bppaccounttype;
    private Long storeid;
    private String storescode;
    private String storesname;
    private String lpidurl;
    private Timestamp birthdaydate;
    private String licurl;
    private String taxurl;
}
