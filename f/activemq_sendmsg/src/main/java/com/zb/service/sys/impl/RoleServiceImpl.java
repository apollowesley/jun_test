package com.zb.service.sys.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zb.bean.sys.SysRole;
import com.zb.bean.sys.SysRolePermission;
import com.zb.common.AjaxResult;
import com.zb.common.Pager;
import com.zb.common.enums.Status;
import com.zb.common.vo.RoleVo;
import com.zb.common.vo.ZtreeVo;
import com.zb.mapper.sys.RoleMapper;
import com.zb.mapper.sys.RolePermissionMapper;
import com.zb.service.BaseServiceImpl;
import com.zb.service.sys.RolePermissionService;
import com.zb.service.sys.RoleService;
import com.zb.service.sys.UserRoleService;

import tk.mybatis.mapper.entity.Example;

@Service("roleServiceImpl")
public class RoleServiceImpl extends BaseServiceImpl<SysRole> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    
    @Autowired
    private RolePermissionService rolePermissionService;
    
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<SysRole> getUserRoles(String userName) {
        return roleMapper.getUserRoles(userName);
    }

    @Override
    public Pager<RoleVo> getList(Pager<RoleVo> pager, SysRole role) {
        if(pager.getUsePager()){
            PageHelper.offsetPage(pager.getOffset(), pager.getLimit());
        }
        List<RoleVo> vos=roleMapper.getList(role,pager);
        pager.setRows(vos);
        PageInfo<RoleVo> pageInfo=new PageInfo<RoleVo>(vos);
        pager.setTotal(pageInfo.getTotal());
        return pager;
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void addRole(SysRole role, Long[] permissionIds) throws Exception{
        //添加角色
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        role.setStatus(Status.ENABLE);//角色状态，0：可用  1：不可用
        roleMapper.insert(role);
        
        //设置权限
        for (int i = 0; i < permissionIds.length; i++) {
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permissionIds[i]);
            rolePermissionMapper.insert(rolePermission);
        }
    }

    @Override
    public List<SysRole> getAllRole(boolean haveAdmin) {
        if(haveAdmin){
            return roleMapper.selectAll();
        }
        
        Example example = new Example(SysRole.class);
        example.createCriteria().andNotEqualTo("id", 1);
        return roleMapper.selectByExample(example);
    }

    @Override
    public List<ZtreeVo> queryAllFormatWithZtree(boolean isShowTopParent,boolean haveAdmin) {
        List<ZtreeVo> results = new ArrayList<ZtreeVo>();
        if(isShowTopParent){
            ZtreeVo result = new ZtreeVo();
            result.setId("-1");
            result.setpId("0");
            result.setName("用户所属角色");
            results.add(result);
        }
        List<SysRole> roles = getAllRole(haveAdmin);
        if (CollectionUtils.isNotEmpty(roles)) {
            for (SysRole role : roles) {
                ZtreeVo foo = new ZtreeVo();
                foo.setId(String.valueOf(role.getId()));
                foo.setpId(String.valueOf(-1));
                foo.setName(role.getName());
                results.add(foo);
            }
        }
        return results;
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void updateRole(SysRole role, Long[] permissionIds) throws Exception {
        //更新角色
        roleMapper.updateByPrimaryKeySelective(role);
        
        //更新角色权限
        rolePermissionService.updateRolePermission(role,permissionIds);
    }

    @Override
    public void deleteRole(SysRole role,AjaxResult<String> result) throws Exception {
        //校验该角色是否被用户绑定使用
        boolean bool = userRoleService.checkRoleIsBindUser(role.getId());
        if(bool){
            result.setCode(10004);
            result.setMsg("该角色已被其他用户所绑定使用，不能删除");
            return;
        }
        //删除角色
        roleMapper.delete(role);
        
        //删除角色权限
        rolePermissionService.deleteRolePermission(role);
    }
}
