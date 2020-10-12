package com.slavic.hors.orm.mapper;

import com.slavic.hors.orm.entity.HorsPortalResource;
import com.slavic.hors.orm.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (HorsPortalResource)表数据库访问层
 */
@Mapper
public interface HorsPortalResourceMapper extends BaseMapper<HorsPortalResource, Long>{

    List<HorsPortalResource> listByIds(List<Long> ids);

}