package com.handy.service.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.handy.service.entity.sys.SysAccount;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/22 14:48
 */
public interface ISysAccountService extends IService<SysAccount> {

    /**
     * 登录
     *
     * @return
     */
    SysAccount login(SysAccount sysAccount);

    /**
     * 注册
     *
     * @return
     */
    SysAccount register(SysAccount sysAccount);

}