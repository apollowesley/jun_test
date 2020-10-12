package com.handy.service.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.handy.service.entity.sys.SysPermission;

import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/22 17:22
 */
public interface ISysPermissionService extends IService<SysPermission>{

    /**
     * 根据角色列表查询对应的权限
     *
     * @param roleIdList
     * @return
     */
    List<SysPermission> findByRoleIdList(List<Long> roleIdList);
}
