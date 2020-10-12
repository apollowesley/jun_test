package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditTrefundEntity {
    private long actid;
    private String actname;
    private String realname;
    private long refactid;
    private String refactname;
    private String refrealname;
    private short refaccounttype;
    private long djid;
    private String djno;
    private String sourcetype;
    private Timestamp tradedate;
    private BigDecimal tradeamount;
    private BigDecimal refundamount;
    private BigDecimal repayamount;
    private BigDecimal backinstereamount;
    private BigDecimal backamount;
    private BigDecimal leftamount;
    private short ordertype;
    private long orderid;
    private String ordernum;
    private BigDecimal orderamount;
    private BigDecimal sendmonamount;
    private Short logictype;
    private String tradeip;
    private String digest1;
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
    private long lastedby;
    private String lastedip;


}
