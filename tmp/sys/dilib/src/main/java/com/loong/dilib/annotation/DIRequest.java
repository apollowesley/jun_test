package com.loong.dilib.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Api访问注释
 *
 * @author 张成轩
 */
@Documented
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DIRequest {

	/**
	 * @return 访问地址
	 */
	String value();

	/**
	 * @return 请求方式
	 */
	Method method() default Method.GET;

	/**
	 * @return 编码
	 */
	String charset() default "UTF-8";

	/**
	 * 请求方式
	 */
	public enum Method {

		/** POST请求 */
		POST,
		/** GET方式请求 */
		GET
	}
}
