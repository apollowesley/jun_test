package com.jfast.web.auth.config;

import com.jfast.web.common.core.config.BusinessException;
import com.jfast.web.common.core.utils.Md5Utils;
import com.jfast.web.common.core.utils.ObjectUtils;
import com.jfast.web.common.core.utils.ResultCode;
import com.jfast.web.common.security.config.SystemSecurityUser;
import com.jfast.web.common.security.exception.WebOAuth2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 自定义密码验证
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/9/28 15:57
 */
@Component
public class SystemAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService systemUserDetailService;

    /**
     * 自定义密码验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getName();
        UserDetails user = systemUserDetailService.loadUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new WebOAuth2Exception(new ResultCode(ResultCode.FAIL, "用户不存在"));
        }
        String password = Md5Utils.getMd5((String) authentication.getCredentials(), ((SystemSecurityUser)user).getEncrypt());
        if (!password.equals(user.getPassword())) {
            throw new WebOAuth2Exception(new ResultCode(ResultCode.FAIL, "用户名或密码错误"));
        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
