package com.baomidou.crab.sys.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.crab.sys.dto.LoginDTO;
import com.baomidou.crab.sys.dto.UserDTO;
import com.baomidou.crab.sys.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author jobob
 * @since 2018-09-16
 */
public interface IUserService extends IService<User> {


    /**
     * <p>
     * 根据用户信息分页
     * </p>
     *
     * @param page 分页对象
     * @param user 用户信息
     * @return
     */
    IPage<User> page(IPage<User> page, User user);


    /**
     * <p>
     * 保存用户角色关系信息
     * </p>
     *
     * @param dto 用户 DTO
     * @return
     */
    boolean saveDto(UserDTO dto);


    /**
     * <p>
     * 修改用户角色关系信息
     * </p>
     *
     * @param dto 用户 DTO
     * @return
     */
    boolean updateDtoById(UserDTO dto);


    /**
     * <p>
     * 登录设置 COOKIE
     * </p>
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param dto      用户 DTO
     * @return
     */
    User loginByDto(HttpServletRequest request, HttpServletResponse response, LoginDTO dto);


    /**
     * <p>
     * 更新用户状态
     * </p>
     *
     * @param id     用户 ID
     * @param status 状态
     * @return
     */
    boolean updateStatus(Long id, Integer status);


    /**
     * <p>
     * 解锁用户
     * </p>
     *
     * @param id       用户 ID
     * @param password 明文密码
     * @return
     */
    boolean unlock(Long id, String password);


    /**
     * <p>
     * 重置指定 ID 用户的登录密码
     * </p>
     *
     * @param id 用户 ID
     * @return
     */
    boolean resetPassword(Long id);
}
