package com.baomidou.crab.sys.service;

import java.util.List;

import com.baomidou.crab.sys.entity.UserRole;
import com.baomidou.crab.sys.vo.UserRoleSelectedVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户角色关联表 服务类
 * </p>
 *
 * @author jobob
 * @since 2018-09-16
 */
public interface IUserRoleService extends IService<UserRole> {


    /**
     * <p>
     * 查询用户选择角色 VO
     * </p>
     *
     * @param userId 用户 ID
     * @return
     */
    List<UserRoleSelectedVO> listSelectedVO(Long userId);


    /**
     * <p>
     * 更新用户角色关联信息
     * </p>
     *
     * @param userId 用户 ID
     * @param ids    角色 ID 集合
     * @return
     */
    boolean updateByIds(Long userId, List<Long> ids);


    /**
     * <p>
     * 删除用户角色关系
     * </p>
     *
     * @param userId 用户 ID
     * @return
     */
    boolean removeByUserId(Long userId);
}
