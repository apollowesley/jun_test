package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditTrepay2Entity {
    private String periodbegindate;
    private String periodenddate;
    private String repaymentdate;
    private long djid;
    private String djno;
    private long refactid;
    private String refactname;
    private short refaccounttype;
    private long reflogid;
    private BigDecimal oleftamount;
    private BigDecimal nleftamount;
    private BigDecimal orightamount;
    private BigDecimal nrightamount;
    private long logid;
    private String lognum;
    private BigDecimal ousableamount;
    private BigDecimal nusableamount;
    private BigDecimal prepayamount;
    private BigDecimal pbillamount;
    private BigDecimal pinterestamount;
    private short poverdueday;
    private BigDecimal freeamount;
    private BigDecimal arepayamount;
    private BigDecimal abillamount;
    private BigDecimal ainterestamount;
    private Timestamp lastcalcdate;
    private Short coverdueday;
    private BigDecimal cinterestamount;
    private BigDecimal prestbillamount;
    private BigDecimal prestinsamount;
    private String createdname;
    private Timestamp createdon;
    private long createdby;
    private String createdip;

}
