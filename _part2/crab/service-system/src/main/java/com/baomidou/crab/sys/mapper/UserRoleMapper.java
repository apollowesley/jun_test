package com.baomidou.crab.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.crab.sys.entity.UserRole;
import com.baomidou.crab.sys.vo.UserRoleSelectedVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统用户角色关联表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2018-09-16
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {


    /**
     * <p>
     * 根据用户 ID 查询用户选择角色 VO
     * </p>
     *
     * @param userId 用户 ID
     * @return
     */
    List<UserRoleSelectedVO> selectSelectedVO(@Param("userId") Long userId);
}
