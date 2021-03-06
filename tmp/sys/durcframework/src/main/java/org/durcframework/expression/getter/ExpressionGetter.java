package org.durcframework.expression.getter;

import java.lang.annotation.Annotation;

import org.durcframework.expression.Expression;

/**
 * 负责申明各种条件
 * @author thc
 * 2011-10-28
 */
public interface ExpressionGetter {

	Expression buildExpression(Annotation annotation, String column,
			Object value);

}
