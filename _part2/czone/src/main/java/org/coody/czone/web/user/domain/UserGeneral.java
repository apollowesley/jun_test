package org.coody.czone.web.user.domain;

import java.util.Date;

import org.coody.framework.jdbc.entity.DBModel;
/**
 * 用户基础信息
 * @author Coody
 * @date 2018年8月15日
 */
@SuppressWarnings("serial")
public class UserGeneral extends DBModel{

	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 用户邮箱
	 */
	private String email;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
