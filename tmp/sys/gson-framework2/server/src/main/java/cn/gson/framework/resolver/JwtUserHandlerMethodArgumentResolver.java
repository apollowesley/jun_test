package cn.gson.framework.resolver;

import cn.gson.framework.annotation.JwtUser;
import cn.gson.framework.model.pojo.Account;
import cn.gson.framework.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.framework.resolver</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月25日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@Component
public class JwtUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private AccountService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Account.class) && parameter.hasParameterAnnotation(JwtUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Principal principal = webRequest.getUserPrincipal();
        if (principal == null) {
            return null;
        }
        String[] idAndName = principal.getName().split(":");
        //获取用户信息
        return userService.getById(Long.parseLong(idAndName[0]));
    }
}
