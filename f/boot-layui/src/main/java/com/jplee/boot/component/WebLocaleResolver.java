package com.jplee.boot.component;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 区域解析器/国际化
 * @author jplee
 * @create 2019-01-24 15:30
 */
public class WebLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String language = httpServletRequest.getParameter("l");
        Locale locale = Locale.getDefault();
        String[] split = null;
        if (!StringUtils.isEmpty(language)) {
            split = language.split("_");
            locale = new Locale(split[0], split[1]);
        }else {
            //没传值时使用浏览器默认的国际化值
            String acceptLanguage = httpServletRequest.getHeader("Accept-Language");
            language = acceptLanguage.substring(0,acceptLanguage.indexOf(","));
            split = language.split("-");
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
