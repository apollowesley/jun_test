package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 2016/9/21 0021.
 */
@Data
public class AlipayReceiveEntity {
    private Long tactid;
    private String tactname;
    private String trealname;
    private Long tperiodid;
    private Integer tperiodmonth;
    private String tperiodbegindate;
    private String tperiodenddate;
    private Double toleftamount;
    private Double tnleftamount;
    private Long factid;
    private String factname;
    private String frealname;
    private String fremark;
    private Long paylogid;
    private Long logid;
    private String drawalname;
    private Double drawalamount;
    private String drawaltype;
    private Timestamp drawaldate;
    private Short ordertype;
    private Long orderid;
    private String ordernum;
    private Timestamp successdate;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
    private Long reflogid;
    private String orderdigest;
    private Short tradestatus;
    private Short deleteflag;
    private String tbppusercode;
    private String tbpploginname;
    private String tbppcompanyname;
    private String tbppcompanyfname;
    private String tplatactname;
    private String tstoresname;
    private Long tbppuserid;
    private Long tbppcompanyid;
    private String tbppcompanycode;
    private Integer tbppaccounttype;
    private Long tstoreid;
    private String tstorescode;
    private Integer tplatactindex;
    private Double torightamount;
    private Double tnrightamount;
    private String sourcetype;
}
