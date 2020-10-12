package util;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class DBUtil implements Serializable {
	private static ThreadLocal<Connection> tl;  // 声明一个线程：该线程主要用于存储Connection对象
	private static BasicDataSource dataSource; // 声明数据库链接池
	
	static {
		tl = new ThreadLocal<Connection>(); // 实例化线程
		try {
			Properties prop = new Properties(); 
			prop.load(DBUtil.class.getClassLoader().getResourceAsStream("config.properties")); // 加载配置文件
			
			// 利用 properties的对象从配置文件中读取数据
			String className = prop.getProperty("className");
			String url = prop.getProperty("url");
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");
			Integer maxActive = Integer.parseInt(prop.getProperty("maxActive")); 	
			Integer maxWait = Integer.parseInt(prop.getProperty("maxWait"));
			
			// 输出从配置文件中读取到的文件
//			System.out.println(className);
//			System.out.println(url);
//			System.err.println(userName);
//			System.out.println(password);
//			System.out.println(maxActive);
//			System.out.println(maxWait);
			
			dataSource = new BasicDataSource();  // 实例化连接池

//			设置连接池的相关属性
			dataSource.setDriverClassName(className);
			dataSource.setUrl(url);
			dataSource.setUsername(userName);
			dataSource.setPassword(password);
			dataSource.setMaxActive(maxActive);
			dataSource.setMaxWait(maxWait);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	连接方法
	public static Connection getConnection() throws Exception {
		try {
			Connection conn = dataSource.getConnection();
			tl.set(conn);
			return conn;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
//	关闭方法
	public static void closeConnection() {
		try {
			Connection conn = tl.get();
			tl.remove();
			if(null != conn) {
				conn.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
