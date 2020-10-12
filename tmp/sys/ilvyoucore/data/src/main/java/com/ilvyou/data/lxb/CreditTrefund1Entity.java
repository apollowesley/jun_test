package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditTrefund1Entity {
    private long actid;
    private String actname;
    private String realname;
    private long periodid;
    private int periodmonth;
    private String periodbegindate;
    private String periodenddate;
    private String repaymentdate;
    private long djid;
    private String djno;
    private long periodlogid;
    private long logid;
    private String lognum;
    private String sourcetype;
    private Timestamp tradedate;
    private BigDecimal tradeamount;
    private BigDecimal repayamount;
    private BigDecimal backinstereamount;
    private BigDecimal backamount;
    private short ordertype;
    private long orderid;
    private String ordernum;
    private String tradeip;
    private String digest1;
    private short tradetype;
    private String status;
    private long cravid;
    private String cravnum;
    private String cravtitle;
    private String bppusercode;
    private String bppcompanycode;
    private String bppcompanyfname;
    private int bppaccounttype;
    private String storesname;
    private String platactname;
    private String createdname;
    private Timestamp createdon;
    private long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
}
