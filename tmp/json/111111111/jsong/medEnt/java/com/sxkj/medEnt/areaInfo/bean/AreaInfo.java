package com.sxkj.medEnt.areaInfo.bean;
import com.sxkj.frame.utils.BeanSupport;

import java.util.Date;
import java.lang.Integer;
public class AreaInfo extends BeanSupport {
private String id;
private String code;
private String name;
private String nickname;
private String parentId;
private String parentsId;
private String pointX;
private String pointY;
private String createId;
private Date createDate;
private String operId;
private Date operDate;
private String sortId;
private Integer status;
private Integer version;
public String getId(){ return id ; }
public void setId(String id){ this.id=id; }
public String getCode(){ return code ; }
public void setCode(String code){ this.code=code; }
public String getName(){ return name ; }
public void setName(String name){ this.name=name; }
public String getNickname(){ return nickname ; }
public void setNickname(String nickname){ this.nickname=nickname; }
public String getParentId(){ return parentId ; }
public void setParentId(String parentId){ this.parentId=parentId; }
public String getParentsId(){ return parentsId ; }
public void setParentsId(String parentsId){ this.parentsId=parentsId; }
public String getPointX(){ return pointX ; }
public void setPointX(String pointX){ this.pointX=pointX; }
public String getPointY(){ return pointY ; }
public void setPointY(String pointY){ this.pointY=pointY; }
public String getCreateId(){ return createId ; }
public void setCreateId(String createId){ this.createId=createId; }
public Date getCreateDate(){ return createDate ; }
public void setCreateDate(Date createDate){ this.createDate=createDate; }
public String getOperId(){ return operId ; }
public void setOperId(String operId){ this.operId=operId; }
public Date getOperDate(){ return operDate ; }
public void setOperDate(Date operDate){ this.operDate=operDate; }
public String getSortId(){ return sortId ; }
public void setSortId(String sortId){ this.sortId=sortId; }
public Integer getStatus(){ return status ; }
public void setStatus(Integer status){ this.status=status; }
public Integer getVersion(){ return version ; }
public void setVersion(Integer version){ this.version=version; }
}