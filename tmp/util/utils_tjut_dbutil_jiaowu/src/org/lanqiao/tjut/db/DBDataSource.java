package org.lanqiao.tjut.db;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

/**
 * 获取数据库数据源使用dbcp数据库连接池进行管理
 * 
 * @author Administrator
 *
 */
public class DBDataSource {

	// 数据源对象
	private static DataSource dataSource;
	
	//禁止外部创建该类的实例对象
	private DBDataSource() {
	}

	public static DataSource getDataSource() {
		// 如果实例对象为null则进行创建
		if (dataSource == null) {
			try {
				// 属性配置文件对象
				Properties prop = new Properties();
				prop.load(DBDataSource.class.getClassLoader().getResourceAsStream("db.properties"));
				// 获取数据源生成工厂类的静态方法获取数据源对象
				dataSource = BasicDataSourceFactory.createDataSource(prop);
			} catch (Exception e) {
				// 异常处理
				System.out.println("生成数据库连接池错误："+e.getMessage());
			}
		}
		return dataSource;
	}
}
