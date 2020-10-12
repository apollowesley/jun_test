package com.site.auth.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.site.auth.model.Role;
import com.site.framework.base.Page;
import com.site.framework.mybatis.mapper.BaseMapper;

/**
 * 角色Mapper
 */
@Repository
public interface RoleMapper extends BaseMapper<String, Role> {

    /**
     * 根据用户查询对应所有角色
     *
     * @param userId
     *            用户
     * @return roles 所有角色
     */
    public List<Role> findRoleByUserId(String userId);

    /**
     * 根据编码查询角色
     *
     * @param code
     *            角色编码
     * @return
     */
    public Role findRoleByCode(String code);

    /**
     * @Description 分页查询角色
     * @author 王鑫
     * @param page 分页对象
     * @return 角色对象集合
     */
    public List<Role> paginateRolePage(Page<Role> page);
}
