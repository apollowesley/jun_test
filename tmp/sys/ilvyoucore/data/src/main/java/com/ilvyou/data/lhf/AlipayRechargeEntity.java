package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 2016/9/21 0021.
 */
@Data
public class AlipayRechargeEntity {
    private Long actid;
    private String actname;
    private String realname;
    private Long periodid;
    private Integer periodmonth;
    private String periodbegindate;
    private String periodenddate;
    private Double oleftamount;
    private Double nleftamount;
    private Long logid;
    private Double amount;
    private Double cashbackamount;
    private Short reverseflag;
    private String rechargebyname;
    private Timestamp rechargedate;
    private String remark;
    private Short tradestatus;
    private Timestamp successdate;
    private Integer bankid;
    private String bankcode;
    private String bankname;
    private String bankcard;
    private Short quickflag;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
    private Long reflogid;
    private Short deleteflag;
    private String channeltype;
    private Integer banktype;
    private Short isreconcilate;
    private Short rechargetype;
    private String bppusercode;
    private String bpploginname;
    private String bppcompanyname;
    private String bppcompanyfname;
    private String platactname;
    private String storesname;
    private Long bppuserid;
    private Long bppcompanyid;
    private String bppcompanycode;
    private Integer bppaccounttype;
    private Long storeid;
    private String storescode;
    private Integer platactindex;
    private Double orightamount;
    private Double nrightamount;
    private String sourcetype;
}
