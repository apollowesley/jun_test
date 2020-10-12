package org.durcframework.expression.getter.impl;

import java.lang.annotation.Annotation;
import java.util.List;

import org.durcframework.expression.Expression;
import org.durcframework.expression.annotation.ListField;
import org.durcframework.expression.getter.ExpressionGetter;
import org.durcframework.expression.subexpression.ListExpression;
import org.springframework.util.StringUtils;

//构建list查询条件
public class ListExpressionGetter implements ExpressionGetter {

		@Override
		public Expression buildExpression(Annotation annotation, String column,
				Object value) {
			if (value == null) {
				return null;
			}
			ListField listValueField = (ListField) annotation;
			String joint = listValueField.joint();
			String equal = listValueField.equal();
			String field = listValueField.column();
			if (StringUtils.hasText(field)) {
				column = field;
			}
			if (value.getClass().isArray()) {
				return new ListExpression(joint, column, equal,
						(Object[]) value);
			}
			if (value instanceof List) {
				return new ListExpression(joint, column, equal, (List<?>) value);
			}
			return null;
		}
	}