package com.jfast.web.common.security.service;

import com.jfast.web.common.core.utils.ObjectUtils;
import com.jfast.web.common.security.config.SystemSecurityUser;
import com.jfast.web.common.security.utils.UserUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/29 15:27
 */
@Component
public class AuthorityService {

    public boolean hasAuthority(String authority) {
        SystemSecurityUser systemSecurityUser = UserUtils.getSystemSecurityUser();
        if (ObjectUtils.isNotEmpty(systemSecurityUser)) {
            Collection<? extends GrantedAuthority> authorities = systemSecurityUser.getAuthorities();
            return authorities.stream().map(GrantedAuthority :: getAuthority) // 转换成字符串集合
                    .anyMatch(value -> value.equals(authority)); // 判断集合中是否包含该字符串
        }
        return false;
    }

    public boolean hasAuthority(List<String> authorityList) {
        for (String item : authorityList) {
            if (hasAuthority(item)) {
                return true;
            }
        }
        return false;
    }
}
