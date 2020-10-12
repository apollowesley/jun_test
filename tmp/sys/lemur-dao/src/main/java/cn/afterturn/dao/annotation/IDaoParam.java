package cn.afterturn.dao.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 参数名称,解决接口编译后无法保存名称的问题
 * @author JueYue
 * 2014年4月2日 - 下午4:28:51
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IDaoParam {
	/**
	 * 属性名
	 * 2014年4月2日
	 * @return
	 */
	String value();
}