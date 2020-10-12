package com.ilvyou.data.lxb;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class PlatVirtualEntity {
    private long platid;
    private String platcode;
    private String platname;
    private Double tcreditamount;
    private Double tamount;
    private Double creditamount;
    private Double cancreditamount;
    private Double interestamount;
    private Double payamount;
    private Double refundamount;
    private Double repayamount;
    private Double noreturnamount;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedname;
    private String lastedip;

}
