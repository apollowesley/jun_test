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
		syscfg.put("DBConfigLocation", "DBConfig.properties");
		syscfg.put("log4jConfigLocation", "log4j.properties");  /* /WEB-INF/config/log4j.properties */
		syscfg.put("logbackConfigLocation", "logback.xml");  /* /WEB-INF/config/log4j.properties */
		syscfg.put("viewType", "velocity");
		syscfg.put("viewConfigLocation", "velocity.properties");  /* /WEB-INF/config/velocity.properties */

		syscfg.put("ehcacheConfigLocation", "ehcache.xml"); /* /WEB-INF/config/ehcache.xml */
		
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
		return get("webRoot");
	}
	public static String getDBType(){
		return get("DBType");
	}
	
	public static String getEncoding(){
		return get("encoding");
	}
	
	public static String getPageContext(){
		return get("pageContext");
	}
	
	public static String getPageSuffix(){
		return get("pageSuffix");
	}
	
	public static String getScanPackage(){
		return get("scanPackage");
	}
	
	public static String getHandler(){
		return get("handler");
	}
	
	public static String getDBConfigLocation(){
		return get("DBConfigLocation");
	}
	
	public static String getLog4jConfigLocation(){
		return get("log4jConfigLocation");
	}
	
	public static String getLogbackConfigLocation(){
		return get("logbackConfigLocation");
	}
	
	public static String getViewType(){
		return get("viewType");
	}
	
	public static String getViewConfigLocation(){
		return get("viewConfigLocation");
	}
	
	public static String getEhcacheConfigLocation(){
		return get("ehcacheConfigLocation");
	}
	
	public static String put(String key, String value){
		return syscfg.put(key, value);
	}
	
	public static String get(String key){
		return get(key, "");
	}
	
	public static String getProp(String key){
		return get(key,"");
	}
	
	public static String get(String key, String value) {
		String v = syscfg.get(key);
		return v == null ? value : v.trim();
	}
	
}
