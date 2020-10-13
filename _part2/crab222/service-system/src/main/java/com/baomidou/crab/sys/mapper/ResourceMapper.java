package com.baomidou.crab.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.crab.sys.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统资源表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2018-09-13
 */
public interface ResourceMapper extends BaseMapper<Resource> {


    /**
     * <p>
     * 查询用户菜单权限
     * </p>
     *
     * @param userId 用户 ID
     * @return
     */
    List<Resource> selectMenuByUserId(@Param("userId") Long userId);
}
