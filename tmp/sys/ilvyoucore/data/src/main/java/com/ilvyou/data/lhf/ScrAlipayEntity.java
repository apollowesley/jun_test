package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/16.
 */
@Data
public class ScrAlipayEntity {
    private Long actid;
    private String actname;
    private String realname;
    private String nickname;
    private String password;
    private String salt;
    private String idcard;
    private Short idcardflag;
    private String mobile;
    private Short mobileflag;
    private String email;
    private Short emailflag;
    private String faceurl;
    private Short accounttype;
    private Integer loginpoint;
    private Short regextractflag;
    private Integer regpoint;
    private Timestamp lastedlogindate;
    private Long bppuserid;
    private String bppusercode;
    private String bpploginname;
    private Long bppcompanyid;
    private String bppcompanycode;
    private String bppcompanyname;
    private String bppbindip;
    private Timestamp bppbinddate;
    private Timestamp registerdate;
    private String registerip;
    private Short defaultflag;
    private Short enableflag;
    private Short lockflag;
    private Timestamp createdon;
    private String createdname;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
    private Short bppagentcategory;
    private Integer isnew;
    private String bppcompanyfname;
    private Short bppaccounttype;
    private Long storeid;
    private String storescode;
    private String storesname;
    private Integer platactindex;
    private String platactname;
    private Short authstate;
    private Short authtype;
    private String authcompanyname;
    private short powerstate;

}
