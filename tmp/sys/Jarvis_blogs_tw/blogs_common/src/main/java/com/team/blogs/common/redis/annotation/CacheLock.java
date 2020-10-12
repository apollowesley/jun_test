package com.team.blogs.common.redis.annotation;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaokai
 * @Description: 创建缓存锁
 * @Date: 2019/6/26
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheLock {

    /**
     * redis 锁key的前缀
     *
     * @return redis锁key的前缀
     */
    String prefix() default "";

    /**
     * 过期秒数, 默认为5秒
     *
     * @return 轮询锁的时间
     */
    int expire() default 5;

    /**
     * 超时时间单位
     *
     * @return 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * <p>Key的分隔符（默认 :）</p>
     * <p>生成的Key：N:SO1008:500</p>
     *
     * @return
     */
    String delimiter() default ":";


//    prefix： 缓存中 key 的前缀
//    expire： 过期时间，此处默认为 5 秒
//    timeUnit： 超时单位，此处默认为秒
//    delimiter： key 的分隔符，将不同参数值分割开来

}
