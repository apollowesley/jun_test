package com.zhoux.freemarker.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * 
 * @author zhoux
 * @Date Mar 31, 2017
 * @Email zhoux@souche.com
 * @Desc 
 */
public class FreeMarkerUtil {
	private static final Logger logger = LoggerFactory.getLogger(FreeMarkerUtil.class);
	
	private static String templateDir="templates";
	
	private static String filePath="E:/freemarker";
	
	@SuppressWarnings("static-access")
	public void setParams(String templateDir,String filePath){
		if(null!=templateDir)
			this.templateDir=templateDir;
		if(null!=filePath)
			this.filePath=filePath;
	}
	
	/**
	 * 
	 * @param dir 模板所在目录
	 * @param codePath 生成文件保存地址
	 * @param codeType 文件的后缀：.java   .js  .html
	 * @param root 填充的数据结构
	 * @param template 模板名称
	 * @throws  
	 */
	public static boolean createFile(String codePath,Map<String,Object> root,String templatePath,String codeType){
		
		Configuration configuration=new Configuration();
		
		try {
			configuration.setDirectoryForTemplateLoading(new File(templateDir));
		} catch (IOException e) {
			logger.error("模板根路径错误！");
			e.printStackTrace();
		}
		String codeRealPath=checkFilePath(codePath, root)+codeType;
		try {
			 Template template = configuration.getTemplate(templatePath);
			 Writer out=new OutputStreamWriter(new FileOutputStream(new File(codeRealPath)));
			 template.process(root, out);
			 out.flush();
			 out.close();
			 return true;
		} catch (IOException e) {
			logger.error("获取模板失败");
			e.printStackTrace();
		} catch (TemplateException e) {
			logger.error("根据模板生成代码失败！");
			e.printStackTrace();
		}finally {
		}
		return false;
	}
	/**
	 * 校验保存文件的代码路径是否正确
	 * @param codePath 路径中不包含生成文件的名称
	 * return 拼接文件最后保存的位置
	 */
	private static String  checkFilePath(String codePath,Map<String,Object> root){
		//校验根路径文件夹是否存在
		File file=new File(filePath+codePath);
		if(!file.exists()||!file.isDirectory())
			file.mkdirs();
		
		String classname=root.get("className").toString();
		
		return filePath+codePath+File.separator+String.valueOf(classname.charAt(0)).toUpperCase()+classname.substring(1);
	}
	
	
}
