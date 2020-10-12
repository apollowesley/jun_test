package com.site.auth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.site.auth.mapper.PermissionMapper;
import com.site.auth.mapper.RoleMapper;
import com.site.auth.mapper.RolePermissionMapper;
import com.site.auth.mapper.UserMapper;
import com.site.auth.mapper.UserRoleMapper;
import com.site.auth.model.Permission;
import com.site.auth.model.Role;
import com.site.auth.model.RolePermission;
import com.site.auth.model.User;
import com.site.auth.model.UserRole;
import com.site.auth.service.AuthService;
import com.site.auth.service.UserService;
import com.site.auth.util.UserEncodes;
import com.site.common.constants.Constants;
import com.site.common.exception.BusinessException;
import com.site.framework.base.Page;
import com.site.framework.key.GeneratePrimaryKey;
import com.site.util.DateUtil;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserService userService;

    @Override
    public Page<User> paginateUser(Page<User> page) {
        List<User> users = userMapper.paginateUserPage(page);
        if (log.isDebugEnabled()) {
            log.debug("{}", JSON.toJSONString(users));
        }
        page.setResultList(users);
        return page;
    }

    @Override
    public boolean insertUser(User user) {
        user.setId(GeneratePrimaryKey.getPkValue("T_AUTH_USER"));
        user.setIsDel(Constants.IS_DEL_N);
        user.setCreateTime(DateUtil.getSystemDateTime());
        int flag = userMapper.insert(user);
        if (flag != 1) {
            return false;
        }
        return true;
    }

    @Override
    public void updateUser(User user) {
        int flag = userMapper.update(user);
        log.warn("## 修改用户返回结果={}", flag);
    }

    @Override
    public User getUserByUserId(String userId) {
        return userMapper.findById(userId);
    }

    @Override
    public User findUserByLoginName(String loginName) {
        return userMapper.findUserByLoginName(loginName);
    }

    @Override
    public List<Role> findRoleByUserId(String userId) {
        if (StringUtils.isBlank(userId))
            return null;
        return roleMapper.findRoleByUserId(userId);
    }

    @Override
    public List<Permission> getPermissions() {
        Map<String, Object> param = new HashMap<String, Object>();
        return permissionMapper.findAllByFilter(param);
    }

    @Override
    public List<Permission> getPermissionsByUserId(String userId) {
        return permissionMapper.findPermissionByUserId(userId);
    }

    public void addRole(Role role) {
        if (role == null || StringUtils.isBlank(role.getRoleName())) {
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("## 添加角色 : {}", role.getRoleName());
        }
        Role r = findRoleByCode(role.getRoleCode());
        if (r == null) {
            role.setId(GeneratePrimaryKey.getPkValue("T_AUTH_ROLE"));
            role.setIsDel(Constants.IS_DEL_N);
            role.setCreateTime(DateUtil.getSystemDateTime());
            roleMapper.insert(role);
        }
    }

    @Override
    public void addPermission(Permission permission) {
        if (permission == null || StringUtils.isBlank(permission.getMenuCode()) || StringUtils.isBlank(permission.getParentId()) || StringUtils.isBlank(permission.getMenuName())) {
            throw new BusinessException("## 创建菜单出错；菜单项数据不完整，无法进行创建。");
        }
        permission.setId(GeneratePrimaryKey.getPkValue("T_AUTH_PERMISSION"));
        permission.setIsDel(Constants.IS_DEL_N);
        permission.setCreateTime(DateUtil.getSystemDateTime());
        permissionMapper.insert(permission);
    }

    @Override
    public void addRolePermission(String roleCode, String permissionId) {
        Role role = findRoleByCode(roleCode);
        if (role == null) {
            throw new BusinessException("## 给角色授权失败， 角色编码错误");
        }
        Permission permis = permissionMapper.findById(permissionId);
        if (permis == null) {
            throw new BusinessException("## 给角色授权失败， 授权KEY错误");
        }

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(role.getId());
        rolePermission.setPermissionId(permis.getId());
        RolePermission rp = rolePermissionMapper.findRolePermission(rolePermission);
        if (rp == null) {
            rolePermission.setIsDel(Constants.IS_DEL_N);
            rolePermission.setCreateTime(DateUtil.getSystemDateTime());
            rolePermission.setId(GeneratePrimaryKey.getPkValue("T_AUTH_ROLE_PERMISSION"));
            rolePermissionMapper.insert(rolePermission);
        }

    }

    @Override
    public Role findRoleByCode(String code) {
        if (log.isDebugEnabled()) {
            log.debug("## 根据编码查询角色 :　{}", code);
        }
        return roleMapper.findRoleByCode(code);
    }

    @Override
    public void addUser(User user, Role role) {
        if (user == null || role == null) {
            throw new BusinessException("user.registr.error", "注册信息错误");
        }

        if (StringUtils.isBlank(user.getLoginName()) || StringUtils.isBlank(user.getPassword())) {
            throw new BusinessException("user.registr.error", "注册信息错误");
        }

        if (StringUtils.isBlank(role.getId())) {
            throw new BusinessException("user.registr.error", "用户未指定所属角色");
        }

        Role r = roleMapper.findById(role.getId());
        if (r == null) {
            throw new BusinessException("user.registr.error", "用户未指定所属组织或角色");
        }

        UserEncodes.entryptPassword(user);
        user.setIsDel(Constants.IS_DEL_N);
        user.setCreateTime(DateUtil.getSystemDateTime());
        user.setId(GeneratePrimaryKey.getPkValue("T_AUTH_USER"));
        userMapper.insert(user);

        UserRole ur = new UserRole();
        ur.setRoleId(r.getId());
        ur.setUserId(user.getId());
        ur.setId(GeneratePrimaryKey.getPkValue("T_AUTH_USER_ROLE"));
        ur.setIsDel(Constants.IS_DEL_N);
        ur.setCreateTime(DateUtil.getSystemDateTime());
        userRoleMapper.insert(ur);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED) // 不开启事务
    @Override
    public void add(List<User> list1, List<User> list2) {
        try {
            userService.addUser(list1);
        } catch (Exception e) {
            log.error("# addUser fail , error message=[{}]", e.getMessage());
        }
        try {
            saveUser(list2);
        } catch (Exception e) {
            log.error("# saveUser fail , error message=[{}]", e.getMessage());
        }
    }

    @Override
    public void addUser(List<User> list) {
        if (CollectionUtils.isEmpty(list))
            throw new BusinessException("user.add.fail", "用户新增失败");

        for (User user : list) {

            try {

                if (user == null) {
                    throw new BusinessException("user.registr.error", "注册信息错误");
                }

                if (StringUtils.isBlank(user.getLoginName()) || StringUtils.isBlank(user.getPassword())) {
                    throw new BusinessException("user.registr.error", "注册信息错误");
                }

                UserEncodes.entryptPassword(user);
                user.setIsDel(Constants.IS_DEL_N);
                user.setCreateTime(DateUtil.getSystemDateTime());
                userMapper.insert(user);
            } catch (Exception e) {
                log.error("#新增用户失败,error message=[{}]", e.getMessage());
            }
        }
    }

    @Override
    public void saveUser(List<User> list) {
        if (CollectionUtils.isEmpty(list))
            throw new BusinessException("user.add.fail", "用户新增失败");

        for (User user : list) {

            try {

                if (user == null) {
                    throw new BusinessException("user.registr.error", "注册信息错误");
                }

                if (StringUtils.isBlank(user.getLoginName()) || StringUtils.isBlank(user.getPassword())) {
                    throw new BusinessException("user.registr.error", "注册信息错误");
                }

                UserEncodes.entryptPassword(user);
                user.setIsDel(Constants.IS_DEL_N);
                user.setCreateTime(DateUtil.getSystemDateTime());
                userMapper.insert(user);
            } catch (Exception e) {
                log.error("#新增用户失败,error message=[{}]", e.getMessage());
            }
        }
    }

    @Override
    public Page<Role> paginateRole(Page<Role> page) {
        List<Role> roles = roleMapper.paginateRolePage(page);
        page.setResultList(roles);
        return page;
    }

}
