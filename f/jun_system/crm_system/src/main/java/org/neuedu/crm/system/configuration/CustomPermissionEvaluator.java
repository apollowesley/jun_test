package org.neuedu.crm.system.configuration;

import org.neuedu.crm.system.entity.SysUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

/**
 * @description  要使用hasPermission() 表达式，必须在应用程序上下文中显式配置PermissionEvaluator 。
 * hasPermission(Object target, Object permission)
 *  如果用户有权访问为给定权限提供的目标，则返回true 。 例如，hasPermission(domainObject, 'read')
 *  如果用户有权访问为给定权限提供的目标，则返回true 。 例如，hasPermission(1, 'com.example.domain.Message', 'read')
 * @auther: CDHONG.IT
 * @date: 2019/12/27-14:07
 **/
@Configuration
public class CustomPermissionEvaluator implements PermissionEvaluator {

    /**
     * 用于已经加载访问控制的域对象的情况,如果当前用户具有该对象的给定权限，表达式将返回true
     * @param authentication 登录成功后，得到的认证对象
     * @param targetDomainObject 访问url
     * @param permission 权限(增，删，改，查)
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        // 获得loadUserByUsername()方法的结果
        SysUser user = (SysUser)authentication.getPrincipal();
        // 获得loadUserByUsername()中注入的角色
        List<GrantedAuthority> roles = user.getRoles();
        // 遍历用户所有角色
        for(GrantedAuthority authority : roles) {
            String roleName = authority.getAuthority();
        }
        return false;
    }

    //用于未加载对象但标识符已知的情况。 还需要域对象的抽象“类型”说明符，以允许加载正确的ACL权限
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
