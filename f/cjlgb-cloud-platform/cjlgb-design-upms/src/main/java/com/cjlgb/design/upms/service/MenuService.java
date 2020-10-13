package com.cjlgb.design.upms.service;

import com.cjlgb.design.common.mybatis.service.BaseService;
import com.cjlgb.design.common.upms.entity.SysMenu;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author WFT
 * @date 2020/7/19
 * description:
 */
public interface MenuService extends BaseService<SysMenu> {

    /**
     * 创建菜单
     * @param params com.cjlgb.design.common.upms.entity.SysMenu
     */
    void create(SysMenu params);

    /**
     * 修改菜单
     * @param params com.cjlgb.design.common.upms.entity.SysMenu
     */
    void update(SysMenu params);

    /**
     * 删除菜单
     * @param id java.io.Serializable
     */
    void delete(Serializable id);

    /**
     * 获取菜单列表
     * @param authorities 权限标识列表
     * @return java.util.Collection
     */
    List<SysMenu> selectMenuList(Collection<String> authorities);
}
