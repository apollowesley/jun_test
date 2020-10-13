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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 已废弃,改用@Condition<br>
 * 例子:@Condition(column="username",operator=Operator.like)
 * @author tanghc
 *
 */
@Documented
@Target({  ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Deprecated
public @interface LikeDoubleField {
	/**
	 * 表达连接符,AND,OR,默认AND
	 */
	String joint() default "AND";

	/**
	 * 数据库字段名
	 */
	String column() default "";
}
