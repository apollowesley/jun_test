package org.apache.center.service;

import java.util.List;

import org.apache.center.model.UserRole;
import org.apache.playframework.service.BaseService;

public interface UserRoleService extends BaseService<UserRole> {

	/**
	 * 根据用户id查询 用户角色关连关系
	 * @param userId
	 * @return
	 */
	public List<UserRole> selectByUserId(Long userId);

	/**
	 * 添加用户角色关链
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public boolean addUserRole(Long userId, Long roleId);

	/**
	 * 删除用户角色关链
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public boolean deleteUserRole(Long userId, Long roleId);
}