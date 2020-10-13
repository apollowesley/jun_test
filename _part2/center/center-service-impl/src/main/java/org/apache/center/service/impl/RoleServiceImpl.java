package org.apache.center.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.center.dao.RoleMapper;
import org.apache.center.model.Role;
import org.apache.center.model.UserRole;
import org.apache.center.service.RoleService;
import org.apache.center.service.UserRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.playframework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
/**
 *
 * Role 表数据服务层接口实现类
 *
 */
@Component  
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

	@Autowired
	private UserRoleService userRoleService;
	
	@Override
	public List<Role> selectByUserId(Long userId) {
		List<UserRole> userRoles = userRoleService.selectByUserId(userId);
		if (CollectionUtils.isNotEmpty(userRoles)) {
			List<Long> roleIds = new ArrayList<Long>();
			for (UserRole userRole : userRoles) {
				roleIds.add(userRole.getRoleId());
			}
			return selectBatchIds(roleIds);
		}
		return null;
	}


}


