package com.sxkj.medEnt.errorInfo.bean;
import com.sxkj.frame.utils.BeanSupport;

import java.util.Date;
import java.lang.Integer;
public class ErrorInfo extends BeanSupport {
private String id;
private String roleId;
private String roleName;
private String createId;
private Date createDate;
private String operId;
private Date operDate;
private Integer sortId;
private Integer status;
private Integer version;
private String sysName;
private String url;
private String exceptionInfo;
private String detailInfo;
private String exceptionAdd;
private Date occurTime;
public String getId(){ return id ; }
public void setId(String id){ this.id=id; }
public String getRoleId(){ return roleId ; }
public void setRoleId(String roleId){ this.roleId=roleId; }
public String getRoleName(){ return roleName ; }
public void setRoleName(String roleName){ this.roleName=roleName; }
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
public String getSysName(){ return sysName ; }
public void setSysName(String sysName){ this.sysName=sysName; }
public String getUrl(){ return url ; }
public void setUrl(String url){ this.url=url; }
public String getExceptionInfo(){ return exceptionInfo ; }
public void setExceptionInfo(String exceptionInfo){ this.exceptionInfo=exceptionInfo; }
public String getDetailInfo(){ return detailInfo ; }
public void setDetailInfo(String detailInfo){ this.detailInfo=detailInfo; }
public String getExceptionAdd(){ return exceptionAdd ; }
public void setExceptionAdd(String exceptionAdd){ this.exceptionAdd=exceptionAdd; }
public Date getOccurTime(){ return occurTime ; }
public void setOccurTime(Date occurTime){ this.occurTime=occurTime; }
}