package com.zb.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

import com.zb.common.enums.SignInterceptStatus;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface SignInterceptor {

    /**
     * 标识是否需要进行sign验签
     * 
     */
    SignInterceptStatus signIntercept() default SignInterceptStatus.NO;
}
