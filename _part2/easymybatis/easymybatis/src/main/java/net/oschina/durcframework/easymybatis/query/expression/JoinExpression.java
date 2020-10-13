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

public class JoinExpression implements ExpressionJoinable {

	private String joinSql;

	/**
	 * 自定义连接语句
	 * 
	 * @param joinSql
	 *            inner join table1 t1 on t.xx = t1.xx
	 */
	public JoinExpression(String joinSql) {
		this.joinSql = joinSql;
	}

	@Override
	public String getJoinSql() {
		return joinSql;
	}

}
