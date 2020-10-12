package com.site.auth.mapper;

import org.springframework.stereotype.Repository;

import com.site.auth.model.RolePermission;
import com.site.framework.mybatis.mapper.BaseMapper;

/**
 * 角色菜单许可Mapper
 */
@Repository
public interface RolePermissionMapper extends BaseMapper<String, RolePermission> {

    public RolePermission findRolePermission(RolePermission per);

}
