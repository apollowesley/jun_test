package com.jfast.web.api.admin.mapper;

import com.jfast.web.common.core.base.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 管理员Mapper映射
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/27 19:03
 */
public interface SystemAdminMapper extends BaseMapper {

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    Map findByName(String userName);

    /**
     * 获取用户角色集合
     * @param adminId
     * @return
     */
    List<Map> findRoleByAdminId(Integer adminId);
}
