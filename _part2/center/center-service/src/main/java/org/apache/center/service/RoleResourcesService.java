package org.apache.center.service;

import java.util.List;

import org.apache.center.model.RoleResources;
import org.apache.playframework.service.BaseService;

public interface RoleResourcesService extends BaseService<RoleResources> {
	
	/**
	 * 根据角色id集合查询出可以访问的 资源
	 * @param roleIds
	 * @return
	 */
	public List<RoleResources> selectByRoleIds(List<Long> roleIds);
	
	/**
	 * 保存    角色与资源 关系
	 * @param bigInteger
	 * @param resourcesIds
	 * @return
	 */
	public boolean saveRoleResources(Long roleId,
			Long[] resourcesIds);
}