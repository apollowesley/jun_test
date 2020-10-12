package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/17.
 */
@Data
public class AlipayTransferEntity {
    private Long factid;
    private String factname;
    private String frealname;
    private Long fperiodid;
    private Integer fperiodmonth;
    private String fperiodbegindate;
    private String fperiodenddate;
    private Double foleftamount;
    private Double fnleftamount;
    private Long fromcompanyid;
    private String fromcompanycode;
    private String fromcompanyshortname;
    private String fromcompanyfullname;
    private Long tocompanyid;
    private String tocompanycode;
    private String tocompanyshortname;
    private String tocompanyfullname;
    private Long tactid;
    private String tactname;
    private String trealname;
    private Long tperiodid;
    private Integer tperiodmonth;
    private String tperiodbegindate;
    private String tperiodenddate;
    private Double toleftamount;
    private Long outreflogid;
    private Long inreflogid;
    private Double tnleftamount;
    private Long logid;
    private String lordernum;
    private String payname;
    private Double payamount;
    private Double paidamount;
    private Double distamount;
    private Double expenseamount;
    private String paytype;
    private Timestamp paydate;
    private Timestamp transferdate;
    private String remark;
    private Short tradestatus;
    private Short deleteflag;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
    private Long frombppuserid;
    private String frombppusercode;
    private String frombpploginname;
    private Integer frombppaccounttype;
    private Integer fromplatactindex;
    private String fromplatactname;
    private Long fromstoreid;
    private String fromstorescode;
    private String fromstoresname;
    private Long tobppuserid;
    private String tobppusercode;
    private String tobpploginname;
    private Integer tobppaccounttype;
    private Integer toplatactindex;
    private String toplatactname;
    private Long tostoreid;
    private String tostorescode;
    private String tostoresname;
    private Short frombppagentcategory;
    private Short tobppagentcategory;
    private Double forightamount;
    private Double fnrightamount;
    private Double torightamount;
    private Double tnrightamount;
    private String sourcetype;
}
