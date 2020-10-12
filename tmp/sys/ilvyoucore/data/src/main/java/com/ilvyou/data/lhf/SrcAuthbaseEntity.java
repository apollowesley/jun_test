package com.ilvyou.data.lhf;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ilvyou.data.JsonLongSerializer;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 2016/9/19 0019.
 */
@Data
public class SrcAuthbaseEntity {
    private Long id;
    private Long actid;
    private String actname;
    private Short accounttype;
    private String companyname;
    private String registcode;
    private String address;
    private Integer provinceid;
    private String provincename;
    private Integer cityid;
    private String cityname;
    private Integer countryid;
    private String countryname;
    private String areacode;
    private String tel;
    private Short wordidcode;
    private String legalname;
    private String legalcertid;
    private String wordname;
    private String wordcertid;
    private String agentdepartment;
    private String agentposition;
    private Timestamp applydate;
    private Short approvestate;
    private Short authtype;
    private Timestamp approvedate;
    private String approvereason;
    private String approve;
    private String remark;
    private String otherreason;
    private String createdname;
    private Timestamp createdon;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;

    @JsonSerialize(using = JsonLongSerializer.class )
    public Long getActid(){
        return this.actid;
    }
    @JsonSerialize(using = JsonLongSerializer.class )
    public Long getId(){
        return this.id;
    }
}
