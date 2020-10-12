package cn.afterturn.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 简单SQL的 存储位置
 * @author JueYue
 * 2014年4月2日 - 下午4:33:05
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IDaoSql {
    
	String value();
}
