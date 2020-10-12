package com.freedom.mysql.myrwsplit.helper;

import org.apache.ibatis.mapping.SqlCommandType;

public class ThreadLocalHelper {
	public static void reset() {
		// reset all,reset everything		
		SqlCommandTypeThreadLocal.set(SqlCommandType.UNKNOWN);//sql command type
		AutoCommitThreadLocal.set(true);//auto Commit
		BoundSqlThreadLocal.set(null);//sql
		FetchConnectionTimeThreadLocal.set((long) 0);//getConnection
	}

	public static ThreadLocal<SqlCommandType> SqlCommandTypeThreadLocal = new ThreadLocal<SqlCommandType>() {

		public SqlCommandType initialValue() {
			return SqlCommandType.UNKNOWN;
		}
	};

	public static ThreadLocal<Boolean> AutoCommitThreadLocal = new ThreadLocal<Boolean>() {

		public Boolean initialValue() {//
			return true;
		}
	};

	public static ThreadLocal<String> BoundSqlThreadLocal = new ThreadLocal<String>() {
		public String initialValue() {
			return null;
		}
	};

	public static ThreadLocal<Long> FetchConnectionTimeThreadLocal = new ThreadLocal<Long>() {
		public Long initialValue() {
			return (long) 0;
		}
	};
}
