package com.antdsp.web.dto;

import java.util.List;

import com.antdsp.data.entity.User;
import com.antdsp.data.entity.system.SystemRole;

public class SystemUserDto {
	private User user;
	
	private List<Long> roleIds;
	
	private List<SystemRole> roles;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public List<SystemRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SystemRole> roles) {
		this.roles = roles;
	}
	
	
}
