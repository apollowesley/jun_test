package com.kld.sys.service.impl;

import com.kld.sys.api.ISysUserService;
import com.kld.sys.dao.SysUserDao;
import com.kld.sys.dto.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by anpushang on 2016/3/13.
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public SysUser getSysUserById(Integer sysUserId) throws Exception {
        return this.sysUserDao.getSellCtgyById(sysUserId);
    }

    @Override
    public int deleteSysUserById(Integer sysUserId) throws Exception {
        return this.sysUserDao.deleteSysUserById(sysUserId);
    }
}
