package com.luoqy.speedy.common;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * @author luoqy
 * @date 2019年6月2日 第一次登录创建数据内容
 */
public class FirstCreateBase {
    /**
     * 是否JAR包
     * */
    public static  boolean whetherJar(){
        try {
            String path=FirstCreateBase.class.getResource("/base.properties").getFile();
            if(path.contains(".jar")){
                //是jar包
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
	/**
	 *  对JAR包进行解压
	 * */
	public static void unlock(){
		try{
			Map<String,String> base=ConfigManage.findProperties("base");
			if("true".equals(base.get("first"))&&"false".equals(base.get("localhost"))){
				String os = System.getProperty("os.name").toLowerCase();
				String path=FirstCreateBase.class.getResource("/properties/path.properties").getPath();
				if(path.contains(".jar")){
					path=path.split(".jar")[0]+".jar";
				}
				path=path.replace("file:/","");
				System.err.println(path);
				if (os.startsWith("win")) {
					WindowsDos.runCMD("jar -xvf "+path);
				}else{
					LinuxDos.executeLinuxCmd("jar -xvf "+path);
				}
			}
		}catch (Exception e){

		}
	}
	/**
	 * 根据基础库创建集
	 */
	public static void createDatabase(Map<String, String> map) {
		try {
			Class.forName(map.get("driverClassName"));

			// 一开始必须填一个已经存在的数据库
			String url = "jdbc:mysql://127.0.0.1:3306/mysql?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
			Connection conn = DriverManager.getConnection(url, map.get("username"), map.get("password"));
			Statement stat = conn.createStatement();
			// 创建数据库speedy
			stat.executeUpdate("create database IF NOT EXISTS speedy DEFAULT CHARSET utf8 COLLATE utf8_general_ci");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 *  执行Mysql文件写入到数据库
	 * */
	public static boolean runSql(Map<String, String> dataBase) {
		try {
			// 获取数据库相关配置信息
			Map<String, String> props = dataBase;

			// jdbc 连接信息: 注: 现在版本的JDBC不需要配置driver，因为不需要Class.forName手动加载驱动
			String url = props.get("url");
			String username = props.get("username");
			String password = props.get("password");
			// 建立连接
			Connection conn = DriverManager.getConnection(url, username, password);
			 
			// 创建ScriptRunner，用于执行SQL脚本
			ScriptRunner runner = new ScriptRunner(conn);
			runner.setErrorLogWriter(null);
			runner.setLogWriter(null);
			// 执行SQL脚本Resources.getResourceAsReader("speedy.sql")
			runner.runScript(new InputStreamReader(FirstCreateBase.class.getResourceAsStream("/speedy.sql"), "utf-8"));
			// 若成功，打印提示信息
			System.out.println("====== SUCCESS ======");
			// 关闭连接
			conn.close();
			return true;
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * 第一次启动项目初始化数据库
	 */
	public static boolean initBase(Map<String,String> dataBae) {
		Map<String,String> baseProps=dataBae;
		dataBae.put("first","true");
		try {
			String first = baseProps.get("first");
			Map<String, String> map = new HashMap<String, String>();
			if (first.equals("true")) {
				// 如果第一次登录
				// MySqldbUtil.mysql("create table", type);
				// 根据基础库创建集
				createDatabase(dataBae);
				// 执行完毕后更改登录状态
				if(runSql(baseProps)){
						map.put("first", "false");
						map.put("localhost", "false");
						baseProps.remove("first");
					if(whetherJar()){
						//是JAR包的时候
                        ConfigManage.updatePropertie("base", map);
                        ConfigManage.updatePropertie("jdbc", baseProps);
                    }else{
						//非JAR包存在的本地化的时候
						ConfigManage.updateProp("base",map);
						ConfigManage.updateProp("jdbc",baseProps);
					}
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
			return false;
		}
	}
}
