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
import net.oschina.durcframework.easymybatis.query.expression.ValueExpression;

public class LikeExpressionFactory implements ExpressionFactory {

	@Override
	public Expression buildExpression(Joint joint, String columnName, Operator operator, Object value) {
		return new ValueExpression(joint.getJoint(), columnName, operator.getOperator(), this.getValue(value));
	}

	protected String getValue(Object value) {
		return "%" + value + "%";
	}

}
