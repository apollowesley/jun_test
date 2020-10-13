package org.apache.center.service;

import java.util.List;

import org.apache.center.model.Role;
import org.apache.playframework.service.BaseService;

public interface RoleService extends BaseService<Role> {
	
	/**
	 * 根据用户ID查询角色
	 * @param userId
	 * @return
	 */
	List<Role> selectByUserId(Long userId);
}