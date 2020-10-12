package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class PlatVrlogEntity {
    private long platid;
    private String platcode;
    private String platnname;
    private long actid;
    private String actname;
    private int periodmonth;
    private String sourcetype;
    private long logid;
    private Timestamp tradedate;
    private BigDecimal tradeamount;
    private BigDecimal inamount;
    private String tradeflow;
    private int tradetype;
    private long tradeid;
    private String tradenum;
    private int ordertype;
    private long orderid;
    private String ordernum;
    private Integer extendtype;
    private Long extendid;
    private String extendnum;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;

}
