package com.leo.vhr.service;

import com.leo.vhr.mapper.RoleMapper;
import com.leo.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/17
 * @version: 1.0
 */
@Service
public class RoleService
{
    @Autowired
    RoleMapper roleMapper;

    public List<Role> getAllRoles()
    {
        return roleMapper.getAllRoles();
    }

    public Integer addRole(Role role)
    {
        if(role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        return roleMapper.insertSelective(role);
    }

    public Integer deleteRoleById(Integer rid)
    {
        return roleMapper.deleteByPrimaryKey(rid);
    }
}
