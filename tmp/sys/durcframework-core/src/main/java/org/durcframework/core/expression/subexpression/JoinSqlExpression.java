package org.durcframework.core.expression.subexpression;

public class JoinSqlExpression extends DefaultJoinExpression {

	/**
	 * 自定义连接语句
	 * @param joinSql 如:inner join table2 t2 on t.id = t2.id
	 */
	public JoinSqlExpression(String joinSql) {
		super(joinSql);
	}

}
