package com.cjlgb.design.upms.controller;

import com.cjlgb.design.common.core.bean.Validation;
import com.cjlgb.design.common.security.annotation.PreAuthorize;
import com.cjlgb.design.common.security.context.SecurityContextHolder;
import com.cjlgb.design.common.upms.entity.SysPower;
import com.cjlgb.design.upms.service.PowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author WFT
 * @date 2020/7/24
 * description:权限控制器
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/power")
public class PowerController {

    private final PowerService powerService;

    /**
     * 查询权限列表
     * @param parameter 查询条件
     * @return java.util.List
     */
    @GetMapping
    @PreAuthorize
    public List<SysPower> list(SysPower parameter){
        return this.powerService.selectList(parameter);
    }

    /**
     * 获取当前用户可见的权限树
     * @return java.util.List
     */
    @GetMapping(value = "/tree")
    @PreAuthorize
    public List<SysPower> list(){
        //  获取当前用户的权限列表
        Collection<String> authorities = SecurityContextHolder.getAuthentication().getAuthorities();
        return this.powerService.selectListTree(authorities);
    }

    /**
     * 创建权限
     * @param parameter com.cjlgb.design.common.upms.entity.SysPower
     */
    @PostMapping
    @PreAuthorize
    public void add(@RequestBody @Validated SysPower parameter){
        this.powerService.create(parameter);
    }

    /**
     * 修改权限
     * @param parameter com.cjlgb.design.common.upms.entity.SysPower
     */
    @PutMapping
    @PreAuthorize
    public void edit(@RequestBody @Validated(value = Validation.Edit.class) SysPower parameter){
        this.powerService.update(parameter);
    }

    /**
     * 删除权限
     * @param powerId java.lang.Long
     */
    @DeleteMapping(value = "/{powerId}")
    @PreAuthorize
    public void delete(@PathVariable(value = "powerId") Long powerId){
        this.powerService.delete(powerId);
    }

}
