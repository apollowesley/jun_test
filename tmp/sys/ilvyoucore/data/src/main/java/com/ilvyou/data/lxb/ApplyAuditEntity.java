package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class ApplyAuditEntity {
    private long taskid;
    private String taskcode;
    private long actid;
    private String actname;
    private long pkid;
    private String resultcode;
    private String resultname;
    private int audittype;
    private String remark;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedname;
    private String lastedip;
}
