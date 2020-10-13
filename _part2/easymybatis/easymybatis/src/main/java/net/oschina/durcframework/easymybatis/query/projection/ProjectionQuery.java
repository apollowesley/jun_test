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

import java.util.ArrayList;
import java.util.List;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.expression.Expression;
import net.oschina.durcframework.easymybatis.query.expression.ExpressionListable;
import net.oschina.durcframework.easymybatis.query.expression.ExpressionSqlable;
import net.oschina.durcframework.easymybatis.query.expression.ExpressionValueable;
import net.oschina.durcframework.easymybatis.query.expression.ListExpression;
import net.oschina.durcframework.easymybatis.query.expression.SqlExpression;
import net.oschina.durcframework.easymybatis.query.expression.ValueExpression;

/**
 * 聚合查询
 * @author tanghc
 *
 */
public class ProjectionQuery extends Query {

	private List<ExpressionValueable> valueHavingExprList = new ArrayList<ExpressionValueable>();
	private List<ExpressionListable> listHavingExprList = new ArrayList<ExpressionListable>();
	private List<ExpressionSqlable> sqlHavingExpreList = new ArrayList<ExpressionSqlable>();

	private ProjectionList projectionList = ProjectionList.projectionList();
	private ProjectionList groupByList = ProjectionList.projectionList();
	
	public ProjectionQuery addHaving(Expression expression) {
		if (expression instanceof ExpressionValueable) {
			if(valueHavingExprList == null) {valueHavingExprList = new ArrayList<>();}
			valueHavingExprList.add((ExpressionValueable)expression);
		} else if (expression instanceof ExpressionListable) {
			if(listHavingExprList == null) {listHavingExprList = new ArrayList<>();}
			listHavingExprList.add((ExpressionListable)expression);
		} else if (expression instanceof ExpressionSqlable) {
			if(sqlHavingExpreList == null) {sqlHavingExpreList = new ArrayList<>();}
			sqlHavingExpreList.add((ExpressionSqlable)expression);
		}
		return this;
	}

	/**
	 * 添加聚合列
	 * @param projection 聚合对象
	 * @return
	 */
	public ProjectionQuery addProjection(Projection projection) {
		projectionList.add(projection);
		return this;
	}

	/**
	 * 添加聚合列
	 * @param expre 自定义列
	 * @return
	 */
	public ProjectionQuery addProjection(String expre) {
		this.addProjection(Projections.column(expre));
		return this;
	}

	/**
	 * 添加group by
	 * @param columns
	 * @return
	 */
	public ProjectionQuery addGroupBy(String ...columns) {
		for (String column : columns) {
			groupByList.add(Projections.column(column));
		}
		return this;
	}
	
	/**
	 * 添加having条件,自定义sql
	 * @param expression
	 * @return
	 */
	public ProjectionQuery addHavingSqlExpression(SqlExpression expression) {
		this.addHaving(expression);
		return this;
	}
	
	/**
	 * 添加having条件,单值查询
	 * @param expression
	 * @return
	 */
	public ProjectionQuery addHavingValueExpression(ValueExpression expression) {
		this.addHaving(expression);
		return this;
	}

	/**
	 * 添加having条件,list查询
	 * @param expression
	 * @return
	 */
	public ProjectionQuery addHavingListExpression(ListExpression expression) {
		this.addHaving(expression);
		return this;
	}

	public ProjectionList getProjectionList() {
		return projectionList;
	}

	public ProjectionList getGroupByList() {
		return groupByList;
	}

	public List<ExpressionValueable> getValueHavingExpressions() {
		return valueHavingExprList;
	}

	public List<ExpressionListable> getListHavingExpressions() {
		return listHavingExprList;
	}

	public List<ExpressionSqlable> getSqlHavingExpressions() {
		return sqlHavingExpreList;
	}

}
