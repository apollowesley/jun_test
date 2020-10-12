package org.durcframework.entity;

import java.util.List;

import org.durcframework.expression.Expression;
import org.durcframework.expression.ExpressionBuilder;
import org.durcframework.expression.ExpressionQuery;

/**
 * 负责查询的实体类,其它关于查询的类都要继承该类
 * 
 * @author hc.tang 2013年11月14日
 * 
 */
public class SearchEntity {

	/** 第一页 */
	private int pageIndex = 1;
	/** 每页10条记录 */
	private int pageSize = 10;

	private String sortname;
	private String sortorder;

	/**
	 * 构建查询条件
	 * @return
	 */
	public ExpressionQuery buildExpressionQuery() {
		ExpressionQuery query = new ExpressionQuery();

		addAnnotionExpression(query);

		bindPageInfo(query);

		return query;
	}

	// //开启off/on注解,其之间的代码不会被eclipse格式化(CTRL + SHIFT + F)
	// @off
	// 添加注解查询条件
	private void addAnnotionExpression(ExpressionQuery query) {
		List<Expression> expresList = ExpressionBuilder.buildExpressions(this);

		for (Expression express : expresList) {
			query.add(express);
		}
	}

	// @on

	// 绑定分页信息
	private void bindPageInfo(ExpressionQuery query) {
		query.setPageIndex(getPageIndex());
		query.setPageSize(getPageSize());
		query.setSortname(getSortname());
		query.setSortorder(getSortorder());
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex <= 0) {
			pageIndex = 1;
		}
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize <= 0) {
			pageSize = 10;
		}
		this.pageSize = pageSize;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
}
