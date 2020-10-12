package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class ApplyAccountEntity {
    private Long taskid;
    private String taskcode;
    private Long actid;
    private String actname;
    private String realname;
    private Long batchid;
    private String idcard;
    private Integer idcardflag;
    private String mobile;
    private Integer mobileflag;
    private String email;
    private Integer accounttype;
    private Integer authstate;
    private Integer authtype;
    private String pverificode;
    private Long bppuserid;
    private String bppusercode;
    private String bpploginname;
    private Long bppcompanyid;
    private String bppcompanycode;
    private String bppcompanyname;
    private String bppcompanyfname;
    private Integer bppaccounttype;
    private Long storeid;
    private String storescode;
    private String storesname;
    private String platactname;
    private String creditname;
    private String creditremark;
    private Integer creditstatus;
    private Timestamp creditdate;
    private int creditamount;
    private int actualamount;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedname;
    private String lastedip;
}
