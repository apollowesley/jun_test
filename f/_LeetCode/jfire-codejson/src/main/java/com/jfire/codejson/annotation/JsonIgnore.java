package com.jfire.codejson.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 使用该注解表明这个方法会被忽略
 * 
 * @author 林斌
 *        
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonIgnore
{

}
