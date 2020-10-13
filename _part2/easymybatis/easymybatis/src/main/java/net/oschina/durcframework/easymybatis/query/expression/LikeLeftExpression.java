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


/**
 * Like条件查询,左边模糊匹配,即'%aaa'
 * @author tanghc
 *
 */
public class LikeLeftExpression extends AbstractLikeExpression {


	/**
	 * Like条件查询,左边模糊匹配,即'%aaa'
	 * @param column 数据库字段名
	 * @param value 查询的值
	 */
	public LikeLeftExpression(String column, Object value) {
		super(column, value);
	}

	/**
	 * Like条件查询,左边模糊匹配,即'%aaa'
	 * @param joint
	 * @param column 数据库字段名
	 * @param value 查询的值
	 */
	public LikeLeftExpression(String joint, String column, Object value) {
		super(joint, column, value);
	}
	
	@Override
	public Object getValue() {
		return "%" + super.getValue();
	}
	
	
}
