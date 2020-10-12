package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/16.
 */
@Data
public class AlipayAccountEntity {
    private Long actid;
    private String actname;
    private String realname;
    private String nickname;
    private Short accounttype;
    private Short periodendmonth;
    private Integer periodendday;
    private Short periodbeginmonth;
    private Integer periodbeginday;
    private String paypassword;
    private String drawalpassword;
    private Double leftamount;
    private Integer drawaldays;
    private Double drawallimit;
    private Integer cardqty;
    private Integer pcardqty;
    private Integer ccardqty;
    private Short enableflag;
    private Short lockflag;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
    private Double nobusinessamount;
    private Double rightamount;
    private Double unsettleamount;
    private Double unpayamount;
    private Double freezeamount;
}
