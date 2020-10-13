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

package net.oschina.durcframework.easymybatis.query.expression.builder;

import java.lang.annotation.Annotation;

import net.oschina.durcframework.easymybatis.query.annotation.LikeLeftField;
import net.oschina.durcframework.easymybatis.query.expression.Expression;
import net.oschina.durcframework.easymybatis.query.expression.LikeLeftExpression;

/**
 * 构建左模糊查询条件.已废弃,改用LikeLeftExpressionFactory
 * 
 * @author tanghc
 */
@Deprecated
public class LikeLeftExpressionGetter implements ExpressionGetter {

	@Override
	public Expression buildExpression(Annotation annotation, String columnName, Object value) {
		if (value instanceof String) {
			String strVal = (String) value;
			if ("".equals(strVal.trim())) {
				return null;
			}
		}
		LikeLeftField valueField = (LikeLeftField) annotation;
		String column = valueField.column();
		if(column == null || "".equals(column)) {
			column = columnName;
		}
		return new LikeLeftExpression(valueField.joint(), column, value);
	}

}
