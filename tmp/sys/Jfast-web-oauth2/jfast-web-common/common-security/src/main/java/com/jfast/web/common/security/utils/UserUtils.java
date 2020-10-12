package com.jfast.web.common.security.utils;

import com.jfast.web.common.core.utils.ObjectUtils;
import com.jfast.web.common.security.config.SystemSecurityUser;
import com.jfast.web.common.security.service.AuthorityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/28 14:03
 */
public class UserUtils {

    /**
     * 获取用户信息
     * @return
     */
    public static SystemSecurityUser getSystemSecurityUser() {
         Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication.getPrincipal() instanceof SystemSecurityUser) {
            return (SystemSecurityUser) authentication.getPrincipal();
        } else if (authentication instanceof OAuth2Authentication) {
            Map credentials = (Map) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
            Map principalMap = (Map) credentials.get("principal");
            if (ObjectUtils.isNotEmpty(principalMap)) {
                String username = (String) principalMap.get("username");
                String password = (String) principalMap.get("password");
                boolean isSuper = (boolean) principalMap.get("super");
                String encrypt = (String) principalMap.get("encrypt");//authority
                Integer id = (Integer) principalMap.get("id");
                List<Map> menuList = (List<Map>) principalMap.get("menuList");
                List<Map> roleList = (List<Map>) principalMap.get("roleList");
                List<Map> authorities = (List<Map>) principalMap.get("authorities");
                List<GrantedAuthority> authorityList = new ArrayList<>();
                if (ObjectUtils.isNotEmpty(authorities)) {
                    authorities.forEach(item -> {
                        String permission = (String) item.get("authority");
                        authorityList.add(new SimpleGrantedAuthority(permission));
                    });
                }
                SystemSecurityUser systemSecurityUser = new SystemSecurityUser(username,
                        "", encrypt, authorityList);
                systemSecurityUser.setMenuList(menuList);
                systemSecurityUser.setSuper(isSuper);
                systemSecurityUser.setId(id);
                systemSecurityUser.setRoleList(roleList);
                return systemSecurityUser;
            }
        }
        return null;
    }

}
