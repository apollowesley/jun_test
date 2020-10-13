package com.cjlgb.design.upms.controller;

import com.cjlgb.design.common.core.bean.Validation;
import com.cjlgb.design.common.core.util.TreeUtils;
import com.cjlgb.design.common.security.annotation.PreAuthorize;
import com.cjlgb.design.common.security.context.SecurityContextHolder;
import com.cjlgb.design.common.upms.entity.SysMenu;
import com.cjlgb.design.upms.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author WFT
 * @date 2020/7/19
 * description:菜单控制器
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/menu")
public class MenuController {

    private final MenuService menuService;

    /**
     * 获取全部菜单树
     * @return java.util.List
     */
    @PreAuthorize
    @GetMapping(value = "/all/tree")
    public List<SysMenu> getAllMenuTree(){
        return TreeUtils.getInstance().build(this.menuService.list());
    }

    /**
     * 查询当前用户可见的菜单树
     * @return java.util.List
     */
    @PreAuthorize
    @GetMapping(value = "/tree")
    public List<SysMenu> getMenuTree(){
        //  获取当前用户权限列表
        Collection<String> authorities = SecurityContextHolder.getAuthentication().getAuthorities();
        //  获取菜单列表
        return TreeUtils.getInstance().build(this.menuService.selectMenuList(authorities));
    }

    /**
     * 创建菜单
     * @param parameter com.cjlgb.design.common.upms.entity.SysMenu
     */
    @PostMapping
    @PreAuthorize
    public void add(@RequestBody @Validated SysMenu parameter){
        this.menuService.create(parameter);
    }

    /**
     * 修改菜单
     * @param parameter com.cjlgb.design.common.upms.entity.SysMenu
     */
    @PutMapping
    @PreAuthorize
    public void edit(@RequestBody @Validated(value = Validation.Edit.class) SysMenu parameter){
        this.menuService.update(parameter);
    }

    /**
     * 删除菜单
     * @param menuId java.lang.Long
     */
    @DeleteMapping(value = "/{menuId}")
    @PreAuthorize
    public void delete(@PathVariable(value = "menuId") Long menuId){
        this.menuService.delete(menuId);
    }
}
