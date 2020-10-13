package org.apache.center.service.impl;

import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;

import org.apache.playframework.mybatisplus.mapper.EntityWrapper;
import org.apache.playframework.service.impl.BaseServiceImpl;
import org.apache.center.service.UserRoleService;
import org.apache.center.model.UserRole;

import java.util.List;

import org.apache.center.dao.UserRoleMapper;
/**
 *
 * UserRole 表数据服务层接口实现类
 *
 */
@Component  
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

	@Override
	public List<UserRole> selectByUserId(Long userId) {
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		EntityWrapper<UserRole> ew = new EntityWrapper<UserRole>(userRole);
		return selectList(ew);
	}

	@Override
	public boolean addUserRole(Long userId, Long roleId) {
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		return insert(userRole);
	}

	@Override
	public boolean deleteUserRole(Long userId, Long roleId) {
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		EntityWrapper<UserRole> ew = new EntityWrapper<UserRole>(userRole);
		return delete(ew);
	}

}


