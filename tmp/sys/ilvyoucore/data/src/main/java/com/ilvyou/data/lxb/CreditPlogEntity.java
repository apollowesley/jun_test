package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditPlogEntity {
    private long actid;
    private String actname;
    private String realname;
    private long periodid;
    private int periodmonth;
    private String periodbegindate;
    private String periodenddate;
    private String repaymentdate;
    private long logid;
    private BigDecimal ocreditamount;
    private BigDecimal ncreditamount;
    private String sourcetype;
    private int djtype;
    private long djid;
    private String djnum;
    private Timestamp tradedate;
    private BigDecimal amount;
    private int ordertype;
    private long orderid;
    private String ordernum;
    private long cravid;
    private String cravnum;
    private String cravtitle;
    private String tradeip;
    private String digest1;
    private String direction;
    private String tradetype;
    private String status;
    private String createdname;
    private Timestamp createdon;
    private long createdby;
    private String createdip;

}
