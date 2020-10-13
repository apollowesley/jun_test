/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oschina.durcframework.easymybatis.query.annotation;

import net.oschina.durcframework.easymybatis.query.Joint;
import net.oschina.durcframework.easymybatis.query.Operator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 条件表达式,作用在bean的get方法上
 * 
 * @author tanghc
 *
 */
@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Condition {
	/**
	 * 表达式之间的连接符,AND|OR,默认AND
	 * @return 默认AND
	 */
	Joint joint() default Joint.AND;

	/**
	 * 数据库字段名
	 * 
	 * @return 默认""
	 */
	String column() default "";

	/**
	 * 连接符
	 * 
	 * @return
	 */
	Operator operator() default Operator.nil;
	
	/**
     * 是否忽略，设置true，@Condition将不起作用
     * 
     * @return
     */
	boolean ignore() default false;

	/**
	 * 是否忽略空字符串，设置true，忽略空字符串的字段
	 *
	 * @return 返回true，空字符串字段不生成条件
	 */
	boolean ignoreEmptyString() default false;
}
