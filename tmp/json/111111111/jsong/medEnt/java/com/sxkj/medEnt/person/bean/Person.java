package com.sxkj.medEnt.person.bean;
import com.sxkj.frame.utils.BeanSupport;

import java.lang.Integer;
import java.util.Date;
public class Person extends BeanSupport {
private String id;
private String entId;
private String personType;
private String personSort;
private String personName;
private String eduBack;
private String jobTitle;
private String position;
private String idCard;
private String phoneNo;
private String major;
private Integer duration;
private Integer reportState;
private Date reportDate;
private String createId;
private Date createDate;
private String operId;
private Date operDate;
private Integer sortId;
private Integer status;
private Integer version;
public String getId(){ return id ; }
public void setId(String id){ this.id=id; }
public String getEntId(){ return entId ; }
public void setEntId(String entId){ this.entId=entId; }
public String getPersonType(){ return personType ; }
public void setPersonType(String personType){ this.personType=personType; }
public String getPersonSort(){ return personSort ; }
public void setPersonSort(String personSort){ this.personSort=personSort; }
public String getPersonName(){ return personName ; }
public void setPersonName(String personName){ this.personName=personName; }
public String getEduBack(){ return eduBack ; }
public void setEduBack(String eduBack){ this.eduBack=eduBack; }
public String getJobTitle(){ return jobTitle ; }
public void setJobTitle(String jobTitle){ this.jobTitle=jobTitle; }
public String getPosition(){ return position ; }
public void setPosition(String position){ this.position=position; }
public String getIdCard(){ return idCard ; }
public void setIdCard(String idCard){ this.idCard=idCard; }
public String getPhoneNo(){ return phoneNo ; }
public void setPhoneNo(String phoneNo){ this.phoneNo=phoneNo; }
public String getMajor(){ return major ; }
public void setMajor(String major){ this.major=major; }
public Integer getDuration(){ return duration ; }
public void setDuration(Integer duration){ this.duration=duration; }
public Integer getReportState(){ return reportState ; }
public void setReportState(Integer reportState){ this.reportState=reportState; }
public Date getReportDate(){ return reportDate ; }
public void setReportDate(Date reportDate){ this.reportDate=reportDate; }
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