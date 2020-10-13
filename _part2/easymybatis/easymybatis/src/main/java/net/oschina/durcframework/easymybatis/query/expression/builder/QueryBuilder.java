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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.oschina.durcframework.easymybatis.ext.code.util.FieldUtil;
import net.oschina.durcframework.easymybatis.query.annotation.LikeDoubleField;
import net.oschina.durcframework.easymybatis.query.annotation.LikeLeftField;
import net.oschina.durcframework.easymybatis.query.annotation.LikeRightField;
import net.oschina.durcframework.easymybatis.query.annotation.ListField;
import net.oschina.durcframework.easymybatis.query.annotation.ValueField;
import net.oschina.durcframework.easymybatis.query.expression.Expression;
import net.oschina.durcframework.easymybatis.query.expression.ListExpression;
import net.oschina.durcframework.easymybatis.query.expression.ValueExpression;

/**
 * 从bean中获取Expression,已废弃,改用ConditionBuilder
 */
@Deprecated
public class QueryBuilder {

	private static final String PREFIX_GET = "get";

	// init
	static {
		AnnoExprStore.addExpressionGetter(ListField.class, new ListExpressionGetter());
		AnnoExprStore.addExpressionGetter(ValueField.class, new ValueExpressionGetter());
		AnnoExprStore.addExpressionGetter(LikeLeftField.class, new LikeLeftExpressionGetter());
		AnnoExprStore.addExpressionGetter(LikeRightField.class, new LikeRightExpressionGetter());
		AnnoExprStore.addExpressionGetter(LikeDoubleField.class, new LikeDoubleExpressionGetter());
	}

	private static QueryBuilder underlineFieldBuilder = new QueryBuilder(true);
	private static QueryBuilder camelFieldBuilder = new QueryBuilder(false);

	private static Class<?>[] collectionClasses = { Iterable.class, Collection.class, List.class, ArrayList.class,
			LinkedList.class, Set.class, HashSet.class, TreeSet.class, LinkedHashSet.class };

	private boolean camel2underline = Boolean.TRUE;

	public QueryBuilder() {
		super();
	}

	public QueryBuilder(boolean camel2underline) {
		super();
		this.camel2underline = camel2underline;
	}

	public static QueryBuilder getUnderlineFieldBuilder() {
		return underlineFieldBuilder;
	}

	public static QueryBuilder getCamelFieldBuilder() {
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
					Object value = method.invoke(obj, new Object[] {});
					if (value == null) {
						continue;
					}
					List<Expression> expressionList = buildExpression(method, value);
					expList.addAll(expressionList);
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return expList;
	}

	private List<Expression> buildExpression(Method method, Object value) {
		Annotation[] annotations = method.getAnnotations();
		List<Expression> expList = new ArrayList<Expression>();
		String columnName = this.buildColumnName(method);

		if (annotations.length == 0) {
			Class<?> returnType = method.getReturnType();
			if (isCollectionOrArray(returnType)) {
				ListExpression expression;
				if(returnType.isArray()) {
					expression = new ListExpression(columnName, (Object[])value);
				} else {
					expression = new ListExpression(columnName, (Collection<?>)value);
				}
				expList.add(expression);
			} else {
				ValueExpression expression = new ValueExpression(columnName, value);
				expList.add(expression);
			}
		} else {
			for (Annotation annotation : annotations) {
				ExpressionGetter expressionGetter = AnnoExprStore.get(annotation);
				if (expressionGetter != null) {
					Expression expression = expressionGetter.buildExpression(annotation, columnName, value);
					expList.add(expression);
				}
			}
		}

		return expList;
	}

	private boolean isCollectionOrArray(Class<?> returnType) {
		if (returnType.isArray()) {
			return true;
		} else {
			for (Class<?> clazz : collectionClasses) {
				if (clazz == returnType) {
					return true;
				}
			}
		}
		return false;
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

	static class AnnoExprStore {

		private static Map<String, ExpressionGetter> map = new HashMap<String, ExpressionGetter>();

		/**
		 * 通过注解获取
		 * 
		 * @param annotation
		 * @return
		 */
		public static ExpressionGetter get(Annotation annotation) {
			return map.get(annotation.annotationType().getSimpleName());
		}

		/**
		 * 保存
		 * 
		 * @param clazz
		 *            注解的class
		 * @param getter
		 *            ExpressionGetter
		 */
		public static void addExpressionGetter(Class<?> clazz, ExpressionGetter getter) {
			map.put(clazz.getSimpleName(), getter);
		}
	}

}
