package com.leo.vhr.service;

import com.leo.vhr.mapper.MenuMapper;
import com.leo.vhr.mapper.MenuRoleMapper;
import com.leo.vhr.model.Hr;
import com.leo.vhr.model.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/14
 * @version: 1.0
 */
@Service
public class MenuService
{
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Menu> getAllMenusById()
    {
        return menuMapper.getMenuByHrId(((Hr) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal()).getId());
    }

    public List<Menu> getAllMenusWithRole()
    {
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus()
    {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidById(Integer rid)
    {
        return menuMapper.getIdsById(rid);
    }

    @Transactional
    public boolean updateMenuRole(Integer rid,Integer[] mids)
    {
        menuRoleMapper.deleteByRid(rid);
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        return result==mids.length;
    }
}
