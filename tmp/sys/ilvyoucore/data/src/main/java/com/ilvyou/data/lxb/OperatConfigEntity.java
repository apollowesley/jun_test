package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class OperatConfigEntity {
    private String snum;
    private String scode;
    private Timestamp startdate;
    private Timestamp enddate;
    private int curflag;
    private String remark;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
}
