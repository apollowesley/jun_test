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
public @interface DIResponse {

	/**
	 * @return 请求方式
	 */
	Type value() default Type.JSON;

	/**
	 * 请求方式
	 */
	public enum Type {

		/** 字符串类型 */
		STRING,
		/** JSON类型 */
		JSON,
		/** JSONP类型 */
		JSONP,
		/** XML类型 */
		XML
	}
}
