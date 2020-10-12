package com.jfast.web.api.admin.mapper;

import com.jfast.web.common.core.base.BaseMapper;

import java.util.Map;

/**
 * 角色Mapper映射
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/28 11:21
 */
public interface SystemRoleMapper extends BaseMapper {

    /**
     * 根据名称获取角色
     * @param roleName
     * @return
     */
    Map findByName(String roleName);
}
