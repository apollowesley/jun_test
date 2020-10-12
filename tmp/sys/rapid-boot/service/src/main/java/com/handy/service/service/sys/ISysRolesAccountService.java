package com.handy.service.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.handy.service.entity.sys.SysRolesAccount;

import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/22 17:17
 */
public interface ISysRolesAccountService extends IService<SysRolesAccount> {

    /**
     * 根据用户id查询对应角色
     *
     * @param accountId
     * @return
     */
    List<SysRolesAccount> findByAccountId(Long accountId);
}
