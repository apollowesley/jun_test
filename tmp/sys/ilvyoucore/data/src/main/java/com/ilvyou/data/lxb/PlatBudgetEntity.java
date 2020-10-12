package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class PlatBudgetEntity {
    private long budid;
    private String budcode;
    private int periodmonth;
    private int budtype;
    private int budamount;
    private int status;
    private String remark;
    private String uauditname;
    private String uauditnum;
    private Timestamp auditdate;
    private String auditby;
    private String auditname;
    private String auditip;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedname;
    private String lastedip;
}
