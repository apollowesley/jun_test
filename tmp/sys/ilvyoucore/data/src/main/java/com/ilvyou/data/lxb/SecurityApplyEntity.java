package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class SecurityApplyEntity {
    private long actid;
    private String actname;
    private String realname;
    private Long batchid;
    private String rulecode;
    private String rulename;
    private String idcard;
    private int idcardflag;
    private String mobile;
    private int mobileflag;
    private String email;
    private int accounttype;
    private int authstate;
    private int authtype;
    private long bppuserid;
    private String bppusercode;
    private String bpploginname;
    private long bppcompanyid;
    private String bppcompanycode;
    private String bppcompanyname;
    private String bppcompanyfname;
    private Integer bppaccounttype;
    private Long storeid;
    private String storescode;
    private String storesname;
    private String platactname;
    private Integer score;
    private String status;
    private String operatname;
    private Timestamp operatdate;
    private String operatremark;
    private Timestamp createdon;
    private Long createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedname;
    private String lastedip;
}
