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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.oschina.durcframework.easymybatis.SqlConsts;
import net.oschina.durcframework.easymybatis.query.expression.Expression;
import net.oschina.durcframework.easymybatis.query.expression.ExpressionJoinable;
import net.oschina.durcframework.easymybatis.query.expression.ExpressionListable;
import net.oschina.durcframework.easymybatis.query.expression.ExpressionSqlable;
import net.oschina.durcframework.easymybatis.query.expression.ExpressionValueable;
import net.oschina.durcframework.easymybatis.query.expression.Expressional;
import net.oschina.durcframework.easymybatis.query.expression.JoinExpression;
import net.oschina.durcframework.easymybatis.query.expression.ListExpression;
import net.oschina.durcframework.easymybatis.query.expression.SqlExpression;
import net.oschina.durcframework.easymybatis.query.expression.ValueConvert;
import net.oschina.durcframework.easymybatis.query.expression.ValueExpression;
import net.oschina.durcframework.easymybatis.query.expression.builder.ConditionBuilder;
import net.oschina.durcframework.easymybatis.query.param.BaseParam;
import net.oschina.durcframework.easymybatis.query.param.SchPageableParam;
import net.oschina.durcframework.easymybatis.query.param.SchSortableParam;

/**
 * 查询类
 * <pre>
查询姓名为张三的用户
@GetMapping("sch")
public List<TUser> sch(String username) {
	Query query = new Query();
	query.eq("username", username); 
	List<TUser> list = dao.find(query);
	return list;
}

查询姓名为张三并且拥有的钱大于100块
@GetMapping("sch2")
public List<TUser> sch2(String username) {
	Query query = new Query();
	query.eq("username", username).gt("money", 100);
	List<TUser> list = dao.find(query);
	return list;
}

查询姓名为张三并带分页
@GetMapping("sch3")
public List<TUser> sch3(String username,PageParam param) {
	Query query = param.toQuery();
	query.eq("username", username);
	List<TUser> list = dao.find(query);
	return list;
}

// 根据添加时间倒序
Query query = new Query();
query.addSort("create_time",Sort.DESC);
dao.find(query);
 * </pre>
 * @author tanghc
 *
 */
public class Query implements Queryable {

	private static final String REG_SQL_INJECT = "([';*--|])+";
	
	private int start;
	private int limit = 10;
	// 排序信息
	private Set<String> orderInfo;
	// 是否查询全部
	private boolean queryAll;

	private Map<String, Object> paramMap;

	private List<ExpressionValueable> valueExpressions;
	private List<ExpressionJoinable> joinExpressions;
	private List<ExpressionListable> listExpressions;
	private List<ExpressionSqlable> sqlExpressions;

	private List<String> columns = Collections.emptyList();
	private List<String> otherTableColumns;


	/**
	 * 将bean中的字段转换成条件,字段名会统一转换成下划线形式.已废弃，改用Query.buind(bean)
	 * <pre><code>
	 * User user = new User();
	 * user.setUserName("jim");
	 * Query query = Query.buildFromBean(user);
	 * </code>
	 * 这样会组装成一个条件:where user_name='jim'
	 * 更多功能可查看开发文档.
	 * </pre>
	 * @param bean
	 * @return
	 */
	@Deprecated
	public static Query buildFromBean(Object bean) {
		Query query = new Query();
		
		bindExpressionsFromBean(bean, query);
		
		return query;
	}
	
	private static void bindExpressionsFromBean(Object bean,Query query) {
		List<Expression> expresList = ConditionBuilder.getUnderlineFieldBuilder().buildExpressions(bean);

		for (Expression expression : expresList) {
			query.addExpression(expression);
		}
	}
	
	/**
	 * 将bean中的字段转换成条件,不会将字段名转换成下划线形式.
	 * <pre><code>
	 * User user = new User();
	 * user.setUserName("jim");
	 * Query query = Query.buildFromBeanByProperty(user);
	 * </code>
	 * 这样会组装成一个条件:where userName='jim'
	 * 更多功能可查看开发文档.
	 * </pre>
	 * @param bean
	 * @return
	 */
	public static Query buildFromBeanByProperty(Object bean) {
		Query query = new Query();
		List<Expression> expresList = ConditionBuilder.getCamelFieldBuilder().buildExpressions(bean);

		for (Expression expression : expresList) {
			query.addExpression(expression);
		}
		
		return query;
	
	}
	
