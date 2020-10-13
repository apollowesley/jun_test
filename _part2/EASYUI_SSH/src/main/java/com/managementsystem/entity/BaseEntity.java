package com.managementsystem.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 表字段ID
	 */

	private Long id;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 修改日期
	 */
	private Date modifyDate;
	
	/**
	 * 创建人ID
	 */
	private Long createid;
	
	/**
	 * 创建人
	 */
	private String createUser;//创建人
	
	/**
	 * 修改人ID
	 */
	private Long modifyid;
	/**
	 * 修改人
	 */
	private String modifyUser;//修改人
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Long getCreateid() {
		return createid;
	}
	public void setCreateid(Long createid) {
		this.createid = createid;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Long getModifyid() {
		return modifyid;
	}
	public void setModifyid(Long modifyid) {
		this.modifyid = modifyid;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	
	

}
