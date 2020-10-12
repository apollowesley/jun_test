package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class PlatValogEntity {
    private long platid;
    private String platcode;
    private String platnname;
    private int periodmonth;
    private String sourcetype;
    private int tradetype;
    private long tradeid;
    private String tradenum;
    private int ordertype;
    private long orderid;
    private String ordernum;
    private String fieldname;
    private BigDecimal prevalue;
    private BigDecimal tradevalue;
    private BigDecimal aftervalue;
    private String describe;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;

}
