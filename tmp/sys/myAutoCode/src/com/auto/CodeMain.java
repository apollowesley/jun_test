package com.auto;

import java.io.IOException;
import java.sql.SQLException;

import com.auto.bean.CodeBean;
import com.auto.codeUtil.CodeUtil;
import com.auto.codeUtil.DbType;
import com.auto.util.PropertiesUtil;

/**
 * 自动生成mybatis所需的xml文件，包含分页查询、增、删、改、查、根据ID查询
 */
public class CodeMain {
	public static void main(String[] args) throws SQLException, IOException {
		System.out.println("columnName".toUpperCase());
		PropertiesUtil.getInstance();
		
		CodeBean param = new CodeBean();
		param.setDbType(DbType.mysql);//使用的数据库
		param.setBasePackage("net.jeeshop.services");//业务逻辑的根包
		param.setActionBasePackage("net.jeeshop.web.action");//action的根包
		param.setPath("d:\\123\\jeeshopweb");//代码生成的路径
		//表名称，多个表名称之间使用逗号分割
		param.setTabStr("t_product");//"t_superType,t_smallType,t_productType");
		
		//下面2个属性无需改动
		param.setQueryTableNameSql("queryTableSql");
		param.setQueryColSql("queryTableInfoSql");
		
		new CodeUtil().writeCodeToFile(param);
		System.out.println("\n文件生成路径:"+param.getPath());
	}
}
