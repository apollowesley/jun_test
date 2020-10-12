package com.zhoux.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
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
 * @Desc 一个简单的使用freemarker的例子
 */
public class Demo {
	private static final Logger logger=LoggerFactory.getLogger(Demo.class);

	public static void main(String[] args) {
		Configuration configuration=new Configuration();
		try {
			configuration.setDirectoryForTemplateLoading(new File("templates"));
		} catch (IOException e) {
			logger.debug("设置模板根路径错误");
			e.printStackTrace();
		}
		Map<String, Object> root=new HashMap<String,Object>();
		root.put("user", "zhoux");
		
		try {
			Template template=configuration.getTemplate("test.ftl");
			template.process(root, new OutputStreamWriter(System.out));
		} catch (IOException e) {
			logger.error("获取freemarker模板失败，请检查模板跟路径是否正确！");
			e.printStackTrace();
		} catch (TemplateException e) {
			logger.error("根据模板合成文件失败！");
			e.printStackTrace();
		}
		
		
	}
}
