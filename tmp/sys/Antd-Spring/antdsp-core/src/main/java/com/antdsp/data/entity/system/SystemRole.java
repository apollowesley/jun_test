package com.antdsp.data.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.antdsp.data.entity.AbstractEntity;

@Table(name="tb_system_role")
@Entity
public class SystemRole extends AbstractEntity {
	
	@Column(name="role_name" , nullable=false , length= 32)
	private String roleName;
	
	@Column(name="description" , length= 32)
	private String description;

	public String getRoleName() {
		return roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
