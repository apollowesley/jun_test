package com.ilvyou.data.ilvyou;

/**
 * Created by admin on 2016/10/28.
 */
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AccountEntity {
    private Long id;
    private String loginname;
    private String password;
    private String displayname;
    private Boolean isuperadmin;
    private Integer status;
    private String salt;
    private Integer accounttype;
    private String phone;
    private String tel;
    private String email;
    private String qq;
    private Integer gender;
    private String idcode;
    private String address;
    private String remarks;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
}