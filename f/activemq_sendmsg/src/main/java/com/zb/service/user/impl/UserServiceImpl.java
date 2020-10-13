package com.zb.service.user.impl;

import java.util.Date;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zb.bean.sys.SysUser;
import com.zb.common.Pager;
import com.zb.common.qo.UserQo;
import com.zb.common.vo.UserVo;
import com.zb.mapper.sys.UserMapper;
import com.zb.service.BaseServiceImpl;
import com.zb.service.sys.UserRoleService;
import com.zb.service.user.UserService;
import com.zb.util.CryptoUtils;

@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<SysUser> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;
    
    @Override
    public SysUser getUserByName(String username) {
        List<SysUser> list = userMapper.getUserByName(username);
        return (list == null || 0 == list.size()) ? null : list.get(0);
    }

    @Override
    public Pager<UserVo> getList(Pager<UserVo> pager, UserQo userQo) {
        if(!userQo.getHaveUserListPermission()){
            Subject subject = SecurityUtils.getSubject();
            SysUser user = getUserByName(String.valueOf(subject.getPrincipal()));
            userQo.setUserId(user.getId());
        }
        
        if (pager.getUsePager()) {
            PageHelper.offsetPage(pager.getOffset(), pager.getLimit());
        }
        
        List<UserVo> vos = userMapper.getUserList(userQo, pager);
        pager.setRows(vos);
        PageInfo<UserVo> pageInfo = new PageInfo<UserVo>(vos);
        pager.setTotal(pageInfo.getTotal());
        return pager;
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void deleteUser(SysUser user) throws Exception{
        //删除用户
        userMapper.delete(user);
        
        //删除用户对于的角色
        userRoleService.deleteByUserId(user.getId());
    }

    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void updateUser(SysUser user, UserQo qo) throws Exception{
        //更新用户
        user.setRealName(qo.getRealName());
        user.setStatus(qo.getStatus());
        user.setUpdateTime(new Date());
        user.setUserName(qo.getUserName());
        userMapper.updateByPrimaryKey(user);
        
        //更新用户角色列表
        userRoleService.updateUserRole(user,qo.getRoleIds());
    }

    
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public void saveUser(UserQo qo) throws Exception {
        //新增用户
        SysUser user = new SysUser();
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setRealName(qo.getRealName());
        user.setUserName(qo.getUserName());
        user.setStatus(qo.getStatus());
        user.setPassword(CryptoUtils.encodeMD5(qo.getPassword()));
        userMapper.insert(user);
        
        //保存角色
        userRoleService.updateUserRole(user, qo.getRoleIds());
    }
}
