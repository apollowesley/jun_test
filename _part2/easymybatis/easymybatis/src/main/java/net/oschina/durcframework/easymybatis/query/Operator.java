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

package net.oschina.durcframework.easymybatis.query;

/**
 * 操作符
<table border="1">
<thead>
<tr>
<th>查询方式</th>
<th>说明</th>
</tr>
</thead>
<tbody>
<tr>
<td>eq</td>
<td>等于=</td>
</tr>
<tr>
<td>gt</td>
<td>大于&gt;</td>
</tr>
<tr>
<td>lt</td>
<td>小于&lt;</td>
</tr>
<tr>
<td>ge</td>
<td>大于等于&gt;=</td>
</tr>
<tr>
<td>le</td>
<td>小于等于&lt;=</td>
</tr>
<tr>
<td>notEq</td>
<td>不等于&lt;&gt;</td>
</tr>
<tr>
<td>like</td>
<td>模糊查询</td>
</tr>
<tr>
<td>in</td>
<td>in()查询</td>
</tr>
<tr>
<td>notIn</td>
<td>not in()查询</td>
</tr>
</tbody>
</table>
 * @author tanghc
 *
 */
public enum Operator {
	/** 等于= */
	eq("=")
	/** 大于&gt; */
	,gt(">")
	/** 小于&lt; */
	,lt("<")
	/** 大于等于&gt;= */
	,ge(">=")
	/** 小于等于&lt;= */
	,le("<=")
	/** 不等于&lt;&gt; */
	,notEq("<>")
	/** in()查询  */
	,in("IN")
	/** not in()查询 */
	,notIn("NOT IN")
	/** 模糊查询,两边模糊查询,like '%xx%' */
	,like("LIKE")
	/** 左模糊查询,like '%xx' */
	,likeLeft("LIKE")
	/** 左模糊查询,like 'xx%' */
	,likeRight("LIKE")
	
	,nil("")
	;
	
	private String operator;

	Operator(String operator) {
		this.operator = operator;
	}

	public String getOperator() {
		return operator;
	}

}
