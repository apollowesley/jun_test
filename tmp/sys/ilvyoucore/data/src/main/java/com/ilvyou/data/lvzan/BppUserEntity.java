package com.ilvyou.data.lvzan;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/17.
 */
@Data
public class BppUserEntity {
    private Long id;
    private String code;
    private String shortname;
    private Long userid;
    private String usernum;
    private String realname;
    private Short sex;
    private String cardcode;
    private String phonenum;
    private String tel;
    private String fox;
    private String email;
    private String qq;
    private String departmentname;
    private String positionname;
    private String address;
    private String remarks;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedname;
    private String lastedip;
    private Integer isbind;
    private Integer isfrozen;
    private Long storeid;
    private String storescode;
    private String storesname;
}
