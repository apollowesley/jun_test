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

package net.oschina.durcframework.easymybatis.query.expression.builder.factory;

import net.oschina.durcframework.easymybatis.query.Joint;
import net.oschina.durcframework.easymybatis.query.Operator;
import net.oschina.durcframework.easymybatis.query.expression.Expression;

/**
 * 表达式工厂,负责生成SQL条件表达式
 * @author tanghc
 *
 */
public interface ExpressionFactory {
	
	/**
	 * 
	 * @param joint 表达式之间的连接符
	 * @param columnName 数据库字段名
	 * @param operator 操作符
	 * @param value 值
	 * @return
	 */
	Expression buildExpression(Joint joint, String columnName,Operator operator, Object value);
}
