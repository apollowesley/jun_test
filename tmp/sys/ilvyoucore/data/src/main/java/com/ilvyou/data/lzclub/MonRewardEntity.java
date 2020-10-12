package com.ilvyou.data.lzclub;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/17.
 */
@Data
public class MonRewardEntity {
    private Long monid;
    private String moncode;
    private String monname;
    private Short montype;
    private Integer periodmonth;
    private Long storeid;
    private String storescode;
    private String storesname;
    private Long companyid;
    private String companycode;
    private String companyname;
    private Long userid;
    private String usercode;
    private Double tamount;
    private Double usableamount;
    private Double usedamount;
    private Double undoamount;
    private Double fzamount;
    private Short status;
    private Timestamp createdon;
    private String createdby;
    private String createdname;
    private String createdip;
    private Timestamp lastedon;
    private String lastedby;
    private String lastedname;
    private String lastedip;
}
