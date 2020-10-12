package com.slavic.hors.orm.mapper;

import com.slavic.hors.orm.entity.HorsPortalUserRole;
import com.slavic.hors.orm.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * (HorsPortalUserRole)表数据库访问层
 */
@Mapper
public interface HorsPortalUserRoleMapper extends BaseMapper<HorsPortalUserRole, Long>{

    int deleteByUserId(Long userId);

}