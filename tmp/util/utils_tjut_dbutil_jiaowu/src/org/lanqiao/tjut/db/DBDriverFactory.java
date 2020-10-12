	package org.lanqiao.tjut.db;

public class DBDriverFactory {
	/**
	 * 获取DBDriver的实例对象
	 * 
	 * @return 实例
	 */
	public static DBDriver getDBDriverInstance() {
		return new DBDriver();
	}
}
