package com.antdsp.web.dto;

import java.util.List;

public class RoleDto {
	private Long id;
	private String roleName;
	private String description;
	
	private List<Long> menuIds;

	public Long getId() {
		return id;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getDescription() {
		return description;
	}

	public List<Long> getMenuIds() {
		return menuIds;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMenuIds(List<Long> menuIds) {
		this.menuIds = menuIds;
	}
	
	
}
