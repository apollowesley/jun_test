package com.leo.vhr.config;

import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @description: 判断当前用户是否具备资格
 * @author: Leo
 * @createDate: 2020/2/15
 * @version: 1.0
 */
@Component
public class MyDecisionManager implements AccessDecisionManager
{
    /**
     * @param authentication：保存当前用户登录信息,比如你的角色信息
     * @param o：请求对象
     * @param collection：上一个过滤器方法的返回值，比如需要的哪些角色
     * @return
     * @description：
     * @since v1.0.0
     * author Leo
     * date 2020/2/15
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException
    {
        for (ConfigAttribute configAttribute : collection)
        {
            //获取需要的角色
            String needRoles = configAttribute.getAttribute();
            //System.out.println(needRoles);
            if ("ROLE_LOGIN".equals(needRoles))
            {
                //如果当前用户是匿名用户的话
                if (authentication instanceof AnonymousAuthenticationToken)
                {
                    throw new AccessDeniedException("尚未登录，请登录");
                }
                else
                {
                    //说明你是登录用户，那么后面的判断也就不需要了
                    return;
                }
            }
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities)
            {
                if (authority.getAuthority().equals(needRoles))
                {
                    //如果当前用户角色与所需要的角色是相当的，就说明，当前用户具备资格
                    //那么就return
                    return;
                }
            }
        }
        // System.out.println("到权限了！");
        throw new AccessDeniedException("权限不足，请联系管理员！");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute)
    {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass)
    {
        return true;
    }
}
