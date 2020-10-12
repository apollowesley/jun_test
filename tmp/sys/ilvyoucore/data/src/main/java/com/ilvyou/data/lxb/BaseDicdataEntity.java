package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class BaseDicdataEntity {
    private long categoryid;
    private String categorycode;
    private String categoryname;
    private Integer showorder;
    private String diccode;
    private String dicname;
    private String extension;
    private String remark;
    private int status;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
}