	public static Query build() {
		return new Query();
	}

	/**
	 * 添加其它表的字段 query.addOtherColumn("t2.username as name");
	 * 
	 * @param column
	 * @return
	 */
	public Query addOtherColumn(String column) {
		if (otherTableColumns == null) {
			otherTableColumns = new ArrayList<>();
		}
		otherTableColumns.add(column);
		return this;
	}

	/**
	 * 批量添加其它字段.
	 * 
	 * query.addOtherColumns(
	 * "t2.username"
	 * ,"t2.userage as age"
	 * )
	 * 
	 * @param columns
	 * @return
	 */
	public Query addOtherColumns(String... columns) {
		for (String column : columns) {
			this.addOtherColumn(column);
		}
		return this;
	}

	@Override
	public List<String> getOtherTableColumns() {
		return otherTableColumns;
	}
	
	// ------ 设置分页信息 ------
	
	/**
     * 设置分页信息
     * 
     * @param pageIndex
     *            当前第几页,从1开始
     * @param pageSize
     *            每页结果集大小
     * @return 返回自身对象
     */
	public Query page(int pageIndex, int pageSize) {
	    this.setPage(pageIndex, pageSize);
	    return this;
	}
	
	/**
     * 设置分页信息,针对不规则分页。对应mysql分页语句：limit {start},{offset}
     * 
     * @param start
     *            记录起始位置
     * @param offset
     *            偏移量
     * @return 返回自身对象
     */
	public Query limit(int start, int offset) {
	    if(start < 0) {
	        throw new IllegalArgumentException("public Query limit(int start, int offset)方法start必须大于等于0");
	    }
	    if(offset < 1) {
	        throw new IllegalArgumentException("public Query limit(int start, int offset)方法offset必须大于等于1");
        }
	    this.start = start;
	    this.limit = offset;
	    return this;
	}

	/**
	 * 设置分页信息。已废弃，改用query.limit(start,offset)
	 * 
	 * @param start
	 *            记录索引
	 * @return 返回自身对象
	 */
	@Deprecated
	public Query setStart(int start) {
		this.start = start;
		return this;
	}

	/**
	 * 设置分页信息。已废弃，改用query.limit(start,offset)
	 * 
	 * @param limit
	 *            记录条数
	 * @return 返回自身对象
	 */
	@Deprecated
	public Query setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	/**
	 * 设置分页信息。已废弃，改用query.page(pageIndex,pageSize);
	 * 
	 * @param pageIndex
	 *            当前第几页,从1开始
	 * @param pageSize
	 *            每页结果集大小
	 * @return 返回自身对象
	 */
	@Deprecated
	public Query setPage(int pageIndex, int pageSize) {
		if (pageIndex < 1) {
			throw new IllegalArgumentException("pageIndex必须大于等于1");
		}
		if (pageSize < 1) {
            throw new IllegalArgumentException("pageSize必须大于等于1");
        }
		int start = (int) ((pageIndex - 1) * pageSize);
		int offset = pageSize;
		return this.limit(start, offset);
	}

	@Override
	public int getStart() {
		return this.start;
	}

	@Override
	public int getLimit() {
		return this.limit;
	}

	/**
	 * 
	 * 同getStart()
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return this.getStart();
	}

	/**
	 * 同getLimit()
	 * 
	 * @return
	 */
	public int getPageSize() {
		return this.getLimit();
	}

	// ------ 设置分页信息 end ------

	/**
	 * 构建查询全部的表达式对象
	 * 
	 * @return
	 */
	public static Query buildQueryAll() {
		Query query = new Query();
		query.queryAll = true;
		return query;
	}

	/**
	 * 添加注解查询条件
	 * 
	 * @param searchEntity
	 * @return
	 */
	public Query addAnnotionExpression(Object searchEntity) {
		bindExpressionsFromBean(searchEntity, this);
		return this;
	}

	/**
	 * 添加分页信息
	 */
	public Query addPaginationInfo(SchPageableParam searchEntity) {
		int start = searchEntity.getStart();
		int limit = searchEntity.getLimit();
		return this.limit(start, limit);
	}
	
