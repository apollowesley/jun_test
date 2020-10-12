package com.site.auth.service;

import java.util.List;

import com.site.auth.model.Permission;
import com.site.auth.model.Role;
import com.site.auth.model.User;
import com.site.framework.base.Page;

/**
 * 
 * 用户接口
 * 
 */
public interface AuthService {

    /**
     * 根据条件查询列表，包含分页
     * 
     * @param page
     * @return
     */
    public Page<User> paginateUser(Page<User> page);

    /**
     * 新增数据
     * 
     * @param user
     *            用户
     */
    public boolean insertUser(User user);

    /**
     * 修改用户数据
     * 
     * @param user
     *            用户
     */
    public void updateUser(User user);

    /**
     * 根据用户ID查询用户
     * 
     * @param userId
     *            用户ID
     * @return 用户
     */
    public User getUserByUserId(String userId);

    /**
     * 根据登录账号查询用户
     * 
     * @param loginName
     *            账号
     * @return 用户
     */
    public User findUserByLoginName(String loginName);

    /**
     * 根据用户ID查询所对应的角色
     * 
     * @param userId
     *            用户ID
     * @return 角色
     */
    public List<Role> findRoleByUserId(String userId);

    /**
     * 查询所有菜单
     *
     * @return permissions 菜单
     */
    public List<Permission> getPermissions();

    /**
     * 查询用户所能访问的所有菜单
     *
     * @param userId
     *            用户ID
     * @return permissions 菜单
     */
    public List<Permission> getPermissionsByUserId(String userId);

    /**
     * 添加一个角色 ，若已经存在同名角色，则不创建
     *
     * @param role
     *            角色对象
     */
    public void addRole(Role role);

    /**
     * 添加 菜单
     *
     * @param permission
     *            菜单项
     */
    public void addPermission(Permission permission);

    /**
     * 给角色授权
     *
     * @param roleCode
     *            角色编码
     * @param permissionKey
     *            授权对应的KEY
     */
    public void addRolePermission(String roleCode, String permissionKey);

    /**
     * 根据编码查询角色
     *
     * @param code
     *            角色编码
     * @return 角色
     */
    public Role findRoleByCode(String code);

    /**
     * 新增用户
     *
     * @param user
     *            用户
     * @param role
     *            角色
     */
    public void addUser(User user, Role role);

    public void add(List<User> list1, List<User> list2);

    public void addUser(List<User> list);

    public void saveUser(List<User> list);

    /**
     * 
     * @Description 分布查询角色
     * @author 王鑫
     * @return 角色
     */
    public Page<Role> paginateRole(Page<Role> page);

}
