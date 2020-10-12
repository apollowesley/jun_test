package com.slavic.hors.orm.mapper;

import com.slavic.hors.orm.entity.HorsPortalRole;
import com.slavic.hors.orm.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (HorsPortalRole)表数据库访问层
 */
@Mapper
public interface HorsPortalRoleMapper extends BaseMapper<HorsPortalRole, Long>{

    List<HorsPortalRole> listByIds(List<Long> list);

}