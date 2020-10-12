package com.luoqy.speedy.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;

import com.luoqy.speedy.common.ConfigManage;
import com.luoqy.speedy.util.Result;

/**
 * @author qf sql语句执行
 */
public class MySqldbUtil {
	/**
	 * @param sqlStr 执行SQL语句
	 * @param type 列表=list 否则为单条数据
	 * @param dataBase 数据库资料，不填写设为null则为默认
	 * @return Object map集合
	 */
	@Async
	public static Object mysqlSelect(String sqlStr, String type,DataBase dataBase) {
		Result result = new Result();
		result.setData(null);
		Connection conn = null;
		Statement stmt = null;
		try {
			String url = "";
			String user = "";
			String password = "";
			String driverClass = "";
			if (null==dataBase||null == dataBase.getUrl()||"".equals(dataBase.getUrl())) {
				// 读取配置文件
				Map<String,String> props=ConfigManage.find("jdbc");
				//初始化的时候回查询不到
				if (null == props||props.size()==0) {
					url = "jdbc:mysql://127.0.0.1:3306/speedy?useUnicode=true&characterEncoding=utf8";
					driverClass = "com.mysql.cj.jdbc.Driver";
					user = "root";
					password = "root";
					Class.forName(driverClass);
				} else {
					// 加载文件
					// 读取信息
					url = props.get("url");
					user = props.get("username");
					password = props.get("password");
					driverClass = props.get("driverClassName");
					
					// 注册驱动程序
					Class.forName(driverClass);
				}
			} else {
				url = dataBase.getUrl();
				user = dataBase.getUsername();
				password = dataBase.getPassword();
				driverClass = dataBase.getDriverClass();
				// 注册驱动程序
				Class.forName(driverClass);
			}

			// 打开链接
			conn = (Connection) DriverManager.getConnection(url, user, password);
			// 执行查询
			stmt = (Statement) conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sqlStr);
			// 获取结果集信息
			ResultSetMetaData resultSetMD = resultSet.getMetaData();
			// 总共多少条值
			List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
			while (resultSet.next()) {
				Map<String, String> map = new HashMap<String, String>();

				for (int i = 1; i < resultSetMD.getColumnCount() + 1; i++) {
					// 字段名
					String name = resultSetMD.getColumnName(i);
					// 值
					String value = resultSet.getObject(i) + "";
					if (value.equals("null")) {
						value = "";
					}
					// 处理时间尾缀问题
					if (name.contains("time")) {
						value = value.split("\\.")[0];
					}
					map.put(resultSetMD.getColumnName(i), value);
				}
				listData.add(map);
			}
			result.setMessage("查询执行成功,共查询到" + listData.size() + "条数据");
			// 是否获取单个值
			if (type.equals("list")) {
				return listData;
			} else {
				if (0 == listData.size()) {
					return null;
				} else {
					return listData.get(0);
				}
			}
		} catch (Exception e) {
			// 处理 Class.forName 错误
			System.err.println("数据源配置信息异常，需要进行配置=="+e.getMessage());

			// AllException.recordException(e);
			return null;
		} finally {
			// 关闭资源
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				System.err.println("数据源配置信息异常，需要进行配置");
				return null;
			} // 什么都不做
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				System.err.println("数据源配置信息异常，需要进行配置");
				return null;
			}
		}

	}

	/**
	 * @param sqlStr
	 *            [sql语句，增删改查共用]
	 * @param dataBase 数据库资料，不填写设为null则为默认
	 * @return 返回通过sql查询的结果集
	 */
	@Async
	public static int mysqlExecute(String sqlStr, DataBase dataBase) {
		Connection conn = null;
		Statement stmt = null;
		try {
			String url = "";
			String user = "";
			String password = "";
			String driverClass = "";
			if (null == dataBase||null == dataBase.getUrl()||"".equals(dataBase.getUrl())) {
				// 读取配置文件
				Map<String,String> props= ConfigManage.find("jdbc");
				if (null == props||props.size()==0) {
					url = "jdbc:mysql://127.0.0.1:3306/speedy?useUnicode=true&characterEncoding=utf8";
					driverClass = "com.mysql.cj.jdbc.Driver";
					user = "root";
					password = "root";
					Class.forName(driverClass);
				} else {
					// 加载文件
					// 读取信息
					url = props.get("url");
					user = props.get("username");
					password = props.get("password");
					driverClass = props.get("driverClassName");
					// 注册驱动程序
					Class.forName(driverClass);
				}
			} else {
				url = dataBase.getUrl();
				user = dataBase.getUsername();
				password = dataBase.getPassword();
				driverClass = dataBase.getDriverClass();
				// 注册驱动程序
				Class.forName(driverClass);
			}

			// 打开链接
			conn = (Connection) DriverManager.getConnection(url, user, password);
			// 执行查询
			stmt = (Statement) conn.createStatement();
			int rs = stmt.executeUpdate(sqlStr,Statement.RETURN_GENERATED_KEYS);
			if(sqlStr.contains("insert")){
                ResultSet rsKeys=stmt.getGeneratedKeys();
                if (rs > 0) {
                    if(rsKeys.next()){
                        return rsKeys.getInt(1);
                    } else{
                        return 0;
                    }
                } else {
                    return 0;
                }
            }else{
			    return rs;
            }

		} catch (Exception e) {
			// 处理 Class.forName 错误

			System.err.println("数据源配置信息异常，需要进行配置");
			e.printStackTrace();
			// AllException.recordException(e);
			return 0;
		} finally {
			// 关闭资源
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				System.err.println("数据源配置信息异常，需要进行配置");
			} // 什么都不做
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
