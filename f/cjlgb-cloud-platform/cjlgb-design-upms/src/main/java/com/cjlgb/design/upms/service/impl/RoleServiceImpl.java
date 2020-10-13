package com.cjlgb.design.upms.service.impl;

import com.cjlgb.design.common.mybatis.service.impl.BaseServiceImpl;
import com.cjlgb.design.common.upms.entity.SysRole;
import com.cjlgb.design.upms.mapper.SysRoleMapper;
import com.cjlgb.design.upms.service.EmployeeRoleService;
import com.cjlgb.design.upms.service.RolePowerService;
import com.cjlgb.design.upms.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author WFT
 * @date 2020/7/13
 * description:
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements RoleService {

    private final RolePowerService rolePowerService;
    private final EmployeeRoleService employeeRoleService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Serializable roleId) {
        //  删除角色
        if (this.removeById(roleId)){
            //  删除关联表
            this.rolePowerService.deleteByRoleId(roleId);
            this.employeeRoleService.deleteByRoleId(roleId);
        }
    }
}
