package com.slavic.hors.orm.mapper;

import com.slavic.hors.orm.entity.HorsPortalUser;
import com.slavic.hors.orm.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (HorsPortalUser)表数据库访问层
 */
@Mapper
public interface HorsPortalUserMapper extends BaseMapper<HorsPortalUser, Long>{
    List<HorsPortalUser> listByIds(List<Long> list);
}