package com.ilvyou.data.lvzan;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/17.
 */
@Data
public class AppUserEntity {
    private Long companyid;
    private String companycode;
    private String companyname;
    private Long id;
    private String usecode;
    private String loginname;
    private String usename;
    private String password;
    private String salt;
    private Time lastedtime;
    private Short accounttype;
    private Short isaccountcompany;
    private Short isaccounting;
    private Short issupper;
    private Short agentcategory;
    private String suppercode;
    private Short status;
    private String remarks;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedname;
    private String lastedip;
    private Long storeid;
    private String storescode;
    private String storesname;
    private Long lhfactid;
    private String lfhactname;
    private String lhfrealname;
    private String lhfnickname;
    private String lhfmobile;
    private String lhfemail;
    private String email;
}
