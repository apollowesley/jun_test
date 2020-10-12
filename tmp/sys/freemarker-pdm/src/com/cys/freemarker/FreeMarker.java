package com.cys.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.org.cys.pdm.PDM;
import com.org.cys.pdm.PDMColumn;
import com.org.cys.pdm.PDMTable;
import com.org.cys.pdm.Parser;

import freemarker.template.Configuration;
import freemarker.template.Template;


/**
 * 生成代码生成器主类
 * @author caiyushen 2017-09-09
 * */
public class FreeMarker{
	
	//模块-包名
	private static String packageName = "base";
	
	//pdm文件路径
	private static String pdmFilePath = "E:\\pdmtest.pdm";
	
	//放置模板文件路径前缀
	private static String templateFilePath = "E:/EclipseWorkspace/Tomcat6.0Workspace4.3/freemarker-pdm/src/com/cys/freemarker";
	
	//文件输出路径
	private static String outFilePath = "E:"+File.separator+"freeMarkerFTL"+File.separator;
	
	//测试方法
	public static void main(String[] args) {
		Parser parser = new Parser();
		try {
			//读取pdm文件
			PDM pdm = parser.pdmParser(pdmFilePath);
			//获取表的数据
			ArrayList<PDMTable> pdmTableList = pdm.getTables();
			int pdmTableListSize = pdmTableList.size();
			//遍历所有表数据
			for(int i=0;i<pdmTableListSize;i++){
				//获取一个表数据
				PDMTable pdmTable = pdmTableList.get(i);
				
				getEntityInterface(pdmTable,packageName);
				getEntity(pdmTable,packageName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 通过文件名加载模板
	 * @param ftlName 模板文件名
	 * @param ftlPath 模板路径
	 * */
	public static Template getTemplate(String ftlName,String ftlPath) throws Exception{
		try {
			Configuration cfg = new Configuration();  	//通过Freemaker的Configuration读取相应的ftl
			cfg.setEncoding(Locale.CHINA, "utf-8");
			cfg.setDirectoryForTemplateLoading(new File(ftlPath));	//设定去哪里读取相应的ftl模板文件
			Template temp = cfg.getTemplate(ftlName);	//在模板文件目录中找到名称为name的文件
			return temp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

	/**
	 * 将table的字段数据转化变量数据
	 * @param pdmTable 单表数据
	 * @author caiyushen
	 * */
	public static List<Map<String,Object>> switchTable(PDMTable pdmTable){
		//获取该表所有字段数据
		ArrayList<PDMColumn> columnsList = pdmTable.getColumns();
		//System.out.println("表内字段数目: " + columnsList.size());
		//System.out.println("注释里表名: "+pdmTable.getName());
		//System.out.println("表名: "+pdmTable.getCode());
		List<Map<String,Object>> dataList = new ArrayList();
		for(int i=0;i<columnsList.size();i++){
			//获取一个字段数据
			PDMColumn column = columnsList.get(i);
			String newGetSetCode = switchColumnGetSet(column);
			//System.out.println("注释："+column.getComment());	
			//System.out.println("类型："+column.getDataType());
			//将字段名转化为变量名
			String newColumn = switchColumn(column);	
			Map<String,Object> resultMap = new HashMap<>();
			resultMap.put("newGetSetCode", newGetSetCode); //新的getset变量
			resultMap.put("newColumn", newColumn);  //新变量
			resultMap.put("comment", column.getComment()); //字段注释
			resultMap.put("dataType",switchObject(column)); //字段类型
			resultMap.put("columnName", column.getName()); //数据库字段名字
			dataList.add(resultMap);
		}
		return dataList;
	}
	
	/**
	 * 生成模板文件
	 * @param file 新增的文件名以及路径
	 * @param template 指定的模板文件
	 * @param pdmTable 数据库表实体类
	 * @param packageName 包名-模块名
	 * @param entityName 实体类名称
	 * */
	public static void getFile(File file,Template template,PDMTable pdmTable,String packageName,String entityName){
		try {
			
			//获取实体类名的变量（小写）
//			String entityNameSmall = switchEntityNameSmall(pdmTable);
			String entityNameSmall = switchEntityNameSmall2(pdmTable);
			
			//获取XML名: BASIC_CASE
			String entityNameXML = switchEntityNameXML(pdmTable);
			List<Map<String,Object>> dataList = switchTable(pdmTable);
			
			Map<String, Object> context = new HashMap<String, Object>();
			context.put("entityName", entityName);
			context.put("entityNameSmall", entityNameSmall);
			context.put("entityNameXML", entityNameXML);
			context.put("entityComment", pdmTable.getName());
			context.put("tableName", pdmTable.getCode());//数据库表名
			context.put("dataList", dataList);
			context.put("package", packageName);//包名-模块名
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			template.process(context, out);
	        out.flush();
	        out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 生成实体类接口
	 * @param pdmTable 数据库表实体类
	 * @param packageName 包名-模块名
	 * */
	public static void getEntityInterface(PDMTable pdmTable,String packageName){
		try {
			//获取实体类名
//			String entityName = switchEntityName(pdmTable);
			String entityName = switchEntityName2(pdmTable);
			File file = new File(outFilePath+"I"+entityName+".java");
			//指定模板
			Template template = getTemplate("IEntityMarker.ftl",templateFilePath);
			//生成文件
			getFile(file,template,pdmTable,packageName,entityName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成实体类	 
	 * @param pdmTable 数据库表实体类
	 * @param packageName 包名-模块名
	 * */
	public static void getEntity(PDMTable pdmTable,String packageName){
		try {
			//获取实体类名
//			String entityName = switchEntityName(pdmTable);
			String entityName = switchEntityName2(pdmTable);

			File file = new File(outFilePath+entityName+".java");
			//指定模板
			Template template = getTemplate("EntityMarker.ftl",templateFilePath);
			//生成文件
			getFile(file,template,pdmTable,packageName,entityName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据数据表字段类型转换为对应封装的JAVA类型
	 * */
	public static String switchObject(PDMColumn column){
		String resultObj = "";
		String dataType = column.getDataType().substring(0,1);
		String dataType2 = column.getDataType().substring(0,2);
		if(dataType.equals("N") || dataType.equals("n")){//NUMBER number
			resultObj = "Long";
			return resultObj;
		}
		
		if(dataType.equals("I")||dataType.equals("i")){//INT
			resultObj = "Integer";
			return resultObj;
		}
		
		if(dataType.equals("C")||dataType.equals("V")||dataType.equals("v")){///CLOB VARCHAR varchar
			resultObj = "String";
			return resultObj;
		}
	
		if(dataType.equals("F")){//FLOAT
			resultObj = "Float";
			return resultObj;
		}
		
		if(dataType2.equals("DO") || dataType2.equals("do")){//Dobule double
			resultObj = "Double";
			return resultObj;
		}
		
		if(dataType2.equals("DA") || dataType2.equals("da")){//DATE date
			resultObj = "Date";
			return resultObj;
		}
		
		System.out.println("name: "+column.getName()+" dataType:"+resultObj);
		return resultObj;
	}
	
	/**
	 * 将数据表名转化为实体类名
	 * 取最后的作为实体类名
	 * */
	public static String switchEntityName(PDMTable pdmTable){
		String tableName = pdmTable.getCode();
		String[] array = tableName.split("_");
		String newCode = "";
		tableName = array[array.length-1]; 
		char[] c= tableName.toCharArray();
		String replaceCode = "";
		for(int i=0;i<c.length;i++){
			if(i>0){
				replaceCode += c[i];
			}
		}
		newCode = c[0]+ exChange(replaceCode);
		System.out.println("新实体类名："+newCode);
		return newCode;
	}
	
	
	/**
	 * 将数据表名转化为实体类名
	 * 除了T以外作为实体类名
	 * */
	public static String switchEntityName2(PDMTable pdmTable){
		String tableName = pdmTable.getCode();
		String[] array = tableName.split("_");
		String newCode = "";
		if(array.length>1){
			for(int j=1;j<array.length;j++){
				tableName = array[j];
				char[] c= tableName.toCharArray();
				String replaceCode = "";
				for(int i=0;i<c.length;i++){
					if(i>0){
						replaceCode += c[i];
					}
				}
				newCode += c[0]+ exChange(replaceCode);
				System.out.println("新变量名："+newCode);
			}
		}
		return newCode;
	}
	
	/**
	 * 将数据表名转化为变量名(小写)
	 * 取最后的作为实体类名
	 * */
	public static String switchEntityNameSmall(PDMTable pdmTable){
		String tableName = pdmTable.getCode();
		String[] array = tableName.split("_");
		String newCode = "";
		tableName = array[array.length-1]; 
		char[] c= tableName.toCharArray();
		String replaceCode = "";
		for(int i=0;i<c.length;i++){
			replaceCode += c[i];
		}
		newCode = exChange(replaceCode);
		System.out.println("新实体类名："+newCode);
		return newCode;
	}
	
	
	/**
	 * 将数据表名转化为变量名(小写)
	 * 除了T以外作为实体类名
	 * */
	public static String switchEntityNameSmall2(PDMTable pdmTable){
		String tableName = pdmTable.getCode();
		String[] array = tableName.split("_");
		String newCode = "";
		if(array.length>1){
			for(int j=1;j<array.length;j++){
				tableName = array[j];
				char[] c= tableName.toCharArray();
				if(j>=2){
					String replaceCode = "";
					for(int i=0;i<c.length;i++){
						if(i>0){
							replaceCode += c[i];
						}
					}
					newCode += c[0] + exChange(replaceCode);
				}else{
					String replaceCode = "";
					for(int i=0;i<c.length;i++){
						replaceCode += c[i];
					}
					newCode += exChange(replaceCode);
				}
				System.out.println("新实体类名："+newCode);
			}
		}
		return newCode;
	}
	
	/**
	 * 将数据表名转化为XML文件名
	 * */
	public static String switchEntityNameXML(PDMTable pdmTable){
		String tableName = pdmTable.getCode();
		String[] array = tableName.split("_");
		String newCode = "";
		if(array.length>1){
			for(int j=1;j<array.length;j++){//去掉T
				tableName = array[j];
				char[] c= tableName.toCharArray();
				String replaceCode = "";
				for(int i=0;i<c.length;i++){
					if(i>0){
						replaceCode += c[i];
					}
				}
				if(j==array.length-1){
					newCode += c[0] + exChange(replaceCode);
				}else{
					newCode += c[0] + exChange(replaceCode) + "_";
				}
				
			}
		}
		System.out.println("新实体类名XML："+newCode);
		return newCode;
	}
	
	
	
	
	/**
	 * 把数据表字段转化为变量名
	 * */
	public static String switchColumn(PDMColumn column){
		String newCode = "";
		String[] array = column.getCode().split("_");
		for(int k=0;k<array.length;k++){
			String codeChar = array[k];
			if(k>0){
				char[] c=codeChar.toCharArray();
				String replaceCode = "";
				for(int l=0;l<c.length;l++){
					if(l>0){
						replaceCode += c[l];
					}
				}
				newCode += c[0] + exChange(replaceCode);
			}else{
				newCode += exChange(codeChar);
			}
		}
		if(newCode.equals("IId")||newCode.equals("iId")){
			System.out.println("转换后的新变量：id");
			newCode = "id";
		}else{
			System.out.println("转换后的新变量："+newCode);
		}	
		return newCode;
	}
	
	
	/**
	 * 把数据表字段转化为getSet方法名字
	 * */
	public static String switchColumnGetSet(PDMColumn column){
		String newCode = "";
		String[] array = column.getCode().split("_");
		for(int i=0;i<array.length;i++){
			String codeChar = array[i];
			char[] c=codeChar.toCharArray();
			String replaceCode = "";
			for(int j=0;j<c.length;j++){
				if(j>0){
					replaceCode += c[j];
				}
			}
			newCode += c[0] + exChange(replaceCode);
		}
		
		if(newCode.equals("Iid")||newCode.equals("IId")){
			System.out.println("转换后的get、set新变量：Id");
			newCode = "Id";
		}else{
			System.out.println("转换后的get、set新变量："+newCode);
		}	
		return newCode;
	}
	
	
	/**
	 * 把一个字符串中的大写转为小写,小写转大写
	 * */
	public static String exChange(String str){  
	    StringBuffer sb = new StringBuffer();  
	    if(str!=null){  
	        for(int i=0;i<str.length();i++){  
	            char c = str.charAt(i);  
	            if(Character.isUpperCase(c)){  
	                sb.append(Character.toLowerCase(c));  
	            }else if(Character.isLowerCase(c)){  
	                sb.append(Character.toUpperCase(c));   
	            }  
	        }  
	    }  
	    return sb.toString();  
	}  
	
}