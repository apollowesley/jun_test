package org.nature.framework.db;

import org.nature.framework.helper.ConfigHelper;

public abstract class DbIdentifyer {
	
	public static boolean isMySql(){
		return "com.mysql.jdbc.Driver".equals(ConfigHelper.getJdbcDriver());
	}
	public static boolean isMsSql(){
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(ConfigHelper.getJdbcDriver())
				||"net.sourceforge.jtds.jdbc.Driver".equals(ConfigHelper.getJdbcDriver());
	}
	public static boolean isOracle(){
		return "oracle.jdbc.OracleDriver".equals(ConfigHelper.getJdbcDriver())
				||"oracle.jdbc.driver.OracleDriver".equals(ConfigHelper.getJdbcDriver());
	}
}
