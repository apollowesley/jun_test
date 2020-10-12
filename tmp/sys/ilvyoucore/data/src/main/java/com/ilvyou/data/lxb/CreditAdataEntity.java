package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditAdataEntity {
    private long actid;
    private String actname;
    private String realname;
    private BigDecimal creditlimit;
    private BigDecimal usablelimit;
    private BigDecimal templimit;
    private int totalperiods;
    private int closeperiods;
    private int curlimitflag;
    private int tpaycount;
    private BigDecimal tpayamount;
    private Integer tpenaltycount;
    private BigDecimal closeinsamout;
    private int trepaycount;
    private BigDecimal trepayamount;
    private int trefundcount;
    private BigDecimal trefundamount;
    private Integer tbackcount;
    private BigDecimal tbackamount;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedname;
    private String lastedip;
}
