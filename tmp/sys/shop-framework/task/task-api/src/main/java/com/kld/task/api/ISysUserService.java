package com.kld.task.api;

import com.kld.task.dto.SysUser;

/**
 * Created by anpushang on 2016/3/13.
 */
public interface ISysUserService {

    public SysUser getSysUserById(Integer sysUserId) throws Exception;

    public int deleteSysUserById(Integer sysUserId)throws Exception;
}
