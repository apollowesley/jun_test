package site.zhouinfo.mysql.dao;

import java.sql.*;

/**
 *
 * Author:      zhou
 * Create Date：2016-02-02 22:25
 */
public class Dao {

	private static String url = "jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=utf8";
	private static String user = "root";
	private static String password = "root";

	/**
	 * 链接数据库
	 */
	public static Connection getConnection() {
		Connection conn = null;
		//connect the MySQL database
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
