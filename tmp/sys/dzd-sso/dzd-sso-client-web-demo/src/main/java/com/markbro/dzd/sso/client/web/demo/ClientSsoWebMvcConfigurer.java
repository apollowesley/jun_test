package com.markbro.dzd.sso.client.web.demo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.markbro.dzd.sso.core.SsoConf;
import com.markbro.dzd.sso.core.interceptor.SsoRequestInterceptor;
import com.markbro.dzd.sso.core.interceptor.SsoRequestInterceptorClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ClientSsoWebMvcConfigurer extends WebMvcConfigurationSupport {
    protected   final Logger log= LoggerFactory.getLogger(getClass());
    private String filterType= SsoConf.SSO_FILTER_TYPE;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
    @ConditionalOnProperty(name = "sso.filterType", havingValue = "1")
    @Bean
    public  MySsoRequestInterceptorClient getMySsoRequestInterceptorClient(){
        log.info(">>>>>>>>>>>注册请求拦截器 MySsoRequestInterceptorClient！");
        return new MySsoRequestInterceptorClient();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //1表示使用拦截器过滤请求
        //与之对应的0则表示用过滤器拦截请求。配置在SsoClientConfig.java
        if("1".equals(filterType)){
            registry.addInterceptor(getMySsoRequestInterceptorClient()).excludePathPatterns("/sso/**").addPathPatterns("/**");
            super.addInterceptors(registry);
        }
    }


    class MySsoRequestInterceptorClient extends SsoRequestInterceptorClient{
        //
        //这种情况的interceptor中能注入其他内容，比如其他service，如果用new 的拦截器，不能注入其它东西。
    }


    //使用阿里 FastJson 作为JSON MessageConverter
   /* @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,//保留空的字段
                SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
                SerializerFeature.WriteNullNumberAsZero,//Number null -> 0
                SerializerFeature.PrettyFormat);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));

        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastMediaTypes.add(new MediaType("text/html;charset=UTF-8"));
        converter.setSupportedMediaTypes(fastMediaTypes);

        converters.add(converter);
    }*/


    //注册统一异常处理
   /* @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new ClientDefaultExceptionHandler());
    }*/
}
