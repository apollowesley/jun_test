package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditAlogEntity {
    private long actid;
    private String actname;
    private String realname;
    private long pkid;
    private String sourcetype;
    private int djtype;
    private long djid;
    private String djnum;
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
