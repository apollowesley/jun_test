package com.ilvyou.data.lhf;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/4.
 */
@Data
public class AlipayCpayEntity {
    private long actid;
    private String actname;
    private String realname;
    private long periodid;
    private int periodmonth;
    private String periodbegindate;
    private String periodenddate;
    private BigDecimal oleftamount;
    private BigDecimal nleftamount;
    private BigDecimal orightamount;
    private BigDecimal nrightamount;
    private long cactid;
    private String cactname;
    private String crealname;
    private short treftype;
    private long treflogid;
    private String sourcetype;
    private long reflogid;
    private long logid;
    private Timestamp tradedate;
    private BigDecimal tradeamount;
    private String tradeflow;
    private short ordertype;
    private long orderid;
    private String ordernum;
    private String tradedigest;
    private String tradetype;
    private short status;
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

