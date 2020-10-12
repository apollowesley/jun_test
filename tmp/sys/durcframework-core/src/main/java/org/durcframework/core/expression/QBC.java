package org.durcframework.core.expression;

import java.util.Collection;
import java.util.List;

import org.durcframework.core.SpringContext;
import org.durcframework.core.dao.BaseDao;
import org.durcframework.core.expression.subexpression.DefaultJoinExpression;
import org.durcframework.core.expression.subexpression.InnerJoinExpression;
import org.durcframework.core.expression.subexpression.ListExpression;
import org.durcframework.core.expression.subexpression.ValueExpression;
import org.durcframework.core.service.SearchService;

/**
 * QBC查询类
 * 
// 查询姓名为Jim,并且id是20和25的学生
// 查询结果以name字段升序

// SELECT * FROM student t WHERE name = 'Jim' AND id IN ( 20,25 ) ORDER BY
// name ASC LIMIT 0,10
QBC<Student> qbc = QBC.create(dao);
List list = qbc
	.eq("name", "Jim")
	.in("id", Arrays.asList(20, 25))
	.sort("name")
	.list();
				
 * @author tanghc
 * 2015-6-30
 */
public class QBC<Entity> {

	private ExpressionQuery query;
	private BaseDao<Entity> dao;

	private QBC() {
	};
		
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <Entity> QBC<Entity> create(SearchService service) {
		return create(service.getDao());
	}

	@SuppressWarnings("unchecked")
	public static <Entity> QBC<Entity> create(Class<Entity> clazzDao) {
		return create((BaseDao<Entity>) SpringContext.getBean(clazzDao));
	}
	
	public static <Entity> QBC<Entity> create(BaseDao<Entity> dao) {
		QBC<Entity> qbc = new QBC<Entity>();
		
		qbc.dao = dao;
		qbc.query = new ExpressionQuery();
		
		return qbc;
	}
	
	public void addExpressions(List<Expression> expressions) {
		query.addAll(expressions);
	}
	
	/**
	 * 查询数据,带有分页,即limit 0,10
	 * @return
	 */
	public List<Entity> list() {
		return dao.find(query);
	}
	
	/**
	 * 查询全部
	 * @return
	 */
	public List<Entity> listAll() {
		query.setQueryAll(true);
		return dao.find(query);
	}
	
	/**
	 * 查询一条记录,如果没有返回null
	 * @return
	 */
	public Entity listOne() {
		
		this.start(0).limit(1);
		
		List<Entity> list = dao.find(query);
		if(list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	public int count() {
		return dao.findTotalCount(query);
	}
	
	/**
	 * 添加排序
	 * @param sortName 数据库字段名
	 * @return
	 */
	public QBC<Entity> sort(String sortName) {
		query.addSort(sortName);
		return this;
	}

	/**
	 * 添加排序
	 * @param sortName 数据库字段名
	 * @param order 数据库字段名
	 * @return
	 */
	public QBC<Entity> sort(String sortName, String order) {
		query.addSort(sortName, order);
		return this;
	}

	/**
	 * 设置查询起始位置
	 * @param start
	 * @return
	 */
	public QBC<Entity> start(int start) {
		query.setStart(start);
		return this;
	}

	/**
	 * 设置查询数据量
	 * @param limit
	 * @return
	 */
	public QBC<Entity> limit(int limit) {
		query.setLimit(limit);
		return this;
	}
	
	/**
	 * 内连接
	 * @param secondTableName 第二张表名
	 * @param secondTableTableAlias 第二张表别名
	 * @param firstTableColumn 第一张表管理字段
	 * @param secondTableColumn 第二张管理表字段
	 */
	public QBC<Entity> innerJoin(String secondTableName,
			String secondTableTableAlias, String firstTableColumn,
			String secondTableColumn) {
		query.addJoinExpression(new InnerJoinExpression(secondTableName, secondTableTableAlias, firstTableColumn, secondTableColumn));
		return this;
	}
	
	/**
	 * 连接表
	 * @param joinSql 连接表sql,如:LEFT JOIN table t2 ON t1.id = t2.id
	 * @return
	 */
	public QBC<Entity> join(String joinSql) {
		query.addJoinExpression(new DefaultJoinExpression(joinSql));
		return this;
	}
	
	
	public QBC<Entity> eq(String column, Object value) {
		query.addValueExpression(new ValueExpression(column, value));
		return this;
	}

	public QBC<Entity> eq(String column, String equal, Object value) {
		query.addValueExpression(new ValueExpression(column, equal, value));
		return this;
	}

	public QBC<Entity> eq(String joint, String column, String equal, Object value) {
		query.addValueExpression(new ValueExpression(joint, column, equal,
				value));
		return this;
	}
	
	public QBC<Entity> in(String column, Collection<?> value) {
		query.addListExpression(new ListExpression(column, value));
		return this;
	}

	public QBC<Entity> in(String column, String equal, Collection<?> value) {
		query.addListExpression(new ListExpression(column, equal, value));
		return this;
	}

	public QBC<Entity> in(String column, Object[] value) {
		query.addListExpression(new ListExpression(column, value));
		return this;
	}

	public QBC<Entity> in(String column, String equal, Object[] value) {
		query.addListExpression(new ListExpression(column, equal, value));
		return this;
	}

	@SuppressWarnings("rawtypes")
	public QBC<Entity> in(String column, Collection<?> value, ValueConvert valueConvert) {
		query.addListExpression(new ListExpression(column, value, valueConvert));
		return this;
	}
}
