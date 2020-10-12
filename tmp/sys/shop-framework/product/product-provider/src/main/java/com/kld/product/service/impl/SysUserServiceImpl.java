package com.kld.product.service.impl;

import com.kld.product.api.ISysUserService;
import com.kld.product.dao.SysUserDao;
import com.kld.product.dto.SysUser;
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
