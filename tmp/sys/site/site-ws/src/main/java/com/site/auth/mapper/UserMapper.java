package com.site.auth.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.site.auth.model.User;
import com.site.framework.base.Page;
import com.site.framework.mybatis.mapper.BaseMapper;

/**
 * 用户Mapper
 * 
 */
@Repository
public interface UserMapper extends BaseMapper<String, User> {

    /**
     * 根据用户名查询用户
     *
     * @param username
     *            用户名
     * @return user 用户
     */
    public User findUserByName(String username);

    /**
     * 查询店铺所有用户
     *
     * @param id
     *            店铺ID
     * @return
     * @author wangxin
     */
    public List<User> findUserByShop(String organizeId);

    /**
     * 查询组织下所有客服员工
     *
     * @return
     */
    public List<User> findUsers();

    /**
     * 根据条件（店铺、名称）查询客服人员
     *
     * @param shopId
     *            店铺ID
     * @param empName
     *            客服人员名称
     * @return
     */
    public List<User> findEmp(String roleCode, Integer status, String shopId, String empName);

    /**
     * 用户分页查询
     * 
     * @param page
     *            分页对象
     * @return
     */
    public List<User> paginateUserPage(Page<User> page);

    /**
     * 根据登录账号查询用户
     * 
     * @param loginName
     *            账号
     * @return 用户
     */
    public User findUserByLoginName(String loginName);

}
