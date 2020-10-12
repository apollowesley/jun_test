package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditTrepay1Entity {
    private long actid;
    private String actname;
    private String realname;
    private long periodid;
    private int periodmonth;
    private long djid;
    private String djno;
    private long periodlogid;
    private long logid;
    private String lognum;
    private String sourcetype;
    private BigDecimal repayamount;
    private BigDecimal capitalamount;
    private BigDecimal interestamount;
    private Timestamp repaydate;
    private short pastflag;
    private String tradeip;
    private String digest1;
    private String repaytype;
    private String status;
    private Timestamp passdate;
    private Short passstatus;
    private String passname;
    private String bppusercode;
    private String bppcompanycode;
    private String bppcompanyfname;
    private Short bppaccounttype;
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
