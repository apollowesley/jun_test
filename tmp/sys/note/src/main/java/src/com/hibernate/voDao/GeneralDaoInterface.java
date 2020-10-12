/**
 * 
 * @auther wuwang
 * @createTime 2015-1-24 下午11:26:13
 */
package com.hibernate.voDao;

import java.util.List;

/**
 * 
 * 
 * @author peaches
 */
public interface GeneralDaoInterface {

	/**
	 * @param sql
	 * @return
	 * @createTime 2015-1-24 下午11:42:08
	 */
	int executeUpdate(String sql);

	/**
	 * @param sql
	 * @param args
	 * @return
	 * @createTime 2015-1-24 下午11:41:57
	 */
	int executeUpdate(String sql, String... args);

	/**
	 * @param sql
	 * @param args
	 * @return
	 * @createTime 2015-1-25 上午1:32:19
	 */
	List getList(String sql, String... args);

	/**
	 * @param hql
	 * @param args
	 * @return
	 * @createTime 2015-3-7 上午6:45:17
	 */
	List getListHql(String hql, String[] args);
}
