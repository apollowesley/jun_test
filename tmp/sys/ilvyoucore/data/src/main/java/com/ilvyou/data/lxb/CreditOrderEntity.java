package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditOrderEntity {
    private long actid;
    private String actname;
    private String realname;
    private String bppcompanyfname;
    private String platactname;
    private long ractid;
    private String ractname;
    private String rrealname;
    private long logid;
    private String payname;
    private BigDecimal payamount;
    private String paytype;
    private Timestamp paydate;
    private String remark;
    private int logictype;
    private int paytype2;
    private int ordertype;
    private Timestamp startdate;
    private Timestamp enddate;
    private Timestamp closedate;
    private int closeflag;
    private String sourcetype;
    private BigDecimal sendamount;
    private BigDecimal orderamount;
    private long orderid;
    private String ordernum;
    private String orderdigest;
    private String tradeip;
    private Integer tradestatus;
    private Timestamp successdate;
    private long cravid;
    private String cravnum;
    private String cravtitle;
    private BigDecimal vouchamount;
    private BigDecimal monamount;
    private BigDecimal alipayamount;
    private BigDecimal creditamount;
    private BigDecimal minusamount;
    private BigDecimal bankamount;
    private Timestamp bsuccessdate;
    private BigDecimal penaltyamount;
    private BigDecimal exchangeamount;
    private String exchangeids;
    private Timestamp passdate;
    private Integer passflag;
    private String passname;
    private String createdname;
    private Timestamp createdon;
    private long createdby;
    private String createdip;
}
