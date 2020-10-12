
package codeGenerate.mybatis.constants;

public class DBType {

	public static final String ORACLE = "ORACLE";

	public static final String MYSQL = "MYSQL";

	public static final String SQLSERVER = "SQLSERVER";

	public static final String SQLSERVER2005 = "SQLSERVER2005";

	public static final String DB2 = "DB2";

	public static final String INFORMIX = "INFORMIX";

	public static final String SYBASE = "SYBASE";

	public static final String POSTGRESQL = "POSTGRESQL";

	public static final String TERADATA = "TERADATA";

	public static final String NETEZZA = "NETEZZA";

	public static final String OTHER = "OTHER";

	public static final String EMPTY = "EMPTY";

	public static String getByDriverName(String driverName) {
		// 空类型  
		if (null == driverName || driverName.trim().length() < 1) {
			return EMPTY;
		}
		// 截断首尾空格,转换为大写  
		driverName = driverName.trim();

		// Oracle数据库  
		if ("oracle.jdbc.driver.OracleDriver".equals(driverName)) {
			return ORACLE;
		}
		// MYSQL 数据库  
		if ("com.mysql.jdbc.Driver".equals(driverName)) {
			return MYSQL;
		}
		// SQL SERVER 数据库  
		if ("com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(driverName)) {
			return SQLSERVER2005;
		}
		if ("com.microsoft.jdbc.sqlserver.SQLServerDriver".equals(driverName)) {
			return SQLSERVER;
		}
		// DB2 数据库  
		if ("com.ibm.db2.jcc.DB2Driver".equals(driverName)) {
			return DB2;
		}
		// INFORMIX 数据库  
		if ("com.informix.jdbc.IfxDriver".equals(driverName)) {
			return INFORMIX;
		}
		// SYBASE 数据库  
		if ("com.sybase.jdbc2.jdbc.SybDriver".equals(driverName) || "com.sybase.jdbc3.jdbc.SybDriver".equals(driverName)) {
			return SYBASE;
		}
		//PostgreSQL
		if ("org.postgresql.Driver".equals(driverName)) {
			return POSTGRESQL;
		}
		//Teradata
		if ("com.ncr.teradata.TeraDriver".equals(driverName)) {
			return TERADATA;
		}
		//Netezza
		if ("org.netezza.Driver".equals(driverName)) {
			return NETEZZA;
		}

		// 默认,返回其他
		return OTHER;
	}
}
