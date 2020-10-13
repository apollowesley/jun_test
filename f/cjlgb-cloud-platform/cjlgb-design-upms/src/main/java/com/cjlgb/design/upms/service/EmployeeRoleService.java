package com.cjlgb.design.upms.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cjlgb.design.common.mybatis.service.BaseService;
import com.cjlgb.design.common.upms.entity.EmployeeRole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WFT
 * @date 2020/7/14
 * description:
 */
public interface EmployeeRoleService extends BaseService<EmployeeRole> {

    /**
     * 查询某员工的角色Id列表
     * @param employId 员工Id
     * @return java.util.Collection
     */
    default Collection<Long> selectRoleIds(Serializable employId){
        return this.list(
                Wrappers.<EmployeeRole>lambdaQuery()
                        .select(EmployeeRole::getRoleId)
                        .eq(EmployeeRole::getEmployId,employId))
                //  获取角色Id列表
                .stream().map(EmployeeRole::getRoleId).collect(Collectors.toList());
    }

    /**
     * 根据角色Id删除关联
     * @param roleId 角色Id
     */
    default void deleteByRoleId(Serializable roleId){
        this.remove(Wrappers.<EmployeeRole>lambdaQuery().eq(EmployeeRole::getRoleId,roleId));
    }

    /**
     * 根据员工Id删除关联
     * @param employId 员工Id
     */
    default void deleteByEmployId(Serializable employId){
        this.remove(Wrappers.<EmployeeRole>lambdaQuery().eq(EmployeeRole::getEmployId,employId));
    }

    /**
     * 创建员工与角色间的关联
     * @param employId 员工Id
     * @param roleIdList 角色Id列表
     */
    default void create(Long employId, List<Long> roleIdList){
        List<EmployeeRole> list = new ArrayList<>(roleIdList.size());
        roleIdList.forEach(item -> list.add(new EmployeeRole(employId,item)));
        this.saveBatch(list,50);
    }

    /**
     * 修改员工与角色间的关联
     * @param employId 员工Id
     * @param roleIdList 角色Id列表
     */
    default void update(Long employId, List<Long> roleIdList){
        //  删除原纪录
        this.deleteByEmployId(employId);
        //  创建新的关联
        this.create(employId,roleIdList);
    }
}
