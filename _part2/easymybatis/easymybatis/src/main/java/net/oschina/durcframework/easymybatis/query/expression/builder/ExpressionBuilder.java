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

import net.oschina.durcframework.easymybatis.query.Operator;
import net.oschina.durcframework.easymybatis.query.annotation.Condition;
import net.oschina.durcframework.easymybatis.query.expression.Expression;
import net.oschina.durcframework.easymybatis.query.expression.builder.factory.ExpressionFactory;
import net.oschina.durcframework.easymybatis.query.expression.builder.factory.LikeExpressionFactory;
import net.oschina.durcframework.easymybatis.query.expression.builder.factory.LikeLeftExpressionFactory;
import net.oschina.durcframework.easymybatis.query.expression.builder.factory.LikeRightExpressionFactory;
import net.oschina.durcframework.easymybatis.query.expression.builder.factory.ListExpressionFactory;
import net.oschina.durcframework.easymybatis.query.expression.builder.factory.ValueExpressionFactory;
import net.oschina.durcframework.easymybatis.util.ClassUtil;

import java.util.HashMap;
import java.util.Map;

public class ExpressionBuilder {
	private static Map<Operator, ExpressionFactory> factoryMap = new HashMap<>();
	static {
		factoryMap.put(Operator.eq, new ValueExpressionFactory());
		factoryMap.put(Operator.notEq, new ValueExpressionFactory());
		factoryMap.put(Operator.gt, new ValueExpressionFactory());
		factoryMap.put(Operator.ge, new ValueExpressionFactory());
		factoryMap.put(Operator.lt, new ValueExpressionFactory());
		factoryMap.put(Operator.le, new ValueExpressionFactory());

		factoryMap.put(Operator.like, new LikeExpressionFactory());
		factoryMap.put(Operator.likeLeft, new LikeLeftExpressionFactory());
		factoryMap.put(Operator.likeRight, new LikeRightExpressionFactory());

		factoryMap.put(Operator.in, new ListExpressionFactory());
		factoryMap.put(Operator.notIn, new ListExpressionFactory());
	}

	public static Expression buildExpression(Condition annotation, String columnName, Object value) {
	    if(annotation == null) {
	        throw new NullPointerException("Condition不能为null");
	    }
	    if(annotation.ignore()) {
	        return null;
	    }
	    // 是否忽略空字符串
		if (annotation.ignoreEmptyString() && "".equals(value)) {
			return null;
		}
		Operator operator = annotation.operator();
		if (operator == Operator.nil) {
			if (ClassUtil.isArrayOrCollection(value)) {
				operator = Operator.in;
			} else {
				operator = Operator.eq;
			}
		}
		ExpressionFactory expressionFactory = factoryMap.get(operator);

		String column = annotation.column();
		if ("".equals(column)) {
			column = columnName;
		}

		return expressionFactory.buildExpression(annotation.joint(), column, operator, value);
	}
}
