package com.myapp.entity;

import java.math.BigDecimal;
import java.util.Date;

import net.oschina.durcframework.easymybatis.query.annotation.ValueField;
import net.oschina.durcframework.easymybatis.query.param.BaseParam;

public class TUserSch extends BaseParam {

    private Integer idSch;
    private String usernameSch;
    private Byte stateSch;
    private Boolean isdelSch;
    private String remarkSch;
    private Date addTimeSch;
    private BigDecimal moneySch;
    private Float leftMoneySch;

    public void setIdSch(Integer idSch){
        this.idSch = idSch;
    }
    
    @ValueField(column = "id")
    public Integer getIdSch(){
        return this.idSch;
    }

    public void setUsernameSch(String usernameSch){
        this.usernameSch = usernameSch;
    }
    
    @ValueField(column = "username")
    public String getUsernameSch(){
        return this.usernameSch;
    }

    public void setStateSch(Byte stateSch){
        this.stateSch = stateSch;
    }
    
    @ValueField(column = "state")
    public Byte getStateSch(){
        return this.stateSch;
    }

    public void setIsdelSch(Boolean isdelSch){
        this.isdelSch = isdelSch;
    }
    
    @ValueField(column = "isdel")
    public Boolean getIsdelSch(){
        return this.isdelSch;
    }

    public void setRemarkSch(String remarkSch){
        this.remarkSch = remarkSch;
    }
    
    @ValueField(column = "remark")
    public String getRemarkSch(){
        return this.remarkSch;
    }

    public void setAddTimeSch(Date addTimeSch){
        this.addTimeSch = addTimeSch;
    }
    
    @ValueField(column = "add_time")
    public Date getAddTimeSch(){
        return this.addTimeSch;
    }

    public void setMoneySch(BigDecimal moneySch){
        this.moneySch = moneySch;
    }
    
    @ValueField(column = "money")
    public BigDecimal getMoneySch(){
        return this.moneySch;
    }

    public void setLeftMoneySch(Float leftMoneySch){
        this.leftMoneySch = leftMoneySch;
    }
    
    @ValueField(column = "left_money")
    public Float getLeftMoneySch(){
        return this.leftMoneySch;
    }


}