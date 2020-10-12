package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class ApplyPersonEntity {
    private long taskid;
    private String taskcode;
    private long actid;
    private String actname;
    private String applyname;
    private String applyjob;
    private String applymobile;
    private String telecode;
    private String telephone;
    private String createdname;
    private Timestamp createdon;
    private long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private long lastedby;
    private String lastedip;
}
