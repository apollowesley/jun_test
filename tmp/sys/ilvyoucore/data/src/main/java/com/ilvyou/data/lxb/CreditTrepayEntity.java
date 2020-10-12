package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditTrepayEntity {
    private long actid;
    private String actname;
    private long refactid;
    private String refactname;
    private short refaccounttype;
    private String periodids;
    private long djid;
    private String djno;
    private String sourcetype;
    private BigDecimal repayamount;
    private BigDecimal capitalamount;
    private BigDecimal interestamount;
    private BigDecimal leftamount;
    private Timestamp repaydate;
    private short repaytype;
    private String createdname;
    private Timestamp createdon;
    private long createdby;
    private String createdip;
}
