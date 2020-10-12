package club.lis.util;


import club.lis.service.ITableService;
import club.lis.service.impl.MysqlTableService;
import club.lis.service.impl.OracleTableService;
import club.lis.service.impl.SqlServerTableService;

/**
 * 针对各类数据库的服务创建工厂
 * @author mars.liu
 *
 */
public class TableServiceFactory {
	
	public static ITableService getInstance(String dbType) {
		dbType = dbType.toLowerCase();
		if (dbType.equals("mysql")) {
			return getMysqlService();
		} else if (dbType.equals("oracle")) {
			return getOracleService();
		} else if (dbType.equals("sqlserver")) {
			return getSqlServerService();
		}
		throw new RuntimeException("不支持的数据库类型");
	}
	
	private static ITableService getMysqlService(){
		return new MysqlTableService();
	}
	
	private static ITableService getOracleService(){
		return new OracleTableService();
	}
	
	private static ITableService getSqlServerService(){
		return new SqlServerTableService();
	}

}
