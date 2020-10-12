package com.team.blogs.common.redis.annotation;

import java.lang.annotation.*;

/**
 * @Author: xiaokai
 * @Description: 锁的参数
 * @Date: 2019/6/26
 * @Version: 1.0
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {

    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}
