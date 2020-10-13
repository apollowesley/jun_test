package com.zb.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zb.bean.sys.SysPermission;
import com.zb.bean.sys.SysRole;
import com.zb.bean.sys.SysUser;
import com.zb.common.enums.UserStatus;
import com.zb.service.sys.PermissionService;
import com.zb.service.sys.RoleService;
import com.zb.service.user.UserService;

@Service("globalAuthorizingRealm")
public class GlobalAuthorizingRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalAuthorizingRealm.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 登陆认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        if (StringUtils.isEmpty(username)) {
            LOGGER.warn("login username is blank.");
            throw new AccountException("Empty usernames are not allowed by this realm.");
        }

        // 根据用户名加载记录
        SysUser user;
        try {
            user = userService.getUserByName(username);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException("cannot query user[" + username + "]", e);
        }

        if (user == null) {
            final String errmsg = "No account found for user [" + username + "]";
            LOGGER.warn(errmsg);
            throw new UnknownAccountException(errmsg);
        }else if (user.getStatus() == UserStatus.LOCK) {//锁定状态 
            throw new LockedAccountException("用户 [" + username + "] 被锁定.");
        }else if(user.getStatus() == UserStatus.DISABLE){
            throw new DisabledAccountException("用户 [" + username + "] 被禁用");
        }
         
        return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.fromRealm(getName()).iterator().next();
        SysUser user = null;
        try {
            // 根据用户名加载记录
            user = userService.getUserByName(userName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if (user == null) {
            return null;
        }

        // 添加session
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute("userInfo", user);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        try {
            // roles
            Set<String> roles = new HashSet<String>();
            // 查找用户角色
            List<SysRole> roleLi = roleService.getUserRoles(userName);
            if (CollectionUtils.isNotEmpty(roleLi)) {
                for (SysRole foo : roleLi) {
                    roles.add(foo.getName());
                }
            }
            info.addRoles(roles);

            // 根据用户名获取权限
            // permissions
            Set<String> permissions = new HashSet<String>();
            List<SysPermission> permissionsLi = permissionService.getUserPermissions(userName);
            if (CollectionUtils.isNotEmpty(permissionsLi)) {
                for (SysPermission foo : permissionsLi) {
                    if (StringUtils.isNotEmpty(foo.getCode())) {
                        permissions.add(foo.getCode());
                    }
                }
            }
            info.addStringPermissions(permissions);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fetch roles and permissions for user[" + userName + "] failed: ", e);
        }

        return info;
    }

    /**
     * 设定密码校验的Hash算法与迭代次数
     */
    /*
     * @PostConstruct public void initCredentialsMatcher() {
     * HashedCredentialsMatcher matcher = new
     * HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME);
     * setCredentialsMatcher(matcher); }
     */

}
