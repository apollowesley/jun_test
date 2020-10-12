package com.ilvyou.data.lhf;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/4.
 */
@Data
public class AlipayCreceiveEntity {
    private long actid;
    private String actname;
    private String realname;
    private long periodid;
    private int periodmonth;
    private String periodbegindate;
    private String periodenddate;
    private double oleftamount;
    private double nleftamount;
    private Double orightamount;
    private Double nrightamount;
    private long cactid;
    private String cactname;
    private String crealname;
    private short treftype;
    private long treflogid;
    private String sourcetype;
    private long reflogid;
    private long logid;
    private Timestamp tradedate;
    private Timestamp tradeamount;
    private double amount1;
    private double amount2;
    private String merchantcode;
    private Short ordertype;
    private Long orderid;
    private String ordernum;
    private String tradeip;
    private String digest1;
    private String tradetype;
    private String status;
    private long cravid;
    private String cravnum;
    private String cravtitle;
    private String bppusercode;
    private String bppcompanycode;
    private String bppcompanyfname;
    private Integer bppaccounttype;
    private String storesname;
    private String platactname;
    private String createdname;
    private Timestamp createdon;
    private long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private long lastedby;
    private String lastedip;

}
