package org.apache.center.service;

import java.util.List;

import org.apache.center.model.Resources;
import org.apache.playframework.service.BaseService;

public interface ResourcesService extends BaseService<Resources> {
	
	/**
	 * 根据角色ID，查询角色可以访问的资源
	 * @param roleId 角色id
	 * @return
	 */
	List<Resources> selectByRoleId(Long roleId);
	
	/**
	 * 根据角色ID集合，查询角色可以访问的资源
	 * @param roleIds 角色ID集合
	 * @return
	 */
	List<Resources> selectByRoleIds(List<Long> roleIds);
	
	/**
	 * 根据用户ID，查询用户可以访问的资源
	 * @param userName 用户名
	 * @param userId 用户id
	 * @return
	 */
	List<Resources> selectByUserId(String userName, Long userId);
}