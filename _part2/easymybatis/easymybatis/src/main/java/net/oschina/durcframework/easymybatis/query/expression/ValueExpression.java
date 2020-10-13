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

package net.oschina.durcframework.easymybatis.query.expression;

import net.oschina.durcframework.easymybatis.SqlConsts;


/**
 * 值查询
 * 
 * @author tanghc 2011-10-28
 */
public class ValueExpression implements ExpressionValueable {

	private String column = "";
	private String equal = SqlConsts.EQUAL;
	private Object value;
	private String joint = SqlConsts.AND;


	public ValueExpression(String column, Object value) {
		if(value == null) {
			throw new NullPointerException("ValueExpression(String column, Object value)中value不能为null.");
		}
		this.column = column;
		this.value = value;
	}

	public ValueExpression(String column, String equal, Object value) {
		this(column, value);
		this.equal = equal;
	}

	public ValueExpression(String joint, String column, String equal,
			Object value) {
		this(column, equal, value);
		this.joint = joint;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	@Override
	public String getEqual() {
		return equal;
	}

	public void setEqual(String equal) {
		this.equal = equal;
	}

	@Override
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String getJoint() {
		return joint;
	}

	public void setJoint(String joint) {
		this.joint = joint;
	}

}
