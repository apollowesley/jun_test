package org.apache.center.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 *
 * 用户表
 *
 */
@TableName(value="user",resultMap="BaseResultMap")
public class User implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主健 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 用户名 */
	@TableField(value = "user_name")
	private String userName;

	/** 密码 */
	private String password;

	/** 过期日期 */
	@TableField(value = "expired_date")
	private Date expiredDate;

	/** 过期凭证 */
	@TableField(value = "credentials_expired")
	private String credentialsExpired;

	/** 全名 */
	@TableField(value = "full_name")
	private String fullName;

	/** 性别(male=男，madam=女） */
	private String gender;

	/** 年龄 */
	@DecimalMin(value = "18", message = "年龄必须在18~120岁之间")
	@DecimalMax(value = "120", message = "年龄必须在18~120岁之间")
	private Integer age;

	/** 地址 */
	private String address;

	/** 移动电话 */
	@TableField(value = "mobile_phone")
	private String mobilePhone;

	/** 邮箱 */
	private String email;

	/** 用户类型 */
	@TableField(value = "user_type")
	private String userType;

	/** 描述 */
	private String description;

	/** 状态 */
	private String state;

	/** 创建时间 */
	@TableField(value = "create_time")
	private Date createTime;

	/** 创建人id */
	@TableField(value = "create_user_id")
	private Long createUserId;

	/** 更新时间 */
	@TableField(value = "update_time")
	private Date updateTime;

	/** 更新人id */
	@TableField(value = "update_user_id")
	private Long updateUserId;

	/** 是否删除 */
	@TableField(value = "is_delete")
	private String isDelete;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getExpiredDate() {
		return this.expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public String getCredentialsExpired() {
		return this.credentialsExpired;
	}

	public void setCredentialsExpired(String credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

}
