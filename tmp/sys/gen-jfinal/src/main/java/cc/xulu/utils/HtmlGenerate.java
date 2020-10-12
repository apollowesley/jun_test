package cc.xulu.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 映射数据库，自动生成Controller
 * 
 * @author Xulu
 */
public class HtmlGenerate {

	private static Connection conn = null;
	private static final String URL;
	private static final String JDBC_DRIVER;
	private static final String USER_NAME;
	private static final String PASSWORD;
	private static final String DATABASENAME;
	private static final String PACKAGENAME;
	private static final String MODELPACKAGENAME;
	
	public static Configuration configuration = null;
	
	static {
		DATABASENAME = "lublog";
		URL = "jdbc:mysql://localhost:3306/" + DATABASENAME;
		JDBC_DRIVER = "com.mysql.jdbc.Driver";
		USER_NAME = "root";
		PASSWORD = "xuluxu123";
		PACKAGENAME = "cc.xulu.controller";
		MODELPACKAGENAME = "cc.xulu.model";
		
		configuration = new Configuration();
		try {
			String template = new File(System.getProperty("user.dir")+ "/template/").getPath();
			//设置模版所在文件夹
			configuration.setDirectoryForTemplateLoading(new File(template));
			configuration.setDefaultEncoding("UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库
	 */
	public static void closeConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成字段静态声明
	 * 
	 * @param map
	 * @param template
	 * @return
	 */
	private static String CreateHtmlString(Map<String, String> map, String template) {
		
		String result = "";
		try {
			Map<String, String> params = map;
			
			Template t = configuration.getTemplate(template);
			StringWriter writer = new StringWriter();
			t.process(params, writer);
			
			result = writer.getBuffer().toString();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 生成字段静态声明
	 * 
	 * @param map
	 * @param template
	 * @return
	 */
	private static String CreateHtmlListString(Map<String, Object> map, String template) {
		
		String result = "";
		try {
			Map<String, Object> params = map;
			
			Template t = configuration.getTemplate(template);
			StringWriter writer = new StringWriter();
			t.process(params, writer);
			
			result = writer.getBuffer().toString();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得数据库的所有表名
	 * 
	 * @return
	 */
	public static List<Map<String,String>> getAllTables() {
		String sql = "select table_name,table_comment from information_schema.tables where table_schema = '"+DATABASENAME+"'";
		Map<String,String> m = null;
		try {
			List<Map<String,String>> result = new ArrayList<Map<String,String>>();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				m = new HashMap<String,String>();
				if (resultSet.getString(1) != null
						&& !resultSet.getString(1).isEmpty()) {
					m.put("tableName", resultSet.getString(1));
					m.put("tableDesc", resultSet.getString(2));
					result.add(m);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获得数据库的所有表名
	 * 
	 * @return
	 */
	public static List<Map<String,String>> getAllColumns(String tableName) {
		String sql = "select column_name,column_comment from information_schema.columns where table_schema = '"+DATABASENAME+"' and column_name != 'id' and table_name = '"+tableName+"'";
		Map<String,String> m = null;
		try {
			List<Map<String,String>> result = new ArrayList<Map<String,String>>();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				m = new HashMap<String,String>();
				if (resultSet.getString(1) != null
						&& !resultSet.getString(1).isEmpty()) {
					m.put("columnName", resultSet.getString(1));
					m.put("columnDesc", resultSet.getString(2));
					result.add(m);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 生成Add页面
	 */
	public static void CreateHtmlAdd() {
		try {
			getConnection();
			List<Map<String,String>> tables = getAllTables();
			Map<String,String> m = null;
			for (int i = 0; i < tables.size(); i++) {
				m = tables.get(i);
				File createFolder = new File(System.getProperty("user.dir")
						+ "/src/main/webapp/" + m.get("tableName"));
				// 路径不存在，生成文件夹
				if (!createFolder.exists()) {
					createFolder.mkdirs();
				}
				String entityString = CreateHtmlString(m, "add.ftl");
				File entity = new File(createFolder.getAbsolutePath() + "/"
						+ "add.html");
				if (!entity.exists()) {
					// 写入文件
					BufferedWriter out = new BufferedWriter(new FileWriter(
							entity, true));
					out.write(entityString);
					out.close();
					out = null;
					entity = null;
				}
			}
			closeConnection();
			System.out.println("生成成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成Add页面
	 */
	public static void CreateHtmlEdit() {
		try {
			getConnection();
			List<Map<String,String>> tables = getAllTables();
			Map<String,String> m = null;
			for (int i = 0; i < tables.size(); i++) {
				m = tables.get(i);
				File createFolder = new File(System.getProperty("user.dir")
						+ "/src/main/webapp/" + m.get("tableName"));
				// 路径不存在，生成文件夹
				if (!createFolder.exists()) {
					createFolder.mkdirs();
				}
				String entityString = CreateHtmlString(m, "edit.ftl");
				File entity = new File(createFolder.getAbsolutePath() + "/"
						+ "edit.html");
				if (!entity.exists()) {
					// 写入文件
					BufferedWriter out = new BufferedWriter(new FileWriter(
							entity, true));
					out.write(entityString);
					out.close();
					out = null;
					entity = null;
				}
			}
			closeConnection();
			System.out.println("生成成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成Add页面
	 */
	public static void CreateHtmlList() {
		try {
			getConnection();
			List<Map<String,String>> tables = getAllTables();
			Map<String,String> m = null;
			Map<String,Object> params = new HashMap<String,Object>();
			for (int i = 0; i < tables.size(); i++) {
				m = tables.get(i);
				File createFolder = new File(System.getProperty("user.dir")
						+ "/src/main/webapp/" + m.get("tableName"));
				// 路径不存在，生成文件夹
				if (!createFolder.exists()) {
					createFolder.mkdirs();
				}
				params.put("tableName", m.get("tableName"));
				params.put("tableDesc", m.get("tableDesc"));
				params.put("columns", getAllColumns(m.get("tableName")));
				String entityString = CreateHtmlListString(params, "list.ftl");
				File entity = new File(createFolder.getAbsolutePath() + "/"
						+ "list.html");
				if (!entity.exists()) {
					// 写入文件
					BufferedWriter out = new BufferedWriter(new FileWriter(
							entity, true));
					out.write(entityString);
					out.close();
					out = null;
					entity = null;
				}
			}
			closeConnection();
			System.out.println("生成成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		CreateHtmlAdd();
		CreateHtmlEdit();
		CreateHtmlList();
	}

}