package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/16.
 */
@Data
public class CreditPeriodHeadEntity {
    private Long actid;
    private String actname;
    private String realname;
    private Long periodid;
    private Integer periodmonth;
    private String periodbegindate;
    private String periodenddate;
    private String repaymentdate;
    private Double beginusablelimit;
    private Double endusablelimit;
    private Double pbillamount;
    private Double currepayamount;
    private Double hrepayamount;
    private Double pinterestamount;
    private Double poverdueday;
    private Double prepayamount;
    private Double nrepayamount;
    private Short periodflag;
    private Short billflag;
    private Short successfag;
    private Short notifyflag1;
    private Short notifyflag2;
    private Short notifyflag3;
    private Short notifyflag4;
    private Short notifyflag5;
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
}
