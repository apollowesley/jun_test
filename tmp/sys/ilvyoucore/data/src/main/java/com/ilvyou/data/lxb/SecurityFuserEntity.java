package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class SecurityFuserEntity {
    private long actid;
    private String actname;
    private long batchid;
    private String rulecode;
    private String rulename;
    private long userid;
    private String usecode;
    private String loginname;
    private String usename;
    private long id;
    private int category;
    private int agentcategory;
    private String lpname;
    private String lpemail;
    private String lpmobile;
    private String lpidcard;
    private String lpidurl;
    private Timestamp birthdaydate;
    private String licnumber;
    private String licurl;
    private String taxnumber;
    private String taxurl;
    private int provinceid;
    private String provincename;
    private int cityid;
    private String cityname;
    private Integer areaid;
    private String areaname;
    private String tel;
    private String address;
    private String createdname;
    private Timestamp createdon;
    private long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private long lastedby;
    private String lastedip;

}
