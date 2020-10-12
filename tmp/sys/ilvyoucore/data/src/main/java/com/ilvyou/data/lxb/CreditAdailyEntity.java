package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditAdailyEntity {
    private long actid;
    private String actname;
    private String realname;
    private long periodid;
    private int periodmonth;
    private short totalperiods;
    private short closeperiods;
    private Long speriodid;
    private Integer speriodmonth;
    private short curlimitflag;
    private short dueflag;
    private String duedata;
    private short autoreturnflag;
    private short canpayflag;
    private short canrepayflag;
    private short canrefundflag;
    private short lockflag;
    private short openflag;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedname;
    private String lastedip;
}
