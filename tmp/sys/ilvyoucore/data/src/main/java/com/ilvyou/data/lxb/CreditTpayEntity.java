package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditTpayEntity {
    private long actid;
    private String actname;
    private String realname;
    private long periodid;
    private int periodmonth;
    private String periodbegindate;
    private String periodenddate;
    private String repaymentdate;
    private BigDecimal ocreditamount;
    private BigDecimal ncreditamount;
    private long refactid;
    private String refactname;
    private String refrealname;
    private int refaccounttype;
    private long reflogid;
    private BigDecimal oleftamount;
    private BigDecimal nleftamount;
    private BigDecimal orightamount;
    private BigDecimal nrightamount;
    private long periodlogid;
    private long logid;
    private String lognum;
    private String sourcetype;
    private Timestamp tradedate;
    private BigDecimal payamount;
    private BigDecimal amount1;
    private BigDecimal amount2;
    private Integer ordertype;
    private Long orderid;
    private String ordernum;
    private String tradeip;
    private String digest1;
    private String tradetype;
    private String status;
    private long cravid;
    private String cravnum;
    private String cravtitle;
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
