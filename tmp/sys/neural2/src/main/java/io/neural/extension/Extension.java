package io.neural.extension;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NPI有多个实现时，可以根据条件进行过滤、排序后再返回。
 *  
 * @author lry
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Extension {
	
	/** 自定义实现类ID **/
    String value() default "";
    
    /** order号越小，在返回的list<Instance>中的位置越靠前 */
	int order() default 20;

	/** NPI的category，获取NPI列表时，根据category进行匹配，当category中存在待过滤的search-category时，匹配成功 */
	String[] category() default "";
    
}
