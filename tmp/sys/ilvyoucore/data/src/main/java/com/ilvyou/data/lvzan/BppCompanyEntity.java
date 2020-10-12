package com.ilvyou.data.lvzan;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/17.
 */
@Data
public class BppCompanyEntity {
    private Long id;
    private String code;
    private String shortname;
    private String preaccount;
    private String name;
    private String brandnme;
    private Integer importid;
    private Integer importtype;
    private Integer partnerapplyid;
    private Integer provinceid;
    private String provincename;
    private Integer cityid;
    private String cityname;
    private Integer countyid;
    private String countyname;
    private String address;
    private String tel;
    private String zip;
    private String fax;
    private String intro;
    private String salesname;
    private String kindcode;
    private String kindname;
    private Short iscalc;
    private Integer agentcategory;
    private String linettypes;
    private String salescitys;
    private String largerimg;
    private String smallimg;
    private String clubcode;
    private String clubname;
    private Short status;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedname;
    private String lastedip;
    private String phonenum;
    private String middleimg;
    private String minimg;
    private Double minusproportion;
    private String coredestination;
    private Double coreminusproportion;
    private String departmentname;
}
