package cn.coder.jdbc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * 要联合查询的表
 * 
 * @author YYDF
 *
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {

	String table();

	/**
	 * 对应的当前字段
	 * @return
	 */
	String current();

	String target() default "";

}