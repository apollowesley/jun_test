package com.auto;

import java.io.IOException;
import java.sql.SQLException;

import com.auto.bean.CodeBean;
import com.auto.codeUtil.CodeUtil;
import com.auto.codeUtil.DbType;
import com.auto.util.PropertiesUtil;

/**
 * �Զ�����mybatis�����xml�ļ���������ҳ��ѯ������ɾ���ġ��顢����ID��ѯ
 */
public class CodeMain {
	public static void main(String[] args) throws SQLException, IOException {
		System.out.println("columnName".toUpperCase());
		PropertiesUtil.getInstance();
		
		CodeBean param = new CodeBean();
		param.setDbType(DbType.mysql);//ʹ�õ����ݿ�
		param.setBasePackage("net.jeeshop.services");//ҵ���߼��ĸ���
		param.setActionBasePackage("net.jeeshop.web.action");//action�ĸ���
		param.setPath("d:\\123\\jeeshopweb");//�������ɵ�·��
		//�����ƣ����������֮��ʹ�ö��ŷָ�
		param.setTabStr("t_product");//"t_superType,t_smallType,t_productType");
		
		//����2����������Ķ�
		param.setQueryTableNameSql("queryTableSql");
		param.setQueryColSql("queryTableInfoSql");
		
		new CodeUtil().writeCodeToFile(param);
		System.out.println("\n�ļ�����·��:"+param.getPath());
	}
}
