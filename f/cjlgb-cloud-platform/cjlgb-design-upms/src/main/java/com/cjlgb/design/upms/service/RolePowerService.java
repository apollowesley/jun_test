package com.cjlgb.design.upms.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cjlgb.design.common.mybatis.service.BaseService;
import com.cjlgb.design.common.upms.entity.SysPower;
import com.cjlgb.design.common.upms.entity.SysRolePower;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WFT
 * @date 2020/7/14
 * description:
 */
public interface RolePowerService extends BaseService<SysRolePower> {

    /**
     * 创建角色与权限间的关联
     * @param roleId 角色Id
     * @param powerId 权限Id
     * @param permission 唯一标识
     */
    default void create(Long roleId,Long powerId,String permission){
        this.save(new SysRolePower(roleId,powerId,permission));
    }

    /**
     * 查询权限列表
     * @param roleIds 角色Id列表
     * @return java.util.Collection
     */
    default Collection<String> selectAuthorities(Collection<Long> roleIds){
        return this.list(
                Wrappers.<SysRolePower>lambdaQuery()
                        .select(SysRolePower::getPermission)
                        .in(SysRolePower::getRoleId,roleIds))
                //  获取权限列表
                .stream().map(SysRolePower::getPermission).collect(Collectors.toList());
    }

    /**
     * 根据权限Id修改唯一标识
     * @param powerId 权限Id
     * @param permission 唯一标识
     */
    default void updatePermissionByPowerId(Serializable powerId,String permission){
        this.update(Wrappers.<SysRolePower>lambdaUpdate()
                .set(SysRolePower::getPermission,permission)
                .eq(SysRolePower::getPowerId,powerId));
    }

    /**
     * 根据权限Id删除关联
     * @param powerId 权限Id
     */
    default void deleteByPowerId(Serializable powerId){
        this.remove(Wrappers.<SysRolePower>lambdaQuery().eq(SysRolePower::getPowerId,powerId));
    }

    /**
     * 根据角色Id删除关联
     * @param roleId 角色Id
     */
    default void deleteByRoleId(Serializable roleId){
        this.remove(Wrappers.<SysRolePower>lambdaQuery().eq(SysRolePower::getRoleId,roleId));
    }

    /**
     * 修改某角色与权限的关联
     * @param roleId 角色Id
     * @param powerList 权限列表
     */
    void update(Long roleId, List<SysPower> powerList);
}
