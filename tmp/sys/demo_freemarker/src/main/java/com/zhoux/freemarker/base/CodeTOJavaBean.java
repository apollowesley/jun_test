package com.zhoux.freemarker.base;

import java.sql.ResultSetMetaData;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhoux.freemarker.bean.TableColumnBean;
import com.zhoux.freemarker.database.ConnectionDB;
import com.zhoux.freemarker.util.FreeMarkerUtil;
import com.zhoux.freemarker.util.ResultUtil;

/**
 * 
 * @author zhoux
 * @Date Apr 1, 2017
 * @Email zhoux@souche.com
 * @Desc
 */
public class CodeTOJavaBean extends codeBaseCon{

	private static final Logger logger =LoggerFactory.getLogger(CodeTOJavaBean.class);
	
	private String  templatePath="javaBean.ftl";

	@Override
	public boolean createJavaBean(String tableName, String codePath) {
		//获取表结构
		try {
			ResultSetMetaData rs = ConnectionDB.getTableColumn(tableName);
			logger.debug(tableName+"的表结构获取成功");
			java.util.List<TableColumnBean> fieldList = ResultUtil.dataTypeSwitch(rs);
			logger.debug("数据类型转换成功");
			//加载生成javabean的数据结构
			java.util.Map<String,Object> root=new HashMap<String,Object>();
			root.put("className", tableName.substring(0,1).toUpperCase()+tableName.substring(1));
			root.put("qualifiedName", getQualifiedName(codePath)+root.get("className").toString());
			root.put("fieldList", fieldList);
			root.put("date", (new Date()).toString());			
			FreeMarkerUtil.createFile(codePath, root, templatePath,".java");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Test
	public void runTest() {
		String string="a";
		System.out.println(string.substring(0,1).toUpperCase()+string.substring(1));
		
		createJavaBean("user", "/com/zhoux/demo");

	}
	/**
	 *  
	 * @param codePath /file/path
	 * @return
	 */
	private static String getQualifiedName(String codePath){
		String[] strs=codePath.split("/");
		StringBuilder sBuilder=new StringBuilder();
		for (String string : strs) {
			sBuilder.append(string).append(".");
		}
		String str=sBuilder.toString();
		int start=0;
		if(str.startsWith("."))
			start++;
		return str.substring(start);
	}

}
