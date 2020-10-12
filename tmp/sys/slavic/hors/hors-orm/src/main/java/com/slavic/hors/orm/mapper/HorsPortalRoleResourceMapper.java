package com.slavic.hors.orm.mapper;

import com.slavic.hors.orm.entity.HorsPortalRoleResource;
import com.slavic.hors.orm.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (HorsPortalRoleResource)表数据库访问层
 */
@Mapper
public interface HorsPortalRoleResourceMapper extends BaseMapper<HorsPortalRoleResource, Long>{

    int deleteByRoleId(Long roleId);

    List<HorsPortalRoleResource> listByRolesId(List<Long> list);
}