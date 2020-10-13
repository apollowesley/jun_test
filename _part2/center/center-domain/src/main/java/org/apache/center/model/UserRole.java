package org.apache.center.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 *
 * 用户角色关链表
 *
 */
@TableName(value="user_role",resultMap="BaseResultMap")
public class UserRole implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主健 */
	@TableId(type = IdType.AUTO)
	private Long id;
	
	/** 用户id，外健 */
	@TableField(value = "user_id")
	private Long userId;

	/** 角色id，外健 */
	@TableField(value = "role_id")
	private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
