package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditTrefund2Entity {
    private String periodbegindate;
    private String periodenddate;
    private String repaymentdate;
    private BigDecimal ousableamount;
    private BigDecimal nusableamount;
    private long djid;
    private String djno;
    private long refactid;
    private String refactname;
    private String refrealname;
    private short refaccounttype;
    private long reflogid;
    private BigDecimal oleftamount;
    private BigDecimal nleftamount;
    private BigDecimal orightamount;
    private BigDecimal nrightamount;
    private long periodlogid;
    private long logid;
    private String lognum;
    private String createdname;
    private Timestamp createdon;
    private long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
}
