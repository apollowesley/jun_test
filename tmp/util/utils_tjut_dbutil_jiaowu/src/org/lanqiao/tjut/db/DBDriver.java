 package org.lanqiao.tjut.db;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public class DBDriver {

	/**
	 * dbutils-beanlist查询
	 * 
	 * @param sql
	 *            查询语句
	 * @param rsh
	 *            查询结果结构handler
	 * @return 查询结果
	 */
	public <T> T query(String sql, ResultSetHandler<T> rsh) {
		// 返回值对象（泛型）
		T lst = null;
		try {
			// 在dbutils工具中 使用 queryrunner进行查询操作
			QueryRunner qr = new QueryRunner(DBDataSource.getDataSource());
			// 获取查询结果list
			lst = qr.query(sql, rsh);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}

	/**
	 * @param sql
	 *            查询语句
	 * @param rsh
	 *            结果handler
	 * @param params
	 *            查询参数
	 * @return 结果集
	 */
	public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) {
		// 返回值对象（泛型）
		T lst = null;
		try {
			// 在dbutils工具中 使用 queryrunner进行查询操作
			QueryRunner qr = new QueryRunner(DBDataSource.getDataSource());
			// 获取查询结果list
			lst = qr.query(sql, rsh, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}

	/**
	 * 新增和修改操作
	 * 
	 * @param sql
	 *            更新、删除、修改语句
	 * @param params
	 *            参数集合
	 * @return 所影响的记录条数
	 */
	public int update(String sql, Object... params) {
		// 返回值对象（泛型）
		int re_i = 0;
		try {
			// 在dbutils工具中 使用 queryrunner进行查询操作
			QueryRunner qr = new QueryRunner(DBDataSource.getDataSource());
			// 获取查询结果list
			re_i = qr.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_i;
	}

	/**
	 * 新增和修改批处理操作
	 * 
	 * @param sql
	 *            更新语句
	 * @param params
	 *            参数集合
	 * @return 批处理所影响的记录条数
	 */
	public int[] batch(String sql, Object[][] params) {
		// 返回值对象（泛型）
		int[] re_i = null;
		try {
			// 在dbutils工具中 使用 queryrunner进行查询操作
			QueryRunner qr = new QueryRunner(DBDataSource.getDataSource());
			// 获取查询结果list
			re_i = qr.batch(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_i;
	}

	/**
	 * 新增数据并返回新增结果对象
	 * 
	 * @param sql
	 *            新增语句
	 * @param rsh
	 *            新增语句handler
	 * @param params
	 *            参数集合
	 * @return 新增的结果对象
	 */
	public <T> T insert(String sql, ResultSetHandler<T> rsh, Object... params) {
		// 返回值对象（泛型）
		T t = null;
		try {
			// 在dbutils工具中 使用 queryrunner进行查询操作
			QueryRunner qr = new QueryRunner(DBDataSource.getDataSource());
			// 获取查询结果list
			t = qr.insert(sql, rsh, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

}
