package com.team.blogs.common.redis.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;


/**
 * @Author: xiaokai
 * @Description: key生成器
 * @Date: 2019/6/26
 * @Version: 1.0
 */
public interface CacheKeyGenerator {

    /**
     * 获取AOP参数，生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}
