package com.sise.school.teach.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录认证的注解
 * @author idea
 * @data 2018/9/22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface ValidateLogin {

    /**
     * 表示是否需要登录
     * @return
     */
    boolean needLogin() default false;
}
