package com.zhoux.freemarker.util;

import java.util.List;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhoux.freemarker.bean.TableColumnBean;
import com.zhoux.freemarker.database.ConnectionDB;

/**
 * 
 * @author zhoux
 * @Date Mar 31, 2017
 * @Email zhoux@souche.com
 * @Desc 将获取的表结构，转换成freemarker能够识别的数据类型
 */
public class ResultUtil {
	
	private static final Logger logger =LoggerFactory.getLogger(ResultUtil.class);
	
	public static List<TableColumnBean> dataTypeSwitch(ResultSetMetaData resultSetMetaData) throws Exception{
		if (null==resultSetMetaData) {
			throw new Exception("结果集为null，无法转换！");
		}
		List<TableColumnBean> fieldList=new ArrayList<TableColumnBean>();
		for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
			TableColumnBean cBean=new TableColumnBean();
			cBean.setColumnName(resultSetMetaData.getColumnName(i));
			cBean.setColumnType(resultSetMetaData.getColumnClassName(i));
			logger.debug("ColumnName="+cBean.getColumnName()+" ;ColumnType="+cBean.getColumnType());
			cBean.setJavaName(nameFormat(cBean.getColumnName()));
			cBean.setJavaType(typeFormat(cBean.getColumnType()));
			fieldList.add(cBean);
		}
		return fieldList;
	}

	private static String typeFormat(String columnType) {
		// TODO Auto-generated method stub
		return columnType;
	}

	private static String nameFormat(String columnName) {
		if(!columnName.contains("_"))
			return columnName;
		String[] strs=columnName.split("_");
		StringBuilder stringBuilder=new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			if(i == 0){
				stringBuilder.append(strs[i]);continue;
			}
			stringBuilder.append(String.valueOf(strs[i].charAt(0)).toUpperCase()).append(strs[i].substring(1));
		}
		return stringBuilder.toString();
	}
	@Test
	public void runTest() throws Exception {
		ResultSetMetaData re = ConnectionDB.getTableColumn("user");
		dataTypeSwitch(re);
	}
	
	

}
