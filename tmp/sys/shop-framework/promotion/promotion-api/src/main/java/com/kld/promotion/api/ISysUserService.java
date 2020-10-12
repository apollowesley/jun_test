package com.kld.promotion.api;

import com.kld.promotion.dto.SysUser;
import com.kld.promotion.dto.SysUser;

/**
 * Created by anpushang on 2016/3/13.
 */
public interface ISysUserService {

    public SysUser getSysUserById(Integer sysUserId) throws Exception;

    public int deleteSysUserById(Integer sysUserId)throws Exception;
}
