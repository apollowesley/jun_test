package org.nature4j.framework.db;

public abstract class DbIdentifyer {
	
	public static boolean isMySql(String jdbcDriver){
		return jdbcDriver.contains("com.mysql.jdbc");
	}
	public static boolean isMsSql(String jdbcDriver){
		return jdbcDriver.contains("com.microsoft.sqlserver.jdbc")
				||jdbcDriver.contains("net.sourceforge.jtds.jdbc");
	}
	public static boolean isOracle(String jdbcDriver){
		return jdbcDriver.contains("oracle.jdbc")
				||jdbcDriver.contains("oracle.jdbc");
	}
	
	public static boolean isSqlite(String jdbcDriver){
		return jdbcDriver.contains("org.sqlite");
	}
}
