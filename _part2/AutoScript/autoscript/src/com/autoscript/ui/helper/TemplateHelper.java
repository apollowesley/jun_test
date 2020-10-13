package com.autoscript.ui.helper;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.autoscript.ui.logger.UILogger;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * 包装freemarker,是根据模版产生目标文件的工具类
 * @author longsebo
 *
 */
public class TemplateHelper {
	private static final Logger logger = UILogger.getLogger(TemplateHelper.class);
	
	/**
	 * 封装freemarker,根据模版产生目标文件,注意templatePath采用类路径做根路径,而resultPath则以当前项目作为根路径
	 * @param paramMap
	 * @param templatePath
	 * @param resultPath
	 */
	public static void createFileByPath(Object paramMap, String templateFileName,String resultPath) throws TemplateHelperException{
		Configuration freemarkerCfg = new Configuration();
		
		if(resultPath != null && resultPath.indexOf(IPublicConst.DIV) == 0){
			resultPath = resultPath.substring(1);
		}
		Template template;
		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		Writer out = null;
		try {
			freemarkerCfg.setDirectoryForTemplateLoading(new File(FileCtrlUtils.getFilePath(templateFileName)));
			freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");
			template = freemarkerCfg.getTemplate(FileCtrlUtils.getFileName(templateFileName));
			template.setEncoding("UTF-8");
			
			File toFile = new File(resultPath);
			fileOutputStream = new FileOutputStream(toFile);
			outputStreamWriter = new OutputStreamWriter(
					fileOutputStream, "UTF-8");
			out = new BufferedWriter(outputStreamWriter);
			template.process(paramMap, out);// 作为参数的根对象交给模版，模版中根据名字直接访问参数对象
			out.flush();
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new TemplateHelperException(UIPropertyHelper.getString("common.TemplateHelper.encodeError"), e);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new TemplateHelperException(UIPropertyHelper.getString("common.ResourceHelper.FileNotFound", templateFileName, e.getMessage()),e);
		} catch (TemplateException e) {
			logger.error(e.getMessage(), e);
			throw new TemplateHelperException(UIPropertyHelper.getString("common.TemplateHelper.creationError"), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new TemplateHelperException(UIPropertyHelper.getString("common.TemplateHelper.FileIOError"), e);
		}finally{
			if(fileOutputStream!=null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);					
				}
			}
			if(outputStreamWriter!=null){
				try {
					outputStreamWriter.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	
	
}
