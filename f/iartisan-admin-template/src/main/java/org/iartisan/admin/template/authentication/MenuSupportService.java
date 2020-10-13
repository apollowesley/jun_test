package org.iartisan.admin.template.authentication;

import com.google.common.collect.Lists;
import org.iartisan.admin.template.authentication.service.code.PermissionType;
import org.iartisan.admin.template.authentication.service.entity.MenuEntity;
import org.iartisan.admin.template.dao.mapper.SystemMenuMapper;
import org.iartisan.admin.template.dao.mapper.SystemRolePermissionMapper;
import org.iartisan.admin.template.dao.model.SystemMenuDO;
import org.iartisan.admin.template.dao.model.SystemRolePermissionDO;
import org.iartisan.runtime.utils.CollectionUtil;
import org.iartisan.runtime.utils.UUIDUtil;
import org.iartisan.runtime.web.authentication.MenuTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author King
 * @since 2018/2/26
 */
@Service
public class MenuSupportService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Autowired
    private RoleSupportService roleSupportService;

    @Autowired
    private SystemRolePermissionMapper systemRolePermissionMapper;

    public List<MenuTree> getMenuPageData(String menuName) {
        List<SystemMenuDO> dbResult = systemMenuMapper.selectFirstMenus(new SystemMenuDO());
        List<MenuTree> dataList = Lists.newArrayList();
        for (SystemMenuDO tree : dbResult) {
            //查询子菜单
            MenuTree first = convertMenuTree(tree);
            SystemMenuDO dbQuery = new SystemMenuDO();
            dbQuery.setParentMenuId(tree.getMenuId());
            dataList.add(first);
            List<SystemMenuDO> systemMenuDOS = systemMenuMapper.selectSecondMenus(dbQuery);
            if (CollectionUtil.isNotEmpty(systemMenuDOS)) {
                for (SystemMenuDO o : systemMenuDOS) {
                    MenuTree second = convertMenuTree(o);
                    second.setParentMenuId(tree.getMenuId());
                    dataList.add(second);
                }
            }
        }
        return dataList;
    }


    public List<MenuTree> getFirstMenus() {
        List<SystemMenuDO> firstMenuList = systemMenuMapper.selectFirstMenus(new SystemMenuDO());
        List<MenuTree> result = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(firstMenuList)) {
            for (SystemMenuDO systemMenuDO : firstMenuList) {
                MenuTree tree = new MenuTree();
                tree.setId(systemMenuDO.getMenuId());
                tree.setTitle(systemMenuDO.getMenuName());
                result.add(tree);
            }
        }
        return result;
    }

    public void addMenu(MenuEntity menuEntity) {
        SystemMenuDO dbInsert = new SystemMenuDO();
        dbInsert.setMenuId(UUIDUtil.shortId());
        dbInsert.setMenuName(menuEntity.getMenuName());
        dbInsert.setParentMenuId(menuEntity.getParentMenuId());
        dbInsert.setMenuUrl(menuEntity.getMenuUrl());
        dbInsert.setMenuIcon(menuEntity.getMenuIcon());
        dbInsert.setMenuPermission(menuEntity.getMenuPermission());
        systemMenuMapper.insert(dbInsert);
    }

    public void deleteData(String menuId) {
        systemMenuMapper.deleteById(menuId);
    }

    public MenuEntity getMenuById(String menuId) {
        SystemMenuDO dbResult = systemMenuMapper.selectById(menuId);
        MenuEntity result = new MenuEntity();
        result.setParentMenuId(dbResult.getParentMenuId());
        result.setMenuUrl(dbResult.getMenuUrl());
        result.setMenuName(dbResult.getMenuName());
        result.setMenuIcon(dbResult.getMenuIcon());
        result.setMenuPermission(dbResult.getMenuPermission());
        result.setParentMenuId(dbResult.getParentMenuId());
        result.setMenuId(dbResult.getMenuId());
        return result;
    }

    public void modifyData(MenuEntity menuEntity) {
        SystemMenuDO dbModify = new SystemMenuDO();
        dbModify.setMenuId(menuEntity.getMenuId());
        dbModify.setMenuName(menuEntity.getMenuName());
        dbModify.setMenuPermission(menuEntity.getMenuPermission());
        dbModify.setMenuUrl(menuEntity.getMenuUrl());
        dbModify.setMenuIcon(menuEntity.getMenuIcon());
        systemMenuMapper.updateById(dbModify);
    }

    //获取菜单列表
    public List<MenuTree> getMenus(String userId) {
        List<String> roles = roleSupportService.getRoleIdsByUserId(userId);
        if (CollectionUtil.isEmpty(roles)) {
            return null;
        }
        SystemRolePermissionDO dbQuery = new SystemRolePermissionDO();
        dbQuery.setRoleIds(roles);
        dbQuery.setPermissionType(PermissionType.m.name());
        List<String> menuIds = systemRolePermissionMapper.selectPermissions(dbQuery);
        if (CollectionUtil.isEmpty(menuIds)) {
            return null;
        }
        SystemMenuDO systemMenuDO = new SystemMenuDO();
        systemMenuDO.setMenuIds(menuIds);
        List<SystemMenuDO> firstMenus = systemMenuMapper.selectFirstMenus(systemMenuDO);
        //得到二级菜单
        if (CollectionUtil.isEmpty(firstMenus)) {
            return null;
        }
        List<MenuTree> result = new ArrayList<>();
        for (SystemMenuDO firstMenu : firstMenus) {
            MenuTree firstTree = convertMenuTree(firstMenu);
            SystemMenuDO secondQuery = new SystemMenuDO();
            secondQuery.setMenuIds(menuIds);
            secondQuery.setParentMenuId(firstMenu.getMenuId());
            List<SystemMenuDO> secondMenus = systemMenuMapper.selectSecondMenus(secondQuery);
            if (null != secondMenus) {
                //添加二级菜单
                List<MenuTree> children = new ArrayList<>();
                for (SystemMenuDO secondMenu : secondMenus) {
                    MenuTree secondTree = convertMenuTree(secondMenu);
                    children.add(secondTree);
                }
                firstTree.setChildren(children);
            }
            result.add(firstTree);
        }
        //查找二级菜单
        return result;
    }

    private MenuTree convertMenuTree(SystemMenuDO data) {
        MenuTree tree = new MenuTree();
        tree.setId(data.getMenuId());
        tree.setTitle(data.getMenuName());
        tree.setIcon(data.getMenuIcon());
        tree.setHref(data.getMenuUrl());
        tree.setPermission(data.getMenuPermission());
        return tree;
    }
}
