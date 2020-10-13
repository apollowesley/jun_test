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

package net.oschina.durcframework.easymybatis.query.projection;

/**
 * 聚合列对象
 * @author tanghc
 */
public class Projection {
	// 表达式,如:count(*),max(age),ave(age)等
	private String expr;
	// 别名
	private String alias;

	public Projection() {
	}

	public Projection(String expr) {
		this(expr, null);
	}

	public Projection(String expr, String alias) {
		this.expr = expr;
		this.alias = alias;
	}
	
	// 返回完整列名称,如count(*) AS cnt
	public String getSql() {
		return alias == null ? expr : expr + " AS " + alias;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Override
	public String toString() {
		return this.getSql();
	}

}
