package org.durcframework.core.expression.projection;

import org.durcframework.core.SQLExpre;

/**
 * 聚合列对象
 * @author tanghc
 */
public class Projection implements SQLExpre {
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
	@Override
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
