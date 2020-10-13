package com.bodsite.common.lock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bodsite.common.constant.LockConstant;
import com.bodsite.common.constant.LockConstant.LOCK_TYPE;

/**
 * 分布式锁注解
* @author bod
* @date 2016年12月24日 下午2:52:15 
*
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {
	
	LOCK_TYPE lockType() default LOCK_TYPE.ZOOKEEPER_LOCK;
		
	String key() default "";//锁key
	
	long expiration() default LockConstant.DEFALUT_OVER_TIME;//请求时间  ms
	
	int count() default LockConstant.RETRY_GET_LOCK_COUNT;//重试次数
	
	int expire() default LockConstant.DEFALUT_LOCK_OVER_TIME;//锁超时时间 s	
	
}
