package com.zb.shiro.filter;

import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.zb.service.user.UserService;

/**
 * 
 * 角色授权认证类 满足任一角色即可放行.
 * 
 * @see http://blog.csdn.net/shadowsick/article/details/39005789 <br/>
 * 
 *      作者: zhoubang 日期：2015年3月26日 下午4:02:46
 */
public class RoleAuthorizationFilter extends AuthorizationFilter {

    @Autowired
    private UserService userService;

    // mappedValue：shiro配置文件中的filterChainDefinitions中自定义角色的name名称
    @Override
    protected boolean isAccessAllowed(ServletRequest request,
            ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);

        // 需要的角色集合
        String[] rolesArray = (String[]) mappedValue;

        // 没有指定角色信息，不需要任何角色即可访问，放行
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }

        /*
         * HttpSession session = ((HttpServletRequest)request).getSession();
         * User current_user = (User) session.getAttribute("userInfo");
         * //判断session是否失效，若失效刷新之 if(current_user == null){ String userName =
         * (String) subject.getPrincipal(); User user =
         * userService.getUserByName(userName); session.setAttribute("userInfo",
         * user); }
         */

        Set<String> roles = CollectionUtils.asSet(rolesArray);
        for (String role : roles) {
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }

}
