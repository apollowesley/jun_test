package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class BaseCategoryEntity {
    private int id;
    private String code;
    private String name;
    private Integer showorder;
    private String remark;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
}
