package com.jplee.boot.service;

import com.jplee.boot.mapper.SysUserMapper;
import com.jplee.boot.model.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author shkstart
 * @create 2019-01-26 23:56
 */
@Service
public class SysUserService {

    @Resource
    SysUserMapper sysUserMapper;

    public SysUser findbyUserid(Long id){
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        return sysUser;
    }

    public boolean regist(SysUser sysUser){
        int count = sysUserMapper.insertSelective(sysUser);
        if(count!=0){
            return true;
        }
        return false;
    }
}
