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
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 映射数据库，自动生成Model
 * 
 * @author Xulu
 */
public class ModelGenerate {

	private static Connection conn = null;
	private static final String URL;
	private static final String JDBC_DRIVER;
	private static final String USER_NAME;
	private static final String PASSWORD;
	private static final String DATABASENAME;
	private static final String PACKAGENAME;
	public static Configuration configuration = null;
	static {
		DATABASENAME = "lublog";
		URL = "jdbc:mysql://localhost:3306/" + DATABASENAME;
		JDBC_DRIVER = "com.mysql.jdbc.Driver";
		USER_NAME = "root";
		PASSWORD = "xuluxu123";
		PACKAGENAME = "cc.xulu.model";
		
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
	 * @param tableName
	 * @return
	 */
	private static String CreateEntityString(String tableName) {
		String result = "";
//		String result = "package " + PACKAGENAME + ";\n\n";
//		result += "import com.jfinal.plugin.activerecord.Model; \n\n";
//		result += "public class "
//				+ StringUtil.toUpperCaseTheFristChar(tableName)
//				+ " extends Model<"
//				+ StringUtil.toUpperCaseTheFristChar(tableName) + ">{\n\n";
//		result += "    private static final long serialVersionUID = 1L;\n";
//		result += "    public static final "
//				+ StringUtil.toUpperCaseTheFristChar(tableName) + " dao = new "
//				+ StringUtil.toUpperCaseTheFristChar(tableName) + "();\n\n";
//		result += "    /**表名**/ \n";
//		result += "    public static String TABLE = \"" + tableName + "\";";
//		String sql = "select column_name from information_schema.columns where table_schema = 'yyerp' and table_name = '"
//				+ tableName + "';";
		try {
//			Statement statement = conn.createStatement();
//			ResultSet resultSet = statement.executeQuery(sql);
//			while (resultSet.next()) {
//				if (resultSet.getString(1) != null
//						&& !resultSet.getString(1).isEmpty()) {
//					String string = resultSet.getString(1);
//					String row = "    public static final String "
//							+ string.toUpperCase(Locale.CHINA) + " = \""
//							+ string + "\";";
//					String note = "    /****/";
//					result += "\n" + note + "\n" + row;
//				}
//			}
//			resultSet.close();
//			statement.close();
//			result += "\n }";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("packageName", PACKAGENAME);
			params.put("tableNameFirstUpper", StringUtil.toUpperCaseTheFristChar(tableName));
			params.put("tableName", tableName);
			params.put("foreigns", getAllForeigns(tableName));
			
			Template t = configuration.getTemplate("model.ftl");
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
	public static List<String> getAllTables() {
		String sql = "select table_name from information_schema.tables where table_schema = '"+DATABASENAME+"'";
		try {
			List<String> result = new ArrayList<String>();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				if (resultSet.getString(1) != null
						&& !resultSet.getString(1).isEmpty()) {
					result.add(resultSet.getString(1));
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获得数据库的所有外键
	 * 
	 * @return
	 */
	public static List<Map<String, String>> getAllForeigns(String tableName) {
		String sql = "SELECT substring(t1.ID,"+(DATABASENAME.length()+2)+") id,substring(t1.FOR_NAME,"+(DATABASENAME.length()+2)+") forName,t2.FOR_COL_NAME forColName,substring(t1.REF_NAME,"+(DATABASENAME.length()+2)+") refName,t2.REF_COL_NAME refColName "
					  + "FROM information_schema.INNODB_SYS_FOREIGN t1,information_schema.INNODB_SYS_FOREIGN_COLS t2 "
					  + "WHERE t1.ID = t2.ID AND t1.ID LIKE '"+DATABASENAME+"%' and t1.for_name = '"+DATABASENAME+"/" + tableName + "'";
		try {
			Map<String, String> m = null;
			List<Map<String, String>> result = new ArrayList<Map<String, String>>();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				m = new HashMap<String, String>();
				if (resultSet.getString(1) != null
						&& !resultSet.getString(1).isEmpty()) {
					m.put("forName", StringUtil.toUpperCaseTheFristChar(resultSet.getString(2)));
					m.put("forColName", resultSet.getString(3));
					m.put("refName", StringUtil.toUpperCaseTheFristChar(resultSet.getString(4)));
					m.put("refColName", resultSet.getString(5));
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
	 * 生成Entity
	 */
	public static void CreateEntity() {
		try {
			getConnection();
			List<String> tables = getAllTables();
			for (int i = 0; i < tables.size(); i++) {
				File createFolder = new File(System.getProperty("user.dir")
						+ "/src/" + PACKAGENAME.replace(".", "/"));
				// 路径不存在，生成文件夹
				if (!createFolder.exists()) {
					createFolder.mkdirs();
				}
				String entityString = CreateEntityString(tables.get(i).trim());
				File entity = new File(createFolder.getAbsolutePath() + "/"
						+ StringUtil.toUpperCaseTheFristChar(tables.get(i))
						+ ".java");
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
		CreateEntity();
	}

}