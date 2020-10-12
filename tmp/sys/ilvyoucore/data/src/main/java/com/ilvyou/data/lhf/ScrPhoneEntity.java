package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/16.
 */
@Data
public class ScrPhoneEntity {
    private Long sendid;
    private Timestamp senddate;
    private String mobile;
    private Short sendtype;
    private String sendcode;
    private Integer effecminutes;
    private Timestamp enddate;
    private String senddetails;
    private String content;
    private Timestamp createdon;
    private String createdname;
    private String createdip;
    private Long createdby;
}
