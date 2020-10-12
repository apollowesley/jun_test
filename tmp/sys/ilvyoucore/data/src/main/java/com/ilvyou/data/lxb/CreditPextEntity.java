package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditPextEntity {
    private long actid;
    private String actname;
    private String realname;
    private long periodid;
    private int periodmonth;
    private String periodbegindate;
    private String periodenddate;
    private String repaymentdate;
    private BigDecimal beginusablelimit;
    private BigDecimal endusablelimit;
    private BigDecimal pastratio;
    private short pastflag;
    private short countflag;
    private short currentflag;
    private short periodflag;
    private short billflag;
    private short successflag;
    private short dueflag;
    private Timestamp duedata;
    private String bppusercode;
    private String bppcompanycode;
    private String bppcompanyfname;
    private Integer bppaccounttype;
    private String storesname;
    private String platactname;
    private String createdname;
    private Timestamp createdon;
    private long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private long lastedby;
    private String lastedip;

}
