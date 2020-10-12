package com.leo.vhr.config;

import com.leo.vhr.model.Menu;
import com.leo.vhr.model.Role;
import com.leo.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/15
 * @version: 1.0
 * 这个类的作用，主要是根据用户传来的请求，分析这个请求需要的角色
 */
@Component
public class MyFilter implements FilterInvocationSecurityMetadataSource
{
    @Autowired
    MenuService menuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException
    {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        List<Menu> menus = menuService.getAllMenusWithRole();
        for (Menu menu : menus)
        {
            if (antPathMatcher.match(menu.getUrl(), requestUrl))
            {
                List<Role> roles = menu.getRoles();
                String[] str = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++)
                {
                    str[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(str);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");//标记，没匹配上就要求登陆访问
    }

    /**
     * @param null
     * @description：
     * 获取该SecurityMetadataSource对象中保存的针对所有安全对象的权限信息的集合。
     * 该方法的主要目的是被AbstractSecurityInterceptor用于启动时校验每个ConfigAttribute对象。
     * @return
     * @since v1.0.0
     * author Leo
     * date 2020/2/15
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes()
    {
        return null;
    }

    /**
     * @param
     * @description：
     * 这里class表示安全对象的类型，该方法用于告知调用者当前SecurityMetadataSource是否
     * 支持此类安全对象，只有支持的时候，才能对这类安全对象调用getAttributes方法。
     * @return
     * @since v1.0.0
     * author Leo
     * date 2020/2/15
     */
    @Override
    public boolean supports(Class<?> aClass)
    {
        return true;
    }
}
