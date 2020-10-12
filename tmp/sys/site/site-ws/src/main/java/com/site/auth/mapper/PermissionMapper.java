package com.site.auth.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.site.auth.model.Permission;
import com.site.framework.mybatis.mapper.BaseMapper;

/**
 * 菜单许可Mapper
 *
 */
@Repository
public interface PermissionMapper extends BaseMapper<String, Permission> {

    /**
     * 查询用户所能访问的所有菜单
     *
     * @param userId
     *            用户
     * @return permissions 菜单
     */
    public List<Permission> findPermissionByUserId(String userId);

    /**
     * 根据菜单KEY查询菜单
     *
     * @param permissionKey
     *            菜单KEY
     * @return
     */
    public Permission findPermissionByParentId(String permissionKey);
}
