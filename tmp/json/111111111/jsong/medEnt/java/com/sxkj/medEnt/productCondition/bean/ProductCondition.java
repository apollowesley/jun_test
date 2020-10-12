package com.sxkj.medEnt.productCondition.bean;
import com.sxkj.frame.utils.BeanSupport;

import java.lang.Integer;
import java.util.Date;
import java.math.BigDecimal;
public class ProductCondition extends BeanSupport {
private String id;
private Integer reportState;
private Date reportDate;
private BigDecimal conditionArea;
private BigDecimal conditionPower;
private BigDecimal refrigePower;
private BigDecimal heatPower;
private BigDecimal humidityPower;
private BigDecimal supplyMax;
private String createId;
private Date createDate;
private String operId;
private Date operDate;
private Integer sortId;
private Integer status;
private Integer version;
public String getId(){ return id ; }
public void setId(String id){ this.id=id; }
public Integer getReportState(){ return reportState ; }
public void setReportState(Integer reportState){ this.reportState=reportState; }
public Date getReportDate(){ return reportDate ; }
public void setReportDate(Date reportDate){ this.reportDate=reportDate; }
public BigDecimal getConditionArea(){ return conditionArea ; }
public void setConditionArea(BigDecimal conditionArea){ this.conditionArea=conditionArea; }
public BigDecimal getConditionPower(){ return conditionPower ; }
public void setConditionPower(BigDecimal conditionPower){ this.conditionPower=conditionPower; }
public BigDecimal getRefrigePower(){ return refrigePower ; }
public void setRefrigePower(BigDecimal refrigePower){ this.refrigePower=refrigePower; }
public BigDecimal getHeatPower(){ return heatPower ; }
public void setHeatPower(BigDecimal heatPower){ this.heatPower=heatPower; }
public BigDecimal getHumidityPower(){ return humidityPower ; }
public void setHumidityPower(BigDecimal humidityPower){ this.humidityPower=humidityPower; }
public BigDecimal getSupplyMax(){ return supplyMax ; }
public void setSupplyMax(BigDecimal supplyMax){ this.supplyMax=supplyMax; }
public String getCreateId(){ return createId ; }
public void setCreateId(String createId){ this.createId=createId; }
public Date getCreateDate(){ return createDate ; }
public void setCreateDate(Date createDate){ this.createDate=createDate; }
public String getOperId(){ return operId ; }
public void setOperId(String operId){ this.operId=operId; }
public Date getOperDate(){ return operDate ; }
public void setOperDate(Date operDate){ this.operDate=operDate; }
public Integer getSortId(){ return sortId ; }
public void setSortId(Integer sortId){ this.sortId=sortId; }
public Integer getStatus(){ return status ; }
public void setStatus(Integer status){ this.status=status; }
public Integer getVersion(){ return version ; }
public void setVersion(Integer version){ this.version=version; }
}