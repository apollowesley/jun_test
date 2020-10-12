package com.cnhuashao.rapiddevelopment.core.demo4.config;

import com.cnhuashao.rapiddevelopment.core.demo4.filter.CookieFilter;
import com.cnhuashao.rapiddevelopment.core.demo4.filter.XssFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 类 {@code XssConfig} Xss配置加载类 <br> 用于将Xss拦截器在系统初始时加载至web服务器中.
 * 本软件仅对本次教程负责，版权所有 <a href="http://www.cnhuashao.com">中国，華少</a><br>
 *
 * @author cnHuaShao
 * <a href="mailto:lz2392504@gmail.com
 * <p>
 * ">cnHuaShao</a>
 * 修改备注：
 * @version v1.0.1 2019/11/5 20:12
 */
@Configuration
@Slf4j
public class XssConfig {

    //private Logger log = LoggerFactory.getLogger(XssConfig.class);

    /**
     * cookie拦截器
     * 用于Cookie全局设置，主要设置有效期、https安全访问、httpOnly启用
     * @return
     */
    @Bean
    public FilterRegistrationBean cookieFilterRegistrationBean(){
        log.info("------------ Start Cookie Filter ------------");
        //1、启动拦截器
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        //注册Cookie拦截器
        filterRegistrationBean.setFilter(new CookieFilter());
        //设置bean加载顺序
        filterRegistrationBean.setOrder(1);
        //启用注册
        filterRegistrationBean.setEnabled(true);
        //添加URL为全部，使其拦截器全局拦截
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    /**
     * 配置初始全局拦截器Xss过滤器
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        log.info("------------ Start Xss Filter ------------");
        //1、启动拦截器
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        //注册Xss拦截器
        filterRegistrationBean.setFilter(new XssFilter());
        //设置bean加载顺序
        filterRegistrationBean.setOrder(1);
        //启用注册
        filterRegistrationBean.setEnabled(true);
        //添加URL为全部，使其拦截器全局拦截
        filterRegistrationBean.addUrlPatterns("/*");
        //2、设置初始化方法
        Map<String, String> initParameters = new HashMap<String,String>(2);
        //设置白名单
        initParameters.put("excludes", "/static/*,/img/*,/js/*,/css/*");
        //是否启用深度过滤机制（文本过滤机制），默认fales
        initParameters.put("isIncludeRichText", "true");
        //为此注册设置init参数。调用此方法将替换任何*现有的init参数。
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }

}
