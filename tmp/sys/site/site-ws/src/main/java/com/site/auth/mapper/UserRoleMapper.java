package com.site.auth.mapper;

import org.springframework.stereotype.Repository;

import com.site.auth.model.UserRole;
import com.site.framework.mybatis.mapper.BaseMapper;

/**
 * 用户角色Mapper
 */
@Repository
public interface UserRoleMapper extends BaseMapper<String, UserRole> {

}
