package com.jfast.web.api.admin.service;

import com.jfast.web.api.admin.mapper.SystemAdminMapper;
import com.jfast.web.common.core.base.BaseService;
import com.jfast.web.common.core.model.Result;
import com.jfast.web.common.core.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/27 19:04
 */
@Service
public class SystemAdminService extends BaseService<SystemAdminMapper> {

    @Autowired
    private SystemMenuService systemMenuService;

    @Override
    public Result<Map> pagination(Map params) {
        try {
            Result<Map> result = super.pagination(params);
            if (result.isSuccess()) {
                Map data = result.getData();
                List<Map> dataList = (List<Map>)data.get("dataList");
                for (Map admin : dataList) {
                    List<Map> roleList = mapper.findRoleByAdminId((Integer) admin.get("id"));
                    List<Integer> roleIds = new ArrayList<>();
                    for (Map role : roleList) {
                        roleIds.add((Integer)role.get("role_id"));
                    }
                    admin.put("roleIds", roleIds);
                }
                return result;
            }
        } catch (Exception e) {
            logger.error("获取数据异常", e);
            return Result.fail();
        }
        return Result.fail();
    }

    public Map findByName(String userName) {
        Map userMap = mapper.findByName(userName);
        if (ObjectUtils.isNotEmpty(userMap)) {
            List<Map> permissionList = null;
            boolean isSuper = (boolean) userMap.get("super_flag");
            if (isSuper) { // 超级管理员加载所有权限
                permissionList = systemMenuService.queryList(new HashMap());
            }
            else {
                permissionList = this.loadUserRoleAndMenu((Integer) userMap.get("id"));
            }
            userMap.put("permissionList", permissionList);
        }
        return userMap;
    }


    /**
     * 加载用户角色权限
     */
    private List<Map> loadUserRoleAndMenu(Integer adminId) {
        return systemMenuService.findMenuByAdminId(adminId);
    }
}
