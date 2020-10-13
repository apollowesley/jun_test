package com.baomidou.crab.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.crab.sys.entity.RoleResource;
import com.baomidou.crab.sys.vo.ResourceZTreeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统角色资源关联表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2018-09-24
 */
public interface RoleResourceMapper extends BaseMapper<RoleResource> {

    List<ResourceZTreeVO> selectZTreeVO(@Param("roleId") Long roleId);
}
