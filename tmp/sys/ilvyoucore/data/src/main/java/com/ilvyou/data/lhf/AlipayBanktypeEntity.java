package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/17.
 */
@Data
public class AlipayBanktypeEntity {
    private Integer bankid;
    private String bankcode;
    private String bankname;
    private String paycode;
    private String remark;
    private String imgname1;
    private String imgpath1;
    private String imgname2;
    private String imgpath2;
    private Short enableflag;
    private Short lockflag;
    private Short accreditflag;
    private String aprovincename;
    private String acityname;
    private Double accreditlimit;
    private Double auselimit;
    private Double aleftlimit;
    private Double awarnlimit;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
    private Short thirdflag;
    private Short rechargeflag;
    private Short privateflag;
    private Integer b2Cshortindex;
    private Integer b2Bshortindex;
}
