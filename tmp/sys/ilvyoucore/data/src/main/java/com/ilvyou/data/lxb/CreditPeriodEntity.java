package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditPeriodEntity {
    private long actid;
    private String actname;
    private String realname;
    private long preperiodid;
    private long periodid;
    private int periodmonth;
    private String periodbegindate;
    private String periodenddate;
    private String repaymentdate;
    private BigDecimal beginusablelimit;
    private BigDecimal endusablelimit;
    private int tpaycount;
    private BigDecimal tpayamount;
    private int trepaycount;
    private BigDecimal prepayamount;
    private BigDecimal pbillamount;
    private BigDecimal pintegererestamount;
    private int poverdueday;
    private BigDecimal freeamount;
    private BigDecimal arepayamount;
    private BigDecimal abillamount;
    private BigDecimal aintegererestamount;
    private Timestamp lastcalcdate;
    private Integer coverdueday;
    private BigDecimal cintegererestamount;
    private BigDecimal prestbillamount;
    private BigDecimal prestinsamount;
    private int arefundcount;
    private BigDecimal arefundamount;
    private int abackcount;
    private BigDecimal abackamount;
    private BigDecimal hbillamount;
    private BigDecimal hintegererestamount;
    private BigDecimal pastratio;
    private int pastflag;
    private String createdname;
    private Timestamp createdon;
    private long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;

}