	public Query addSortInfo(SchSortableParam searchEntity) {
		this.addSort(searchEntity.getDBSortname(), searchEntity.getSortorder());
		return this;
	}

	/**
	 * 构建查询条件.
	 * 
	 * @param searchEntity
	 * @return
	 */
	public static Query build(Object searchEntity) {
	    if(searchEntity instanceof BaseParam) {
	        return ((BaseParam)searchEntity).toQuery();
	    }else {
	        return buildFromBean(searchEntity);
	    }
	}

	@Override
	public Expressional addExpression(Expression expression) {
		if (expression instanceof ExpressionValueable) {
			if(valueExpressions == null) {valueExpressions = new ArrayList<>();}
			valueExpressions.add((ExpressionValueable) expression);
		} else if (expression instanceof ExpressionListable) {
			if(listExpressions == null) {listExpressions = new ArrayList<>();}
			listExpressions.add((ExpressionListable) expression);
		} else if (expression instanceof ExpressionJoinable) {
			if(joinExpressions == null) {joinExpressions = new ArrayList<>();}
			joinExpressions.add((ExpressionJoinable) expression);
		} else if (expression instanceof ExpressionSqlable) {
			if(sqlExpressions == null) {sqlExpressions = new ArrayList<>();}
			sqlExpressions.add((ExpressionSqlable) expression);
		}

		return this;
	}

	public void addAll(List<Expression> expressions) {
		if (expressions != null) {
			for (Expression expression : expressions) {
				this.addExpression(expression);
			}
		}
	}

	public Query addParam(String name, Object value) {
		if (this.paramMap == null) {
			this.paramMap = new HashMap<>();
		}
		this.paramMap.put(name, value);
		return this;
	}

	@Override
	public Map<String, Object> getParam() {
		return this.paramMap;
	}

	@Override
	public boolean getIsQueryAll() {
		return queryAll;
	}

	public void setQueryAll(boolean queryAll) {
		this.queryAll = queryAll;
	}

	@Override
	public List<ExpressionValueable> getValueExpressions() {
		return this.valueExpressions;
	}

	@Override
	public List<ExpressionJoinable> getJoinExpressions() {
		return this.joinExpressions;
	}

	@Override
	public List<ExpressionListable> getListExpressions() {
		return this.listExpressions;
	}

	@Override
	public List<ExpressionSqlable> getSqlExpressions() {
		return this.sqlExpressions;
	}
	
	/**
     * 字段排序
     * @param sortname 数据库字段名
     * @param sort 排序类型
     * @return
     */
	public Query orderby(String sortname, Sort sort) {
	    return this.addSort(sortname, sort);
	}

	/**
	 * 添加ASC排序字段,
	 * 
	 * @param sortname
	 *            数据库字段名
	 * @return 返回自身对象
	 */
	public Query addSort(String sortname) {
		return this.addSort(sortname, SqlConsts.ASC);
	}
	
	/**
	 * 字段排序
	 * @param sortname 数据库字段名
	 * @param sort 排序类型
	 * @return
	 */
	public Query addSort(String sortname, Sort sort) {
		return this.addSort(sortname, sort.name());
	}

	/**
	 * 添加排序字段。
	 * 已废弃，推荐用：public Query addSort(String sortname, Sort sort)
	 * @param sortname
	 *            数据库字段名
	 * @param sortorder
	 *            排序方式,ASC,DESC
	 * @return 返回自身对象
	 */
	@Deprecated
	public Query addSort(String sortname, String sortorder) {

		if (sortname != null && sortname.length() > 0) {
			if (this.orderInfo == null) {
				orderInfo = new LinkedHashSet<String>();
			}
			// 简单防止SQL注入
			sortname = sortname.replace(REG_SQL_INJECT, SqlConsts.EMPTY);

			if (!SqlConsts.DESC.equalsIgnoreCase(sortorder)) {
				sortorder = SqlConsts.ASC;
			}

			orderInfo.add(sortname + SqlConsts.BLANK + sortorder);
		}

		return this;
	}

	@Override
	public boolean getOrderable() {
		return orderInfo != null;
	}

