package org.durcframework.core.expression.projection;

/**
 * 聚合列工厂类
 * @author tanghc
 *
 */
public class Projections {
	
	/**
	 * 自定义列
	 * @param columnName 列名,如count(*),id,等
	 * @return
	 */
	public static Projection column(String columnName) {
		return column(columnName, null);
	}
	
	/**
	 * 自定义列
	 * @param columnName 列名,如count(*),id,等
	 * @param alias 别名
	 * @return
	 */
	public static Projection column(String columnName,String alias) {
		return new Projection(columnName, alias);
	}
	
	/**
	 * 聚合函数最大值,max("age") ==> max(age)
	 * @param column 数据库列名
	 * @return
	 */
	public static Projection max(String column) {
		return max(column, null);
	}

	/**
	 * 聚合函数最大值,max("age","maxAge") ==> max(age) AS maxAge
	 * @param column 数据库列名
	 * @param alias 别名
	 * @return
	 */
	public static Projection max(String column, String alias) {
		return buildProjectionColumn(ProjectionType.MAX, column, alias);
	}
	
	/**
	 * 聚合函数总和,sum("age") ==> sum(age)
	 * @param column 数据库列名
	 * @return
	 */
	public static Projection sum(String column) {
		return sum(column, null);
	}

	/**
	 * 聚合函数总和,sum("age","sumAge") ==> sum(age) AS sumAge
	 * @param column 数据库列名
	 * @param alias 别名
	 * @return
	 */
	public static Projection sum(String column, String alias) {
		return buildProjectionColumn(ProjectionType.SUM, column, alias);
	}
	
	/**
	 * 聚合函数最小值,min("age","minAge") ==> min(age) AS minAge
	 * @param column 数据库列名
	 * @param alias 别名
	 * @return
	 */
	public static Projection min(String column, String alias) {
		return buildProjectionColumn(ProjectionType.MIN, column, alias);
	}
	
	/**
	 * 聚合函数最小值,min("age") ==> min(age)
	 * @param column 数据库列名
	 * @return
	 */
	public static Projection min(String column) {
		return min(column, null);
	}
	
	/**
	 * 聚合函数,总数count("id","cnt") ==> COUNT(id) AS cnt
	 * @param column 列名
	 * @param alias 别名
	 * @return
	 */
	public static Projection count(String column,String alias) {
		return buildProjectionColumn(ProjectionType.COUNT, column, alias);
	}
	
	/**
	 * 聚合函数,总数count("cnt") ==> COUNT(*) AS cnt
	 * @param alias 别名
	 * @return
	 */
	public static Projection count(String alias) {
		return count("*", alias);
	}
	
	/**
	 * 聚合函数,总数count() ==> COUNT(*)
	 * @return
	 */
	public static Projection count() {
		return count("*", null);
	}
	
	
	/**
	 * 聚合函数,总数avg("age","avgAge") ==> avg(age) AS avgAge
	 * @param column 列名
	 * @param alias 别名
	 * @return
	 */
	public static Projection avg(String column,String alias) {
		return buildProjectionColumn(ProjectionType.AVG, column, alias);
	}
	
	
	/**
	 * 聚合函数,总数avg("age") ==> avg(age)
	 * @param column 列名
	 * @return
	 */
	public static Projection avg(String column) {
		return avg(column, null);
	}
	
	
	private static Projection buildProjectionColumn(ProjectionType type,String column, String alias) {
		return new Projection(type.name() + "(" + column + ")", alias);
	}

}
