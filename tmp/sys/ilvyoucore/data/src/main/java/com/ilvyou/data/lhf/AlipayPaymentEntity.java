package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 2016/9/21 0021.
 */
@Data
public class AlipayPaymentEntity {
    private Long factid;
    private String factname;
    private String frealname;
    private Long fperiodid;
    private Integer fperiodmonth;
    private String fperiodbegindate;
    private String fperiodenddate;
    private Double foleftamount;
    private Double fnleftamount;
    private Long tactid;
    private String tactname;
    private String trealname;
    private Long logid;
    private String payname;
    private Double payamount;
    private String paytype;
    private Timestamp paydate;
    private String remark;
    private Timestamp bsuccessdate;
    private Short tradestatus;
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
    private Short deleteflag;
    private String fbppusercode;
    private String fbpploginname;
    private String fbppcompanyname;
    private String fbppcompanyfname;
    private String fplatactname;
    private String fstoresname;
    private Long fbppuserid;
    private Long fbppcompanyid;
    private String fbppcompanycode;
    private Integer fbppaccounttype;
    private Long fstoreid;
    private String fstorescode;
    private Integer fplatactindex;
    private Double forightamount;
    private Double fnrightamount;
    private String sourcetype;
}
