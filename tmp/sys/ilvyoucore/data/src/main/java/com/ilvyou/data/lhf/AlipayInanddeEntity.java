package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/20.
 */
@Data
public class AlipayInanddeEntity {
    private Long pkid;
    private Long inlogid;
    private String delogid;
    private Long infactid;
    private String infactname;
    private String infrealname;
    private Long intactid;
    private String intactname;
    private String intrealname;
    private Long defactid;
    private String defactname;
    private String defrealname;
    private Long detactid;
    private String detactname;
    private String detrealname;
    private Integer periodmonth;
    private String periodbegindate;
    private String periodenddate;
    private Double inamount;
    private Timestamp increasedate;
    private Double surplusamount;
    private Long inreflogid;
    private Long inorderid;
    private String inordernum;
    private String insourcetype;
    private Double deamount;
    private Timestamp decreasedate;
    private Long dereflogid;
    private Long deorderid;
    private String deordernum;
    private String desourcetype;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
}
