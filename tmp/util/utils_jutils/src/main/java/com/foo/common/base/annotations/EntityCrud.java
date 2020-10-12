package com.foo.common.base.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ TYPE })
@Retention(RUNTIME)
public @interface EntityCrud {

	/**
	 * model名称，大写字母开头的驼峰格式。
	 */
	String modelUpperCamelName();

	/**
	 * jsp模块中文名称，用于页面显示，页面title依赖该字段的值
	 */
	String jspTitleName();
}
