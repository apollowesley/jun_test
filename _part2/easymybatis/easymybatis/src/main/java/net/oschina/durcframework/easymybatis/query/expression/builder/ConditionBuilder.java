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

import net.oschina.durcframework.easymybatis.ext.code.util.FieldUtil;
import net.oschina.durcframework.easymybatis.query.annotation.Condition;
import net.oschina.durcframework.easymybatis.query.expression.Expression;
import net.oschina.durcframework.easymybatis.query.expression.ListExpression;
import net.oschina.durcframework.easymybatis.query.expression.ValueExpression;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 条件生成
 * 
 * @author tanghc
 *
 */
public class ConditionBuilder {
	private static final String PREFIX_GET = "get";

	private static ConditionBuilder underlineFieldBuilder = new ConditionBuilder(true);
	private static ConditionBuilder camelFieldBuilder = new ConditionBuilder(false);

	private boolean camel2underline = Boolean.TRUE;

	public ConditionBuilder(boolean camel2underline) {
		super();
		this.camel2underline = camel2underline;
	}

	public static ConditionBuilder getUnderlineFieldBuilder() {
		return underlineFieldBuilder;
	}

	public static ConditionBuilder getCamelFieldBuilder() {
		return camelFieldBuilder;
	}

	/**
	 * 获取条件表达式
	 * 
	 * @param obj
	 * @return
	 */
	public List<Expression> buildExpressions(Object obj) {
		if (obj == null) {
			return null;
		}
		List<Expression> expList = new ArrayList<Expression>();
		Method[] methods = obj.getClass().getDeclaredMethods();
		try {
			for (Method method : methods) {
				if (couldBuildExpression(method)) {
					Object value = method.invoke(obj);
					if (value == null) {
						continue;
					}
					Expression expression = buildExpression(method, value);
					if (expression != null) {
						expList.add(expression);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return expList;
	}

	private Expression buildExpression(Method method, Object value) {
		Condition annotation = method.getAnnotation(Condition.class);
		String columnName = this.buildColumnName(method);
		Expression expression = null;

		if (annotation == null) {
			if (value.getClass().isArray()) {
				expression = new ListExpression(columnName, (Object[]) value);
			} else if (value instanceof Collection) {
				expression = new ListExpression(columnName, (Collection<?>) value);
			} else {
				expression = new ValueExpression(columnName, value);
			}
		} else {
			expression = ExpressionBuilder.buildExpression(annotation, columnName, value);
		}

		return expression;
	}

	// 返回数据库字段名
	private String buildColumnName(Method method) {
		String getMethodName = method.getName();
		String columnName = getMethodName.substring(3);
		columnName = FieldUtil.lowerFirstLetter(columnName);
		if (camel2underline) {
			return FieldUtil.camelToUnderline(columnName);
		} else {
			return columnName;
		}
	}

	// 能否构建表达式
	private static boolean couldBuildExpression(Method method) {
		return method.getReturnType() != Void.TYPE && method.getName().startsWith(PREFIX_GET);
	}

}
