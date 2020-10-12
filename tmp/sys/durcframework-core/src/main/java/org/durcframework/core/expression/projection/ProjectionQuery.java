package org.durcframework.core.expression.projection;

import java.util.ArrayList;
import java.util.List;

import org.durcframework.core.expression.Expression;
import org.durcframework.core.expression.ExpressionQuery;
import org.durcframework.core.expression.subexpression.ListExpression;
import org.durcframework.core.expression.subexpression.SqlExpression;
import org.durcframework.core.expression.subexpression.ValueExpression;

/**
 * 聚合查询
 * <pre>
 * {@literal
```
@Autowired
StudentDao dao;
```

- 查询年纪最小的生日
SELECT MAX(BIRTHDAY) AS maxBirth FROM student t 

```
ProjectionQuery query = new ProjectionQuery();		
query.addProjection(Projections.max("BIRTHDAY", "maxBirth"));
List<Map<String, Object>> list = dao.findProjection(query);
```

- 查询每个部门年纪最小的生日,按部门排序
SELECT DEPARTMENT AS dept , MAX(BIRTHDAY) AS birth 
FROM student t 
GROUP BY dept 
ORDER BY dept ASC 

```
ProjectionQuery query = new ProjectionQuery();
// 添加列
query.addProjection(Projections.column("DEPARTMENT", "dept"));
query.addProjection(Projections.max("BIRTHDAY", "birth"));

// 添加group by
query.addGroupBy("dept");

query.addSort("dept", "asc");

List<Map<String, Object>> list = dao.findProjection(query);    
```

- 使用having,查询每个部门年纪最小的生日,并且生日小于2012-10-12

```
ProjectionQuery query = new ProjectionQuery();
// 添加列
query.addProjection(Projections.column("DEPARTMENT", "dept"));
query.addProjection(Projections.max("BIRTHDAY", "birth"));

// 添加group by
query.addGroupBy("dept");
// 添加having
query.addHaving(new ValueExpression("birth", "<","2012-10-12 00:00:00"));
		
query.addSort("dept", "asc");

List<Map<String, Object>> list = dao.findProjection(query);
```

- 使用where ,查询每个部门年纪最小的生日,并且部门在18,19,20之间,并且生日小于2012-10-12 
SELECT DEPARTMENT AS dept , MAX(BIRTHDAY) AS birth 
FROM student t 
WHERE DEPARTMENT IN (18,19,20) 
GROUP BY dept 
HAVING birth < '2012-10-12 00:00:00' 
ORDER BY dept asc 

```
ProjectionQuery query = new ProjectionQuery();
// 添加列
query.addProjection(Projections.column("DEPARTMENT", "dept"));
query.addProjection(Projections.max("BIRTHDAY", "birth"));
// 添加where
query.add(new ListExpression("DEPARTMENT", Arrays.asList(18,19,20)));
// 添加group by
query.addGroupBy("dept");
// 添加having
query.addHaving(new ValueExpression("birth", "<","2012-10-12 00:00:00"));

query.addSort("dept", "asc");

List<Map<String, Object>> list = dao.findProjection(query);
```

- 查询总数
SELECT COUNT(*) FROM student t 

```
ProjectionQuery query = new ProjectionQuery();
query.addProjection(Projections.count());
List<Map<String, Object>> list = dao.findProjection(query);
```

 * 
 * }
 * 
 * </pre>
 * @author tanghc
 *
 */
public class ProjectionQuery extends ExpressionQuery {

	private List<ValueExpression> valueHavingExprList = new ArrayList<ValueExpression>();
	private List<ListExpression> listHavingExprList = new ArrayList<ListExpression>();
	private List<SqlExpression> sqlHavingExpreList = new ArrayList<SqlExpression>();

	private ProjectionList projectionList = ProjectionList.projectionList();
	private ProjectionList groupByList = ProjectionList.projectionList();

	/**
	 * 添加聚合列
	 * @param projection 聚合对象
	 * @return
	 */
	public ExpressionQuery addProjection(Projection projection) {
		projectionList.add(projection);
		return this;
	}

	/**
	 * 添加聚合列
	 * @param expre 自定义列
	 * @return
	 */
	public ExpressionQuery addProjection(String expre) {
		projectionList.add(Projections.column(expre));
		return this;
	}

	/**
	 * 添加group by
	 * @param columns
	 * @return
	 */
	public ExpressionQuery addGroupBy(String ...columns) {
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
	public ExpressionQuery addHavingSqlExpression(SqlExpression expression) {
		sqlHavingExpreList.add(expression);
		return this;
	}
	
	/**
	 * 添加having条件,单值查询
	 * @param expression
	 * @return
	 */
	public ExpressionQuery addHavingValueExpression(ValueExpression expression) {
		valueHavingExprList.add(expression);
		return this;
	}

	/**
	 * 添加having条件,list查询
	 * @param expression
	 * @return
	 */
	public ExpressionQuery addHavingListExpression(ListExpression expression) {
		listHavingExprList.add(expression);
		return this;
	}

	/**
	 * 添加having
	 * @param expression
	 * @return
	 */
	public ExpressionQuery addHaving(Expression expression) {
		expression.addToHaving(this);
		return this;
	}

	public ProjectionList getProjectionList() {
		return projectionList;
	}

	public ProjectionList getGroupByList() {
		return groupByList;
	}

	public List<ValueExpression> getValueHavingExprList() {
		return valueHavingExprList;
	}

	public List<ListExpression> getListHavingExprList() {
		return listHavingExprList;
	}

	public List<SqlExpression> getSqlHavingExpreList() {
		return sqlHavingExpreList;
	}

}
