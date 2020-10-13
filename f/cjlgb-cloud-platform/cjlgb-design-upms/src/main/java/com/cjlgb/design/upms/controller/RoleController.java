package com.cjlgb.design.upms.controller;

import com.cjlgb.design.common.core.bean.Validation;
import com.cjlgb.design.common.security.annotation.PreAuthorize;
import com.cjlgb.design.common.upms.entity.SysPower;
import com.cjlgb.design.common.upms.entity.SysRole;
import com.cjlgb.design.upms.service.PowerService;
import com.cjlgb.design.upms.service.RolePowerService;
import com.cjlgb.design.upms.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author WFT
 * @date 2020/7/25
 * description:角色控制器
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/role")
public class RoleController {

    private final RoleService roleService;
    private final PowerService powerService;
    private final RolePowerService rolePowerService;

    /**
     * 查询角色列表
     * @param parameter 查询条件
     * @return java.util.List
     */
    @GetMapping
    @PreAuthorize
    public List<SysRole> list(SysRole parameter){
        return this.roleService.selectList(parameter);
    }

    /**
     * 创建系统角色
     * @param parameter com.cjlgb.design.common.upms.entity.SysRole
     */
    @PostMapping
    @PreAuthorize
    public void add(@RequestBody @Validated SysRole parameter){
        this.roleService.create(parameter);
    }

    /**
     * 修改系统角色
     * @param parameter com.cjlgb.design.common.upms.entity.SysRole
     */
    @PutMapping
    @PreAuthorize
    public void edit(@RequestBody @Validated(value = Validation.Edit.class) SysRole parameter){
        this.roleService.update(parameter);
    }

    /**
     * 删除系统角色
     * @param roleId java.lang.Long
     */
    @DeleteMapping(value = "/{roleId}")
    @PreAuthorize
    public void delete(@PathVariable(value = "roleId") Long roleId){
        this.roleService.delete(roleId);
    }

    /**
     * 查询角色的权限列表
     * @param roleId java.lang.Long
     * @return java.util.Collection
     */
    @GetMapping(value = "/authorities/{roleId}")
    @PreAuthorize
    public Collection<String> authorities(@PathVariable(value = "roleId") Long roleId){
        return this.rolePowerService.selectAuthorities(Collections.singleton(roleId));
    }

    /**
     * 配置角色权限
     * @param parameter com.cjlgb.design.common.upms.entity.SysRole
     */
    @PutMapping(value = "/authorities")
    @PreAuthorize
    public void authorities(@RequestBody SysRole parameter){
        Assert.notNull(parameter.getId(),"Id不能为空");
        Assert.notNull(parameter.getAuthorities(),"权限标识不能为空");
        //  获取权限列表
        List<SysPower> powerList;
        if (parameter.getAuthorities().isEmpty()){
            powerList = new ArrayList<>(0);
        } else {
            powerList = this.powerService.selectListByAuthorities(parameter.getAuthorities());
        }
        this.rolePowerService.update(parameter.getId(),powerList);
    }

}
