package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 2016/9/19 0019.
 */
@Data
public class AlipayLogEntity {
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
    private Timestamp tradedate;
    private Double tradeamount;
    private Double inamount;
    private String tradeflow;
    private String tradetype;
    private Short ordertype;
    private Long orderid;
    private String ordernum;
    private String tradedigest;
    private String tradestatus;
    private Short deleteflag;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
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
