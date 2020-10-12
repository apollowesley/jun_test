package cc.yihy.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName:DataTableParam.java
 * @Description: springmvc 解析dataTable参数  注解
 * @Author: yihy
 * @Since: 2016年10月10日
 * @Version: 1.0
 */
@Target(ElementType.PARAMETER)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented
public @interface DataTableParam {

}

