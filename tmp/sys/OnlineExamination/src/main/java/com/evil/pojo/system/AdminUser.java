package com.evil.pojo.system;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.evil.pojo.BaseEntity;

public class AdminUser extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name; // 用户名
	private String password; // 密码

	private long[] rightSum; // 所有权限的总和

	private boolean superAdmin; // 是否是超级管理员

	// 不能修改注册时间
	private Date regdate = new Date();

	// 表示User到Role的多对多的映射的集合
	private Set<Role> roles = new HashSet<Role>();

	// get/set方法....
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long[] getRightSum() {
		return rightSum;
	}

	public void setRightSum(long[] rightSum) {
		this.rightSum = rightSum;
	}

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	// 方法...
	/**
	 * 计算所有角色的权限
	 */
	public void calculateRightSum() {
		int pos = 0;
		long code = 0;
		for (Role role : roles) {
			// 判断是否是超级管理员
			if ("-1".equals(role.getRoleValue())) {
				superAdmin = true;
				roles = null;
				return;
			}
			for (Right right : role.getRights()) {
				pos = right.getRightPos();
				code = right.getRightCode();
				rightSum[pos] = rightSum[pos] | code;
			}
		}

		// 释放资源
		roles = null;
	}

	/**
	 * 判断该用户是否具备该权限
	 */
	public boolean hasRight(Right right) {
		int pos = right.getRightPos();
		long code = right.getRightCode();
		return (rightSum[pos] & code) != 0;
	}

}
