package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class WatchDamountEntity {
    private long actid;
    private String actname;
    private String realname;
    private long pkid;
    private int periodmonth;
    private int creditlimit;
    private BigDecimal usablelimit;
    private String sourcetype;
    private BigDecimal tradeamount;
    private int limitamoint;
    private String merchantcode;
    private int ordertype;
    private long orderid;
    private String ordernum;
    private String bppusercode;
    private String bppcompanycode;
    private String bppcompanyfname;
    private Integer bppaccounttype;
    private String storesname;
    private String platactname;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
}
