package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 2016/9/19 0019.
 */
@Data
public class AlipayWithdrawEntity {
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
    private String applicantname;
    private String applicantmobile;
    private Timestamp applicantdate;
    private String remark;
    private String accreditnum;
    private Integer bankid;
    private String bankname;
    private String bankcode;
    private String banknum;
    private String parsename;
    private Timestamp parsedate;
    private Short tradestatus;
    private String digest;
    private Short notifyflag;
    private Timestamp bankstartdate;
    private Timestamp toaccountdate;
    private Integer ticketdays;
    private Double electronicticket;
    private Timestamp ticketoutdate;
    private Double ticketoutcost;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
    private Long reflogid;
    private Short submitflag;
    private Double intamount;
    private Short deleteflag;
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
