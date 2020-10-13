package com.zb.service.sys.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zb.bean.sys.SysPermission;
import com.zb.common.AjaxResult;
import com.zb.common.Pager;
import com.zb.common.vo.PermissionVo;
import com.zb.common.vo.ZtreeVo;
import com.zb.mapper.sys.PermissionMapper;
import com.zb.service.BaseServiceImpl;
import com.zb.service.sys.PermissionService;
import com.zb.service.sys.RolePermissionService;

@Service("rermissionServiceImpl")
public class PermissionServiceImpl extends BaseServiceImpl<SysPermission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    
    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public List<SysPermission> getUserPermissions(String userName) {
        return permissionMapper.getUserPermissions(userName);
    }

    @Override
    public List<ZtreeVo> queryAllFormatWithZtree(boolean isShowTopParent) {
        List<ZtreeVo> results = new ArrayList<ZtreeVo>();
        if(isShowTopParent){
            ZtreeVo result = new ZtreeVo();
            result.setId("-1");
            result.setpId("0");
            result.setName("无上级权限");
            results.add(result);
        }
        List<SysPermission> permissions = permissionMapper.selectAll();
        if (CollectionUtils.isNotEmpty(permissions)) {
            for (SysPermission pms : permissions) {
                ZtreeVo foo = new ZtreeVo();
                foo.setId(String.valueOf(pms.getId()));
                foo.setpId(String.valueOf(pms.getParentId() == null ? "" : pms.getParentId()));
                foo.setName(pms.getName() + " - " + pms.getCode());
                results.add(foo);
            }
        }
        return results;
    }

    @Override
    public Pager<PermissionVo> getList(Pager<PermissionVo> pager, SysPermission permission) {
        if(pager.getUsePager()){
            PageHelper.offsetPage(pager.getOffset(), pager.getLimit());
        }
        List<PermissionVo> vos=permissionMapper.getList(permission,pager);
        pager.setRows(vos);
        PageInfo<PermissionVo> pageInfo=new PageInfo<PermissionVo>(vos);
        pager.setTotal(pageInfo.getTotal());
        return pager;
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void updatePermission(SysPermission permission) throws Exception {
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void deletePermission(SysPermission permission, AjaxResult<String> result) throws Exception {
        //检查该权限是否被分配给角色
        boolean bool = rolePermissionService.checkPermissionIsBindRole(permission.getId());
        if(bool){
            result.setCode(10005);
            result.setMsg("该权限已被其他角色所绑定使用，不能删除");
            return;
        }
        
        //删除权限
        permissionMapper.delete(permission);
    }
}
