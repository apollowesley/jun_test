package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditAdjustEntity {
    private long adjustid;
    private String adjustnum;
    private long actid;
    private String actname;
    private String realname;
    private int preamount;
    private int afteramount;
    private String remark;
    private int tradetype;
    private long tradeid;
    private String tradenum;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
}
