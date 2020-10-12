package cn.coder.jdbc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标示主键
 * 
 * @author YYDF
 *
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {

	/**
	 * 是否自增长
	 * 
	 * @return true为自增id
	 */
	boolean value() default false;
}