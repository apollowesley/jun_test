package org.apache.center.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.center.dao.ResourcesMapper;
import org.apache.center.model.Resources;
import org.apache.center.model.Role;
import org.apache.center.model.RoleResources;
import org.apache.center.service.ResourcesService;
import org.apache.center.service.RoleResourcesService;
import org.apache.center.service.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.playframework.log.Logger;
import org.apache.playframework.log.LoggerFactory;
import org.apache.playframework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.playframework.mybatisplus.mapper.EntityWrapper;

/**
 *
 * Resources 表数据服务层接口实现类
 *
 */
@Component
@Service
public class ResourcesServiceImpl extends BaseServiceImpl<ResourcesMapper, Resources> implements ResourcesService {

	@Autowired
	private RoleResourcesService roleResourcesService;

	@Autowired
	private RoleService roleService;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List<Resources> selectByRoleId(Long roleId) {
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(roleId);
		return selectByRoleIds(roleIds);
	}

	@Override
	public List<Resources> selectByRoleIds(List<Long> roleIds) {
		List<RoleResources> roleResourcesList = roleResourcesService.selectByRoleIds(roleIds);
		if (CollectionUtils.isNotEmpty(roleResourcesList)) {
			Set<Long> resourcesIds = new HashSet<Long>();
			for (RoleResources roleResources : roleResourcesList) {
				resourcesIds.add(roleResources.getResourcesId());
			}
			EntityWrapper<Resources> ew = new EntityWrapper<Resources>();
			ew.in("id", resourcesIds);
			return selectList(ew);
		}
		return null;
	}

	@Override
	public List<Resources> selectByUserId(String userName, Long userId) {
		List<Resources> resourcesList = null;
		if (StringUtils.equals("admin", userName)) {
			logger.debug("用户拥有的角色和资源, admin用户拥有所有的权限跟资源");
			resourcesList = selectList(null);
		} else {
			List<Role> roles = roleService.selectByUserId(userId);
			if (CollectionUtils.isNotEmpty(roles)) {
				List<Long> roleIds = new ArrayList<Long>();
				for (Role role : roles) {
					roleIds.add(role.getId());
					logger.debug("用户拥有的角色, userName:{}, 角色名称:{}", userName, role.getName());
				}
				resourcesList = selectByRoleIds(roleIds);
				if (logger.isDebugEnabled()) {
					if (CollectionUtils.isNotEmpty(resourcesList)) {
						for (Resources resources : resourcesList) {
							logger.debug("用户可以访问的资源, userName:{}, 资源名称:{}", userName, resources.getName());
						}
					}
				}
			}
		}
		return resourcesList;
	}

}
