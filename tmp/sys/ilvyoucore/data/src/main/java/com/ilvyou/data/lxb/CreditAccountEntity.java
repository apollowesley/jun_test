package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class CreditAccountEntity {
    private long actid;
    private String actname;
    private String realname;
    private String idcard;
    private short idcardflag;
    private String mobile;
    private short mobileflag;
    private String paypassword;
    private String email;
    private short repaymentday;
    private Short periodbeginmonth;
    private short periodbeginday;
    private Short periodendmonth;
    private short periodendday;
    private String contractnum;
    private Timestamp applydate;
    private String bppusercode;
    private String bppcompanycode;
    private String bppcompanyfname;
    private Integer bppaccounttype;
    private String storesname;
    private String platactname;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedname;
    private String lastedip;
}
