package tom.cocook.config;

import java.util.HashMap;

public class Constants {
	
	private final static HashMap<String, String> syscfg = new HashMap<String, String>();

	static {
		syscfg.put("goback", "history.go(-1)");
		syscfg.put("goblank", "location='about:blank'");
		syscfg.put("exceptionClassPrefix", "");
		syscfg.put("exceptionClassPrefixNo", "");
		syscfg.put("maxPageRowCount", "5000");
		
		/************ 以上没有用到************************/
		
		syscfg.put("scanPackage", "");
		syscfg.put("handler", "");
		syscfg.put("DBType", "SQL");
		syscfg.put("DBConfigLocation", "/WEB-INF/config/DBConfig.properties");
		syscfg.put("log4jConfigLocation", "");  /* /WEB-INF/config/log4j.properties */
		syscfg.put("viewType", "velocity");
		syscfg.put("viewConfigLocation", "");  /* /WEB-INF/config/velocity.properties */

		syscfg.put("ehcacheConfigLocation", "/WEB-INF/config/ehcache.xml"); /* /WEB-INF/config/ehcache.xml */
		
		syscfg.put("encoding", "UTF-8");
		syscfg.put("webRoot", "");
		syscfg.put("pageContext", "/WEB-INF/page");
		syscfg.put("pageSuffix", ".htm");
	}
	
	public static HashMap<String, String> getSyscfg() {
		return syscfg;
	}
	

	public static String setWebRoot(String path){
		return syscfg.put("webRoot", path);
	}
	
	public static String getWebRoot(){
		return syscfg.get("webRoot");
	}
	public static String getDBType(){
		return syscfg.get("DBType");
	}
	
	public static String getEncoding(){
		return syscfg.get("encoding");
	}
	
	public static String getPageContext(){
		return syscfg.get("pageContext");
	}
	
	public static String getPageSuffix(){
		return syscfg.get("pageSuffix");
	}
	
	public static String getScanPackage(){
		return syscfg.get("scanPackage");
	}
	
	public static String getHandler(){
		return syscfg.get("handler");
	}
	
	public static String getDBConfigLocation(){
		return syscfg.get("DBConfigLocation");
	}
	
	public static String getLog4jConfigLocation(){
		return syscfg.get("log4jConfigLocation");
	}
	
	public static String getViewType(){
		return syscfg.get("viewType");
	}
	
	public static String getViewConfigLocation(){
		return syscfg.get("viewConfigLocation");
	}
	
	public static String getEhcacheConfigLocation(){
		return syscfg.get("ehcacheConfigLocation");
	}
	
}
