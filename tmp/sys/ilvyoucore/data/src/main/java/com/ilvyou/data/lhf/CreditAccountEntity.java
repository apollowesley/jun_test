package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/16.
 */
@Data
public class CreditAccountEntity {
    private Long actid;
    private String actname;
    private String realname;
    private Double expectlimit;
    private Double creditlimit;
    private Double usablelimit;
    private Double templimit;
    private Short repaymentday;
    private Short periodbeginmonth;
    private Integer periodbeginday;
    private Short periodendmonth;
    private Integer periodendday;
    private Short autoreturn;
    private Short active;
    private Short level;
    private Integer point;
    private Short lockflag;
    private Short openflag;
    private String openname;
    private Timestamp opendate;
    private String openremark;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
}
