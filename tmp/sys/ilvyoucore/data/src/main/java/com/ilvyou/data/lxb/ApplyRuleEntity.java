package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class ApplyRuleEntity {
    private long taskid;
    private String taskcode;
    private long actid;
    private String actname;
    private long pkid;
    private BigDecimal orderratio;
    private BigDecimal idcardratio;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedname;
    private String lastedip;
}
