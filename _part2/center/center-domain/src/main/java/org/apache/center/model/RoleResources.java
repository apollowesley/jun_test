package org.apache.center.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 *
 * 角色资源关链表
 *
 */
@TableName(value="role_resources",resultMap="BaseResultMap")
public class RoleResources implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主健 */
	@TableId(type = IdType.AUTO)
	private Long id;
	
	/** 角色id */
	@TableField(value = "role_id")
	private Long roleId;

	/** 资源id */
	@TableField(value = "resources_id")
	private Long resourcesId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourcesId() {
		return this.resourcesId;
	}

	public void setResourcesId(Long resourcesId) {
		this.resourcesId = resourcesId;
	}

}
