package com.auto.codeUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.auto.bean.CodeBean;
import com.auto.dbTools.DBQueryUtil;
import com.auto.io.WriteFileTools;
import com.auto.util.CodeChar;

@SuppressWarnings("all")
public class CodeUtil {
	private DBQueryUtil dbutil = new DBQueryUtil();
	private WriteFileTools fileUtil = new WriteFileTools();
	private CodeConfig codeConfig = new CodeConfig();
	private String beanStr;
	private String tableName;
	
	public void writeCodeToFile(CodeBean param)  {
		String[] tabArr = check(param);
		try {
			fileUtil.createFile(param.getPath());
			for (int i = 0; i < tabArr.length; i++) {
				List listTabCol = dbutil.queryColsByTableName(tabArr[i].toUpperCase(),
						param.getQueryTableNameSql(), param.getQueryColSql(),param);
				if (listTabCol == null || listTabCol.size() == 0) {
					throw new NullPointerException("表："+tabArr[i] +",无任何列!");
				}
				
				//生成JAVA文件
				tableName = tabArr[i].substring(2);//去掉t_前缀的表名称，表名最好要规范t_user这样的
				String tableName2 = tableName.substring(0,1).toUpperCase()+tableName.substring(1);
				String basePackage = param.getBasePackage();
				basePackage += "."+tableName;//net.jeeshop.services.lable
				
				String actionBasePackage = param.getActionBasePackage() + "."+tableName;//net.jeeshop.web.action
				String basePackageFilepath = param.getPath() +File.separator+ basePackage.replaceAll("\\.", "\\\\");
				String actionBasePackageFilepath = param.getPath() +File.separator+ actionBasePackage.replaceAll("\\.", "\\\\");
				String beanPackage = basePackage+".bean;";
				String daoPackage = basePackage+".dao";
				String implPackage = basePackage+".impl";
				String daoimplPackage = basePackage+".dao.impl";
				String java = ".java";
				String _r = "\r";
				/**
				 * 写LableService
				 */
				String LableService = basePackageFilepath+File.separator+tableName2+"Service"+java;
				StringBuilder serverBuff = new StringBuilder();
				serverBuff.append("package net.jeeshop.services."+tableName+";"+_r);
				serverBuff.append("import net.jeeshop.core.Services;"+_r);
				serverBuff.append("import net.jeeshop.services."+tableName+".bean."+tableName2+";"+_r);
				serverBuff.append("public interface "+tableName2+"Service extends Services<"+tableName2+"> {"+_r);
				serverBuff.append("}"+_r);
				fileUtil.writeJava(LableService, serverBuff.toString());
				
				/**
				 * 写LableServiceImpl
				 */
				String LableServiceImpl = basePackageFilepath+File.separator+"impl"+File.separator+tableName2+"ServiceImpl"+java;
				StringBuilder serverImplBuff = new StringBuilder();
				serverImplBuff.append("package net.jeeshop.services."+tableName+".impl;"+_r);
				serverImplBuff.append("import net.jeeshop.core.ServersManager;"+_r);
				serverImplBuff.append("import net.jeeshop.services."+tableName+"."+tableName2+"Service;"+_r);
				serverImplBuff.append("import net.jeeshop.services."+tableName+".bean."+tableName2+";"+_r);
				serverImplBuff.append("import net.jeeshop.services."+tableName+".dao."+tableName2+"Dao;"+_r);
				serverImplBuff.append("public class "+tableName2+"ServiceImpl extends ServersManager<"+tableName2+"> implements "+tableName2+"Service {"+_r);
				serverImplBuff.append("private "+tableName2+"Dao "+tableName+"Dao;"+_r);
				serverImplBuff.append("public void set"+tableName2+"Dao("+tableName2+"Dao "+tableName+"Dao) {"+_r);
				serverImplBuff.append("this."+tableName+"Dao = "+tableName+"Dao;"+_r);
				serverImplBuff.append("}}"+_r);
				fileUtil.writeJava(LableServiceImpl, serverImplBuff.toString());
				
				/**
				 * 写LableDao
				 */
				String LableDao = basePackageFilepath+File.separator+"dao"+File.separator+tableName2+"Dao"+java;
				StringBuilder daoBuff = new StringBuilder();
				daoBuff.append("package net.jeeshop.services."+tableName+".dao;"+_r);
				daoBuff.append("import net.jeeshop.core.DaoManager;"+_r);
				daoBuff.append("import net.jeeshop.services."+tableName+".bean."+tableName2+";"+_r);
				daoBuff.append("public interface "+tableName2+"Dao extends DaoManager<"+tableName2+"> {"+_r);
				daoBuff.append("}"+_r);
				fileUtil.writeJava(LableDao, daoBuff.toString());
				
				/**
				 * 写LableDaoImpl
				 */
				String LableDaoImpl = basePackageFilepath+File.separator+"dao"+File.separator+"impl"+File.separator+tableName2+"DaoImpl"+java;
				StringBuilder daoImplBuff = new StringBuilder();
				daoImplBuff.append("package net.jeeshop.services."+tableName+".dao.impl;"+_r);
				daoImplBuff.append("import java.util.List;"+_r);
				daoImplBuff.append("import net.jeeshop.core.dao.BaseDao;"+_r);
				daoImplBuff.append("import net.jeeshop.core.dao.page.PagerModel;"+_r);
				daoImplBuff.append("import net.jeeshop.services."+tableName+".bean."+tableName2+";"+_r);
				daoImplBuff.append("import net.jeeshop.services."+tableName+".dao."+tableName2+"Dao;"+_r);
				daoImplBuff.append("public class "+tableName2+"DaoImpl implements "+tableName2+"Dao {"+_r);
				daoImplBuff.append("private BaseDao dao;"+_r);
				daoImplBuff.append("public void setDao(BaseDao dao) {"+_r);
				daoImplBuff.append("this.dao = dao;"+_r);
				daoImplBuff.append("}"+_r);
				daoImplBuff.append("public PagerModel selectPageList("+tableName2+" e) {"+_r);
				daoImplBuff.append("return dao.selectPageList(\""+tableName+".selectPageList\",\""+tableName+".selectPageCount\", e);"+_r);
				daoImplBuff.append("}"+_r);
				daoImplBuff.append("public List selectList("+tableName2+" e) {"+_r);
				daoImplBuff.append("return dao.selectList(\""+tableName+".selectList\", e);"+_r);
				daoImplBuff.append("}"+_r);
				daoImplBuff.append("public "+tableName2+" selectOne("+tableName2+" e) {"+_r);
				daoImplBuff.append("return ("+tableName2+") dao.selectOne(\""+tableName+".selectOne\", e);"+_r);
				daoImplBuff.append("}"+_r);
				daoImplBuff.append("public int delete("+tableName2+" e) {"+_r);
				daoImplBuff.append("return dao.delete(\""+tableName+".delete\", e);"+_r);
				daoImplBuff.append("}"+_r);
				daoImplBuff.append("public int update("+tableName2+" e) {"+_r);
				daoImplBuff.append("return  dao.update(\""+tableName+".update\", e);"+_r);
				daoImplBuff.append("}"+_r);
				daoImplBuff.append("public int deletes(String[] ids) {"+_r);
				daoImplBuff.append(""+tableName2+" e = new "+tableName2+"();"+_r);
				daoImplBuff.append("for (int i = 0; i < ids.length; i++) {"+_r);
				daoImplBuff.append("e.setId(ids[i]);"+_r);
				daoImplBuff.append("delete(e);"+_r);
				daoImplBuff.append("}"+_r);
				daoImplBuff.append("return 0;"+_r);
				daoImplBuff.append("}"+_r);
				daoImplBuff.append("public int insert("+tableName2+" e) {"+_r);
				daoImplBuff.append("return dao.insert(\""+tableName+".insert\", e);"+_r);
				daoImplBuff.append("}"+_r);
				daoImplBuff.append("public int deleteById(int id) {"+_r);
				daoImplBuff.append("return dao.delete(\""+tableName+".deleteById\",id);"+_r);
				daoImplBuff.append("}"+_r);
				daoImplBuff.append("}"+_r);
				fileUtil.writeJava(LableDaoImpl, daoImplBuff.toString());
				
				/**
				 * 写Lable
				 */
				String beanFilepath = basePackageFilepath+File.separator+"bean"+File.separator+tableName2+java;//bean的全路径
				StringBuilder beanBuff = new StringBuilder();
				beanBuff.append("package "+beanPackage+_r);
				beanBuff.append("import java.io.Serializable;"+_r);
				beanBuff.append("import net.jeeshop.core.dao.page.PagerModel;"+_r);
				beanBuff.append("public class "+tableName2+" extends PagerModel implements Serializable {"+_r);
				beanBuff.append("private static final long serialVersionUID = 1L;"+_r);
				StringBuilder beanSubBuff = new StringBuilder();
				for(int j=0;j<listTabCol.size();j++){
					Object e = listTabCol.get(j);
					String colName = ((Map) e).get("columnName".toUpperCase()).toString();
					String dataType = ((Map) e).get("DATATYPE").toString();
					if(dataType.indexOf("int")!=-1){
						beanBuff.append("private int "+colName+";"+_r);
						beanSubBuff.append(colName+"= 0;"+_r);
					}else{
						beanBuff.append("private String "+colName+";"+_r);
						beanSubBuff.append(colName+"= null;"+_r);
					}
				}
				
				beanBuff.append("public void clear() {"+_r);
				beanBuff.append("super.clear();"+_r);
				beanBuff.append(beanSubBuff.toString()+_r);
				beanBuff.append("}"+_r);
				beanBuff.append("}"+_r);
				fileUtil.writeJava(beanFilepath, beanBuff.toString());
				//写Lable
//				writeBean(i, listTabCol, tableName2, basePackageFilepath, beanPackage,
//						java, _r);
				
				
				/**
				 * 写LableAction
				 */
				String LableAction = actionBasePackageFilepath+File.separator+tableName2+"Action"+java;
				StringBuilder actionBuff = new StringBuilder();
				actionBuff.append("package net.jeeshop.web.action."+tableName+";"+_r);
				actionBuff.append("import net.jeeshop.services."+tableName+"."+tableName2+"Service;"+_r);
				actionBuff.append("import net.jeeshop.services."+tableName+".bean."+tableName2+";"+_r);
				actionBuff.append("import net.jeeshop.web.action.system.BaseAction;"+_r);
				actionBuff.append("public class "+tableName2+"Action extends BaseAction<"+tableName2+"> {"+_r);
				actionBuff.append("private static final long serialVersionUID = 1L;"+_r);
				actionBuff.append("private "+tableName2+"Service "+tableName+"Service;"+_r);
				actionBuff.append("public "+tableName2+"Service get"+tableName2+"Service() {"+_r);
				actionBuff.append("return "+tableName+"Service;"+_r);
				actionBuff.append("}"+_r);
				actionBuff.append("protected void selectListAfter() {"+_r);
				actionBuff.append("pager.setPagerUrl(\""+tableName+"!selectList.action\");"+_r);
				actionBuff.append("}"+_r);
				actionBuff.append("public void set"+tableName2+"Service("+tableName2+"Service "+tableName+"Service) {"+_r);
				actionBuff.append("this."+tableName+"Service = "+tableName+"Service;"+_r);
				actionBuff.append("}"+_r);
				actionBuff.append("public "+tableName2+" getE() {"+_r);
				actionBuff.append("return this.e;"+_r);
				actionBuff.append("}"+_r);
				actionBuff.append("public void prepare() throws Exception {"+_r);
				actionBuff.append("if (this.e == null) {this.e = new "+tableName2+"();}"+_r);
				actionBuff.append("}"+_r);
				actionBuff.append("public void insertAfter("+tableName2+" e) {"+_r);
				actionBuff.append("e.clear();"+_r);
				actionBuff.append("}"+_r);
				actionBuff.append("}"+_r);
				fileUtil.writeJava(LableAction, actionBuff.toString());
				
				
				beanStr = basePackage+".bean."+tableName2;
				param.setTableName(tabArr[i].toLowerCase());
				fileUtil.write(param,convertToXml(param, listTabCol));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//释放数据库资源
			dbutil.destory();
		}
	}
	/**
	 * @param i
	 * @param listTabCol
	 * @param tableName2
	 * @param basePackage
	 * @param beanPackage
	 * @param java
	 * @param _r
	 * @throws IOException
	 */
	private void writeBean(int i, List listTabCol, String tableName2,
			String basePackageFilepath, String beanPackage, String java, String _r)
			throws IOException {
		
	}
	private String[] check(CodeBean param){
		if(param.getPath()==null || param.getPath().equals("")
				|| param.getQueryTableNameSql()==null 
				||param.getQueryTableNameSql().equals("")
				|| param.getQueryColSql()==null 
				|| param.getQueryColSql().equals("")
				|| param.getTabStr()==null || param.getTabStr().equals("")){
			throw new IllegalArgumentException("传递的参数不正确!");
		}
		String[] tabArr = param.getTabStr().split(",");
		if(tabArr==null || tabArr.length==0){
			throw new IllegalArgumentException("没有任何表生成!");
		}
		return tabArr;
	}
	//将列转换成对应的mybatis格式的xml文件,实现曾删改查以及分页的代码生成
	private List convertToXml(CodeBean param,List listTabCol){
		List list = new ArrayList();
		list.addAll(myBatisXmlHead(param));
		list.addAll(queryPage(param, listTabCol));
		list.addAll(insertStr(param, listTabCol));
		list.addAll(updateStr(param, listTabCol));
		list.addAll(queryByIdStr(param, listTabCol));
		list.addAll(deleteStr(param, listTabCol));
		list.addAll(myBatisXmlTail());
//		for (int i = 0; i < list.size(); i++) {
//			list.set(i, list.get(i).toString().toLowerCase());
//		}
//		list.addAll(datagridMxmlCode(param, listTabCol));
//		list.addAll(gridMxmlCode(param, listTabCol));
//		list.addAll(JSON(param, listTabCol));
//		list.addAll(setValueByJson(param, listTabCol));
		return list;
	}
	
	private List myBatisXmlHead(CodeBean param){
		List headList = new ArrayList();
		headList.add("<?xml version=\"1.0\" encoding=\""+codeConfig.getEncode().trim()+"\"?>");
		headList.add("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
		headList.add("<!-- create by autoCode,"+CodeConfig.version+" -->");
		headList.add("<mapper namespace=\""+tableName+"\">");
		return headList;
	}
	private List myBatisXmlTail(){
		List tailList = new ArrayList();
		tailList.add("</mapper>");
		return tailList;
	}
	
	//分页查询
	private List queryPage(CodeBean param,List listTabCol){
		List queryByIdList = new ArrayList();
		if(param.getDbType().equals(DbType.oracle)){
			queryByIdList.add(CodeChar.space1+"<select id=\""+param.getTableName()+
					".queryById\" parameterType=\""+beanStr+"\" resultType=\"hashmap\">");
			queryByIdList.add("SELECT /*+ FIRST_ROWS */ * FROM (SELECT inrow.*, ROWNUM RN FROM (");
			queryByIdList.add(CodeChar.space1+" select ");
			boolean isFirst = true;
			String colName = "";
			List<String> conditionList = new ArrayList<String>();
			for (int i = 0; i < listTabCol.size(); i++) {
				colName = ((Map) listTabCol.get(i)).get("columnName".toUpperCase()).toString();
				String dataType = ((Map) listTabCol.get(i)).get("DATATYPE").toString();
				String colDateStr = "t."+colName;
				if(dataType.equals("DATE")){
					colDateStr = "to_char(t."+colName+",\'yyyy-mm-dd hh24:mi:ss\')";
					conditionList.add("<if test=\""+colName+"!=null and "+colName+"!=''\">");
					conditionList.add("and t."+colName+"&gt;=to_date(#{"+colName+"},'yyyy-mm-dd hh24:mi:ss')");
//				MessageFormat.format("", arguments)
					conditionList.add("</if>");
				}else if(dataType.equals("VARCHAR2")){
					conditionList.add("<if test=\""+colName+"!=null and "+colName+"!=''\">");
					conditionList.add("and t."+colName+"=#{"+colName+"}");
//				MessageFormat.format("", arguments)
					conditionList.add("</if>");
				}
				
				if(codeConfig.getLowerOrUpper().equals("2")){
					queryByIdList.add(CodeChar.space2+colDateStr+" \""+colName.toUpperCase()+"\",");
				}else if(codeConfig.getLowerOrUpper().equals("3")){
					queryByIdList.add(CodeChar.space2+colDateStr+" \""+colName+"\",");
				}else{
					//默认小写
					queryByIdList.add(CodeChar.space2+colDateStr+" \""+colName.toLowerCase()+"\",");
					//throw new Exception("NND，请指定 查询列的大小写显示方式。");
				}
			}
			String lastStr = queryByIdList.get(queryByIdList.size()-1).toString();
			lastStr = lastStr.substring(0,lastStr.length()-1);
			queryByIdList.remove(queryByIdList.size()-1);
			queryByIdList.add(lastStr);
			
			queryByIdList.add(CodeChar.space1+" from "+param.getTableName()+" t where 1=1 ");
			queryByIdList.addAll(conditionList);
			queryByIdList.add(")  inrow WHERE ROWNUM &lt;= #{toRow} )WHERE RN &gt; #{fromRow}");
			queryByIdList.add(CodeChar.space1+"</select>");
			queryByIdList.add(" ");
		}else if(param.getDbType().equals(DbType.mysql)){
			queryByIdList.add(CodeChar.space1+"<select id=\""+tableName+
					".selectPageList\" parameterType=\""+beanStr+"\"" +" resultType=\""+beanStr+"\">");
//			queryByIdList.add("SELECT /*+ FIRST_ROWS */ * FROM (SELECT inrow.*, ROWNUM RN FROM (");
			queryByIdList.add(CodeChar.space1+" select ");
			boolean isFirst = true;
			String colName = "";
			List<String> conditionList = new ArrayList<String>();
			for (int i = 0; i < listTabCol.size(); i++) {
				colName = ((Map) listTabCol.get(i)).get("columnName".toUpperCase()).toString();
				String dataType = ((Map) listTabCol.get(i)).get("DATATYPE").toString();
				String colDateStr = "t."+colName;
				if(dataType.equals("DATE")){
					colDateStr = "to_char(t."+colName+",\'yyyy-mm-dd hh24:mi:ss\')";
					conditionList.add("<if test=\""+colName+"!=null and "+colName+"!=''\">");
					conditionList.add("and t."+colName+"&gt;=to_date(#{"+colName+"},'yyyy-mm-dd hh24:mi:ss')");
//				MessageFormat.format("", arguments)
					conditionList.add("</if>");
				}else if(dataType.equals("VARCHAR2")){
					conditionList.add("<if test=\""+colName+"!=null and "+colName+"!=''\">");
					conditionList.add("and t."+colName+"=#{"+colName+"}");
//				MessageFormat.format("", arguments)
					conditionList.add("</if>");
				}
				
				if(codeConfig.getLowerOrUpper().equals("2")){
					queryByIdList.add(CodeChar.space2+colDateStr+" \""+colName.toUpperCase()+"\",");
				}else if(codeConfig.getLowerOrUpper().equals("3")){
					queryByIdList.add(CodeChar.space2+colDateStr+" \""+colName+"\",");
				}else{
					//默认小写
					queryByIdList.add(CodeChar.space2+colDateStr+" \""+colName.toLowerCase()+"\",");
					//throw new Exception("NND，请指定 查询列的大小写显示方式。");
				}
			}
			String lastStr = queryByIdList.get(queryByIdList.size()-1).toString();
			lastStr = lastStr.substring(0,lastStr.length()-1);
			queryByIdList.remove(queryByIdList.size()-1);
			queryByIdList.add(lastStr);
			
			queryByIdList.add(CodeChar.space1+" from "+param.getTableName()+" t where 1=1 ");
			queryByIdList.addAll(conditionList);
			queryByIdList.add(" order by id desc ");
			queryByIdList.add(" limit #{offset},#{pageSize} ");
//			queryByIdList.add(")  inrow WHERE ROWNUM &lt;= #{toRow} )WHERE RN &gt; #{fromRow}");
			queryByIdList.add(CodeChar.space1+"</select>");
			queryByIdList.add(" ");
			
			queryByIdList.addAll(queryPageCount(param, listTabCol));
		}
		return queryByIdList;
	}
	
	private List queryPageCount(CodeBean param,List listTabCol){
		List queryByIdList = new ArrayList();
		queryByIdList.add(CodeChar.space1+"<select id=\""+tableName+
				".selectPageCount\" parameterType=\""+beanStr+"\"" +" resultType=\"java.lang.Integer\">");
//		queryByIdList.add("SELECT /*+ FIRST_ROWS */ * FROM (SELECT inrow.*, ROWNUM RN FROM (");
		queryByIdList.add(CodeChar.space1+" select count(*) from (select 1 " + CodeChar.space1+" from "+param.getTableName()+" t where 1=1 ");
		boolean isFirst = true;
		String colName = "";
		List<String> conditionList = new ArrayList<String>();
//		for (int i = 0; i < listTabCol.size(); i++) {
//			colName = ((Map) listTabCol.get(i)).get("columnName".toUpperCase()).toString();
//			String dataType = ((Map) listTabCol.get(i)).get("DATATYPE").toString();
//			String colDateStr = "t."+colName;
//			if(dataType.equals("DATE")){
//				colDateStr = "to_char(t."+colName+",\'yyyy-mm-dd hh24:mi:ss\')";
//				conditionList.add("<if test=\""+colName+"!=null and "+colName+"!=''\">");
//				conditionList.add("and t."+colName+"&gt;=to_date(#{"+colName+"},'yyyy-mm-dd hh24:mi:ss')");
////			MessageFormat.format("", arguments)
//				conditionList.add("</if>");
//			}else if(dataType.equals("VARCHAR2")){
//				conditionList.add("<if test=\""+colName+"!=null and "+colName+"!=''\">");
//				conditionList.add("and t."+colName+"=#{"+colName+"}");
////			MessageFormat.format("", arguments)
//				conditionList.add("</if>");
//			}
//			
//			if(codeConfig.getLowerOrUpper().equals("2")){
//				queryByIdList.add(CodeChar.space2+colDateStr+" \""+colName.toUpperCase()+"\",");
//			}else if(codeConfig.getLowerOrUpper().equals("3")){
//				queryByIdList.add(CodeChar.space2+colDateStr+" \""+colName+"\",");
//			}else{
//				//默认小写
//				queryByIdList.add(CodeChar.space2+colDateStr+" \""+colName.toLowerCase()+"\",");
//				//throw new Exception("NND，请指定 查询列的大小写显示方式。");
//			}
//		}
//		String lastStr = queryByIdList.get(queryByIdList.size()-1).toString();
//		lastStr = lastStr.substring(0,lastStr.length()-1);
//		queryByIdList.remove(queryByIdList.size()-1);
//		queryByIdList.add("1");//lastStr);
		
//		queryByIdList.add(CodeChar.space1+" from "+param.getTableName()+" t where 1=1 ");
		queryByIdList.addAll(conditionList);
//		queryByIdList.add(" order by id desc ");
//		queryByIdList.add(" limit #{offset},#{pageSize} ");
//		queryByIdList.add(")  inrow WHERE ROWNUM &lt;= #{toRow} )WHERE RN &gt; #{fromRow}");
		queryByIdList.add(CodeChar.space1+" ) a ");
		queryByIdList.add(CodeChar.space1+"</select>");
		queryByIdList.add(" ");
		return queryByIdList;
	}
	private List queryByIdStr(CodeBean param,List listTabCol){
		List queryByIdList = new ArrayList();
		queryByIdList.add(CodeChar.space1+"<select id=\""+tableName+
				".selectOne\" parameterType=\""+beanStr+"\""+" resultType=\""+beanStr+"\">");
		queryByIdList.add(CodeChar.space1+" select ");
		boolean isFirst = true;
		String colName = "";
		List<String> conditionList = new ArrayList<String>();
		for (int i = 0; i < listTabCol.size(); i++) {
			colName = ((Map) listTabCol.get(i)).get("columnName".toUpperCase()).toString();
			String dataType = ((Map) listTabCol.get(i)).get("DATATYPE").toString();
			String colDateStr = "t."+colName;
			if(dataType.equals("DATE")){
				colDateStr = "to_char(t."+colName+",\'yyyy-mm-dd hh24:mi:ss\')";
				conditionList.add("<if test=\""+colName+"!=null and "+colName+"!=''\">");
				conditionList.add("and t."+colName+"&gt;=to_date(#{"+colName+"},'yyyy-mm-dd hh24:mi:ss')");
//				MessageFormat.format("", arguments)
				conditionList.add("</if>");
			}else if(dataType.equals("VARCHAR2")){
				conditionList.add("<if test=\""+colName+"!=null and "+colName+"!=''\">");
				conditionList.add("and t."+colName+"=#{"+colName+"}");
//				MessageFormat.format("", arguments)
				conditionList.add("</if>");
			}
			
			if(codeConfig.getLowerOrUpper().equals("2")){
				queryByIdList.add(CodeChar.space2+colDateStr+" \""+colName.toUpperCase()+"\",");
			}else if(codeConfig.getLowerOrUpper().equals("3")){
				queryByIdList.add(CodeChar.space2+colDateStr+" \""+colName+"\",");
			}else{
				//默认小写
				queryByIdList.add(CodeChar.space2+colDateStr+" \""+colName.toLowerCase()+"\",");
				//throw new Exception("NND，请指定 查询列的大小写显示方式。");
			}
		}
		String lastStr = queryByIdList.get(queryByIdList.size()-1).toString();
		lastStr = lastStr.substring(0,lastStr.length()-1);
		queryByIdList.remove(queryByIdList.size()-1);
		queryByIdList.add(lastStr);
		
		queryByIdList.add(CodeChar.space1+" from "+param.getTableName()+" t where 1=1 and id=#{id}");
		queryByIdList.addAll(conditionList);
		queryByIdList.add(CodeChar.space1+"</select>");
		return queryByIdList;
	}
	private List insertStr(CodeBean param,List listTabCol){
		List insertList = new ArrayList();
		//id=表名.insert
		insertList.add(CodeChar.space1+"<insert id=\""+tableName+".insert\" parameterType=\""+beanStr+"\" useGeneratedKeys=\"true\" keyProperty=\"id\">");
		insertList.add(CodeChar.space2+"insert into "+param.getTableName());
		insertList.add(CodeChar.space2+"(");
		boolean isFirst = true;
		String colName = "";
		for (int i = 0; i < listTabCol.size(); i++) {
			colName = ((Map) listTabCol.get(i)).get("columnName".toUpperCase()).toString();
			String dataType = ((Map) listTabCol.get(i)).get("DATATYPE").toString();
			if(isFirst){
				insertList.add(CodeChar.space2+colName);
				isFirst = false;
			}else{
				if(dataType.indexOf("int")==-1){
					insertList.add(CodeChar.space2+"<if test=\""+colName+"!=null and "+colName+"!=''\">");
					insertList.add(CodeChar.space2+","+colName);
					insertList.add(CodeChar.space2+"</if>");
				}else{
					insertList.add(CodeChar.space2+"<if test=\""+colName+"!=0\">");
					insertList.add(CodeChar.space2+","+colName);
					insertList.add(CodeChar.space2+"</if>");
				}
			}
		}
		insertList.add(CodeChar.space2+")");
		insertList.add(CodeChar.space2+"values");
		insertList.add(CodeChar.space2+"(");
		isFirst = true;
		for (int i = 0; i < listTabCol.size(); i++) {
			colName = ((Map) listTabCol.get(i)).get("columnName".toUpperCase()).toString();
			if(isFirst){
				insertList.add(CodeChar.space2+colName);
				isFirst = false;
			}else{
				String dataType = ((Map) listTabCol.get(i)).get("DATATYPE").toString();
				if(dataType.indexOf("int")==-1){
					insertList.add(CodeChar.space2+"<if test=\""+colName+" !=null and "+colName+" !=''\">");
				}else{
					insertList.add(CodeChar.space2+"<if test=\""+colName+" !=0\">");
				}
				if(dataType.equals("DATE")){
					insertList.add(CodeChar.space2+",to_date(#{"+colName+"},'yyyy-mm-dd hh24:mi:ss')");
				} else{
					insertList.add(CodeChar.space2+",#{"+colName+"}");
				}
				insertList.add(CodeChar.space2+"</if>");
			}
		}
		insertList.add(CodeChar.space1+")</insert>");
		return insertList;
	}
	
	private List queryPageStr(CodeBean param,List listTabCol){
		return null;
	}
	
	private List updateStr(CodeBean param,List listTabCol){
		List updateList = new ArrayList();
		//id=表名.update
		updateList.add(CodeChar.space1+"<update id=\""+tableName+".update\" parameterType=\""+beanStr+"\">");
		updateList.add(CodeChar.space2+"update "+param.getTableName());
		boolean isFirst = true;
		String colName = "",firstColName="";
		for (int i = 0; i < listTabCol.size(); i++) {
			colName = ((Map) listTabCol.get(i)).get("columnName".toUpperCase()).toString();
			if(isFirst){
				firstColName = colName;
				updateList.add(CodeChar.space2+"  set "+colName+"=#{"+colName+"}");
				isFirst = false;
			}else{
				String dataType = ((Map) listTabCol.get(i)).get("DATATYPE").toString();
				
				if(dataType.indexOf("int")==-1){
					updateList.add(CodeChar.space2+"<if test=\""+colName+" !=null and "+colName+" !=''\">");
				}else{
					updateList.add(CodeChar.space2+"<if test=\""+colName+" !=0\">");
				}
				
				if(dataType.equals("DATE")){
					updateList.add(CodeChar.space2+","+colName+"=to_date(#{"+colName+"},'yyyy-mm-dd hh24:mi:ss')");
				}else{
					updateList.add(CodeChar.space2+","+colName+"=#{"+colName+"}");
				}
				updateList.add(CodeChar.space2+"</if>");
			}
		}
		updateList.add(CodeChar.space2+" where 1=1 and "+firstColName+"=#{"+firstColName+"}");
		updateList.add(CodeChar.space1+"</update>");
		return updateList;
	}
	
	private List deleteStr(CodeBean param,List listTabCol){
		List deleteList = new ArrayList();
		deleteList.add(CodeChar.space1+"<delete id=\""+tableName+".deleteById\" parameterType=\""+beanStr+"\">");
		deleteList.add(CodeChar.space2+"delete from "+param.getTableName()+" where 1=1 and id=#{id}");
		deleteList.add(CodeChar.space1+"</delete>");
		return deleteList;
	}
	//生成DataGrid
	private List datagridMxmlCode(CodeBean param,List listTabCol){
		List list = new ArrayList();
		list.add("");
		list.add("");
		list.add("[一下是mxml代码]：");
		String comments = "";
		for (int i = 0; i < listTabCol.size(); i++) {
			String colName = ((Map) listTabCol.get(i)).get("columnName".toUpperCase()).toString().toLowerCase();
			if(listTabCol.get(i)!=null && !listTabCol.get(i).toString().equals("") && ((Map) listTabCol.get(i)).get("comments".toUpperCase())!=null){
				comments = ((Map) listTabCol.get(i)).get("comments".toUpperCase()).toString();
//				System.out.println(comments);
			}
			list.add(CodeChar.space1+"<fdg:FooterDataGridColumn headerText=\""+(comments==""?colName:comments)+ "\" dataField=\""+colName+"\" width=\"100\"/>");
			comments = "";
		}
		return list;
	}
	//生成grid
	private List gridMxmlCode(CodeBean param,List listTabCol){
		List list = new ArrayList();
		list.add("");
		list.add("");
		list.add("[一下是gridmxml代码]：");
		list.add("");
		String comments = "";
		
		list.add("<mx:Grid>");
		int col = 0;
		boolean gate = true;//闸门，true:开闸,false:闭闸
		for (int i = 0; i < listTabCol.size(); i++) {
			if(gate){
				list.add(CodeChar.space1+"<mx:GridRow>");
				gate=false;
			}
			String colName = ((Map) listTabCol.get(i)).get("columnName".toUpperCase()).toString().toLowerCase();
			
			if(listTabCol.get(i)!=null && !listTabCol.get(i).toString().equals("") 
					&& ((Map) listTabCol.get(i)).get("comments".toUpperCase())!=null){
				comments = ((Map) listTabCol.get(i)).get("comments".toUpperCase()).toString();
//				System.out.println(comments);
			}
			list.add(CodeChar.space2+"<mx:GridItem>");
			list.add(CodeChar.space1+CodeChar.space2+"<mx:Label text=\""+(comments==""?colName:comments)+"\"/>");
			list.add(CodeChar.space2+"</mx:GridItem>");
			list.add(CodeChar.space2+"<mx:GridItem>");
			list.add(CodeChar.space1+CodeChar.space2+"<mx:TextInput id=\""+codeConfig.getParamPrefix()
					+colName+"\" width=\"100%\"/>");
			list.add(CodeChar.space2+"</mx:GridItem>");
			col++;
			if(col==4){//每行4列
				gate=true;
				col=0;
			}
			if(gate){
				list.add(CodeChar.space1+"</mx:GridRow>");
			}else{
				if(listTabCol.size()==i+1){
					list.add(CodeChar.space1+"</mx:GridRow>");
				}
			}
			comments = "";
		}
		list.add("</mx:Grid>");
		return list;
	}
	//把参数生成JSON格式
	private List JSON(CodeBean param,List listTabCol){
		List list = new ArrayList();
		list.add("");
		list.add("");
		list.add("[一下是insert or update的参数代码]：");
		list.add("");
		String comments = "";
		list.add("{");
		for (int i = 0; i < listTabCol.size(); i++) {
			String colName = ((Map) listTabCol.get(i)).get("columnName".toUpperCase()).toString().toLowerCase();
			
			if(listTabCol.get(i)!=null && !listTabCol.get(i).toString().equals("") && ((Map) listTabCol.get(i)).get("comments".toUpperCase())!=null){
				comments = ((Map) listTabCol.get(i)).get("comments".toUpperCase()).toString();
//				System.out.println(comments);
			}
			list.add(colName+" : "+codeConfig.getParamPrefix()+colName+".text,"+(codeConfig.getAddIsMents()?("//"+comments):""));
			comments = "";
		}
		String lastStr = list.get(list.size()-1).toString();
		int i = lastStr.lastIndexOf(",");
		lastStr = lastStr.substring(0,i)+lastStr.substring(i+1);
		list.set(list.size()-1, lastStr);
		list.add("}");
		return list;
	}
	//通过JSON去填充控件
	private List setValueByJson(CodeBean param,List listTabCol){
		List list = new ArrayList();
		list.add("");
		list.add("");
		list.add("[一下是setValueByJson的参数代码]：");
		list.add("");
		String comments = "";
		for (int i = 0; i < listTabCol.size(); i++) {
			String colName = ((Map) listTabCol.get(i)).get("columnName".toUpperCase()).toString().toLowerCase();
			
			if(listTabCol.get(i)!=null && !listTabCol.get(i).toString().equals("") && ((Map) listTabCol.get(i)).get("comments".toUpperCase())!=null){
				comments = ((Map) listTabCol.get(i)).get("comments".toUpperCase()).toString();
//				System.out.println(comments);
			}
			list.add(codeConfig.getParamPrefix()+colName+".text = obj[\""+colName+"\"];"+(codeConfig.getAddIsMents()?("//"+comments):""));
			comments = "";
		}
		return list;
	}
	
	public static void main(String[] args) {
		System.out.println("aaa.b".replaceAll("\\.", "}"));
	}
}
