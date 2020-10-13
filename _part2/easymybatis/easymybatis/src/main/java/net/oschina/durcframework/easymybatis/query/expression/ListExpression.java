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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import net.oschina.durcframework.easymybatis.SqlConsts;


/**
 * list或数组查询雕件
 * @author tanghc
 * 2011-10-28
 */
public class ListExpression implements ExpressionListable {

	private String column = "";
	private String equal = SqlConsts.IN;
	private Collection<?> value = Collections.EMPTY_LIST;
	private String joint = SqlConsts.AND;
	

	public ListExpression(String column, Collection<?> value) {
		this.column = column;
		this.value = value;
	}
	
	public <T> ListExpression(String column,String equal, Collection<T> value,ValueConvert<T> valueConvert) {
		this(column, value, valueConvert);
		this.equal = equal;
	}
	
	public <T> ListExpression(String column, Collection<T> value,ValueConvert<T> valueConvert) {
		if(valueConvert == null) {
			throw new NullPointerException("参数ValueConvert不能为null");
		}
		this.column = column;
		Collection<Object> newSet = new HashSet<Object>();
		for (T obj : value) {
			newSet.add(valueConvert.convert(obj));
		}
		this.value = newSet;
	}
	
	public ListExpression(String column, Object[] value) {
		this.column = column;
		this.value = Arrays.asList(value);
	}
	
	public ListExpression(String column, String equal, Object[] value) {
		this(column, value);
		this.equal = equal;
	}
	public ListExpression(String joint,String column, String equal, Object[] value) {
		this(column,equal, value);
		this.joint = joint;
	}

	public ListExpression(String column, String equal, Collection<?> value) {
		this(column, value);
		this.equal = equal;
	}

	public ListExpression(String joint, String column, String equal,
			Collection<?> value) {
		this(column, equal, value);
		this.joint = joint;
	}

	@Override
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
	public Collection<?> getValue() {
		return value;
	}

	public void setValue(Collection<?> value) {
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