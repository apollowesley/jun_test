package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 2016/9/19 0019.
 */
@Data
public class AlipayIncreaseEntity {
    private Long factid;
    private String factname;
    private String frealname;
    private Long tactid;
    private String tactname;
    private String trealname;
    private Integer periodmonth;
    private String periodbegindate;
    private String periodenddate;
    private Long logid;
    private Double amount;
    private Timestamp increasedate;
    private Double surplusamount;
    private Long reflogid;
    private Long orderid;
    private String ordernum;
    private String sourcetype;
    private String remark;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
}
