package com.kld.cms.dao;

import com.kld.common.framework.dao.impl.SimpleDaoImpl;
import com.kld.cms.dto.SysUser;
import org.springframework.stereotype.Repository;

/**
 * Created by anpushang on 2016/3/13.
 */
@Repository
public class SysUserDao extends SimpleDaoImpl<SysUser> {

    public SysUser getSellCtgyById(Integer sysUserId) {
        return (SysUser)get("getById",sysUserId);
    }

    public int deleteSysUserById(Integer sysUserId) {
        return delete(sysUserId);
    }
}
