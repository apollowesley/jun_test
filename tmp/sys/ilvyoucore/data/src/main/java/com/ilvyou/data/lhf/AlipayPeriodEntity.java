package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/16.
 */
@Data
public class AlipayPeriodEntity {
    private Long actid;
    private String actname;
    private String realname;
    private Long periodid;
    private Integer periodmonth;
    private String periodbegindate;
    private String periodenddate;
    private Double bleftamount;
    private Double eleftamount;
    private Double trevenue;
    private Double tspending;
    private Double trecharge;
    private Double texchange;
    private Double tdrawal;
    private Double trefund;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
    private Short currentflag;
    private Short countflag;
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
    private Double brightamount;
    private Double erightamount;
    private Double ttranferin;
    private Double ttranferout;
    private Double treceive;
    private Double tpay;
    private Double tconsume;
    private Double ttranfer;
    private Double tadjustin;
    private Double tadjustout;
    private Double terror;
    private Double tdrawalin;
    private Double ttranferinterest;
    private Double nobusinessamount;
}
