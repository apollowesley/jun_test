package com.luoqy.speedy.util;

import java.util.List;
import java.util.Map;

import com.luoqy.speedy.data.MySqldbUtil;
/**
 *  结合实际sesion配合静态页面进行操作的工具类
 * */
public class SessionUtil {
	public static void main(String[] args) {
		List<Map<String,String>> handleList=(List<Map<String, String>>) MySqldbUtil.mysqlSelect("select * from speedy_menu", "list",null);
		boolean flag=hasHandel(handleList,"/system/users");
		System.out.println(flag);
	
	}
	@SuppressWarnings("unchecked")
	public static String getNameContext(String tablename,String name){
		Map<String,String> map=(Map<String, String>) MySqldbUtil.mysqlSelect("select column_name as name,COLUMN_COMMENT as context from information_schema.columns where table_name  ="+tablename+" and COLUMN_NAME="+name, "none",null);
		return map.get("COLUMN_COMMENT");
	}
	/**
	 * @param handleList 权限集合
	 * @param handleName 权限路径
	 * @return 判断是否存在权限
	 */
	public static boolean hasHandel(List<Map<String,String>> handleList,String handleName){
		for(int i=0;i<handleList.size();i++){
			//这里要判断当前权限是否在所在目录下
			if(handleName.equals(handleList.get(i).get("path"))){
				return true;
			}
		}
		
		return false;
	}
}
