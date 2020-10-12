package com.jfast.web.auth.service;


import com.jfast.web.auth.fegin.SystemAdminFeign;
import com.jfast.web.common.core.utils.ObjectUtils;
import com.jfast.web.common.core.utils.ResultCode;
import com.jfast.web.common.security.config.SystemSecurityUser;
import com.jfast.web.common.security.exception.WebOAuth2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/9/15 14:50
 */
@Service
public class SystemUserDetailService implements UserDetailsService {

    @Autowired
    private SystemAdminFeign systemAdminFeign;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Map userMap = systemAdminFeign.findByName(userName);
        if (ObjectUtils.isEmpty(userMap)) {
            throw new WebOAuth2Exception(new ResultCode(ResultCode.FAIL, "用户不存在"));
        }
        List<Map> permissionList = (List<Map>) userMap.get("permissionList");
        SystemSecurityUser systemSecurityUser = new SystemSecurityUser(userName, (String)userMap.get("password"),
                (String)userMap.get("encrypt"),
                getGrantedAuthorities(permissionList));
        systemSecurityUser.setId((Integer) userMap.get("id"));
        systemSecurityUser.setMenuList(permissionList);
        systemSecurityUser.setSuper((boolean) userMap.get("super_flag"));
        return systemSecurityUser;
    }


    /**
     * 加载用户权限
     * @param permissionList
     * @return
     */
    public final List<GrantedAuthority> getGrantedAuthorities(final List<Map> permissionList) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(permissionList)) {
            permissionList.forEach(permissionMap -> {
                String permission = (String) permissionMap.get("permissions");
                if (ObjectUtils.isNotEmpty(permission)) {
                    authorities.add(new SimpleGrantedAuthority(permission));
                }
            });
        }
        return authorities;
    }
}