	@Override
	public String getOrder() {
		if(orderInfo == null) {
			throw new NullPointerException("orderInfo为空,必须设置排序字段.");
		}
		StringBuilder sb = new StringBuilder();
		for (String order : this.orderInfo) {
			sb.append(",").append(order);
		}
		if (sb.length() > 0) {
			return sb.toString().substring(1);
		} else {
			return "";
		}
	}

	@Override
	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	//

	/**
	 * 添加关联条件
	 * 
	 * @param joinSql
	 * @return
	 */
	public Query join(String joinSql) {
		this.addExpression(new JoinExpression(joinSql));
		return this;
	}
	
	/**
	 * 使用key/value进行多个等于的比对,相当于多个eq的效果
	 * @param map
	 * @return
	 */
	public Query allEq(LinkedHashMap<String, Object> map) {
		Set<String> keys = map.keySet();
		for (String columnName : keys) {
			this.eq(columnName, map.get(columnName));
		}
		return this;
	}
	
	/**
	 * 添加等于条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return
	 */
	public Query eq(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, value));
		return this;
	}

	/**
	 * 添加不等于条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return
	 */
	public Query notEq(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, "<>", value));
		return this;
	}

	/**
	 * 添加大于条件,>
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return
	 */
	public Query gt(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, ">", value));
		return this;
	}

	/**
	 * 大于等于,>=
	 * @param columnName
	 * @param value
	 * @return
	 */
	public Query ge(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, ">=", value));
		return this;
	}

	/**
	 * 添加小于条件,<
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return
	 */
	public Query lt(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, "<", value));
		return this;
	}

	/**
	 * 小于等于,<=
	 * @param columnName
	 * @param value
	 * @return
	 */
	public Query le(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, "<=", value));
		return this;
	}
	
	/**
	 * 添加模糊查询条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return
	 */
	public Query like(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, SqlConsts.LIKE, value));
		return this;
	}

	/**
	 * 添加IN条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return
	 */
	public Query in(String columnName, Collection<?> value) {
		return this.in(columnName, value, null);
	}

	/**
	 * 添加IN条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return
	 */
	public <T> Query in(String columnName, Collection<T> value, ValueConvert<T> valueConvert) {
		if (value == null || value.size() == 0) {
			throw new RuntimeException("查询条件:[" + columnName + " in(...)]" + "至少要有一个值.");
		}

		Expression expression = valueConvert == null ? new ListExpression(columnName, value)
				: new ListExpression(columnName, value, valueConvert);

		this.addExpression(expression);

		return this;
	}

	/**
	 * 添加IN条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return
	 */
	public Query in(String columnName, Object[] value) {
		if (value == null || value.length == 0) {
			throw new RuntimeException("查询条件:[" + columnName + " in(...)]" + "至少要有一个值.");
		}
		this.addExpression(new ListExpression(columnName, value));
		return this;
	}

	/**
	 * 添加not in条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return
	 */
	public Query notIn(String columnName, Collection<?> value) {
		this.addExpression(new ListExpression(columnName, "NOT IN", value));
		return this;
	}

	/**
	 * 添加not in条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return
	 */
	public <T> Query notIn(String columnName, Collection<T> value, ValueConvert<T> valueConvert) {
		this.addExpression(new ListExpression(columnName, "NOT IN", value, valueConvert));
		return this;
	}

	/**
	 * 添加not in条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return
	 */
	public Query notIn(String columnName, Object[] value) {
		this.addExpression(new ListExpression(columnName, "NOT IN", value));
		return this;
	}

	/**
	 * 添加自定义sql条件
	 * 
	 * @param sql
	 *            自定义sql
	 * @return
	 */
	public Query sql(String sql) {
		this.addExpression(new SqlExpression(sql));
		return this;
	}

	public Query notNull(String column) {
		return this.sql(column + " IS NOT NULL");
	}

	public Query isNull(String column) {
		return this.sql(column + " IS NULL");
	}

	public Query notEmpty(String column) {
		return this.sql(column + " IS NOT NULL AND " + column + " <> '' ");
	}

	public Query isEmpty(String column) {
		return this.sql(column + " IS NULL OR " + column + " = '' ");
	}

	public Query notEq() {
		return this.sql("1=2");
	}

}
