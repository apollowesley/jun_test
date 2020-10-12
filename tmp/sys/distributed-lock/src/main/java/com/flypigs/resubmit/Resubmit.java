package com.flypigs.resubmit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Resubmit {
    //key前缀，以区分其它系统或业务
    String lockPrefix() default "";

    //锁对应的Key:如果为空，不加锁
    String lockKey();

    //过期时间
    int expireSeconds() default 5;

    //resolverName
    Class<? extends ResubmitResolver> resolver() default ResubmitResolver.class;
}
