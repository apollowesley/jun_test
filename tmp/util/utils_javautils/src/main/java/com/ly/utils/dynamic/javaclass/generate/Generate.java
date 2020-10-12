package com.ly.utils.dynamic.javaclass.generate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import com.ly.utils.io.FileUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 生成java文件类 使用freemarker核心
 * 
 * @version 1.2
 */
public class Generate {

	/**
	 * 获取模板
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	private static Template getTemplate(String path, String name) throws IOException {
		// 通过Freemaker的Configuration读取相应的ftl
		Configuration cfg = new Configuration();
		// 设定去哪里读取相应的ftl模板文件
		// cfg.setClassForTemplateLoading(Generate.class.getClass(), path);
		// 设置模板文件目录
		cfg.setDirectoryForTemplateLoading(new File(path));
		// 在模板文件目录中找到名称为name的文件
		return cfg.getTemplate(name);
	}

	/**
	 * 将模版输出到流
	 * 
	 * @param template		模版
	 * @param map			值
	 * @param out			输出流
	 */
	private static void out(Template template, Map<?,?> map, OutputStream out) {
		// 获取输出流（指定到控制台（标准输出））
		Writer outs = new OutputStreamWriter(out);
		// 数据与模板合并（数据+模板=输出）
		try {
			template.process(map, outs);
			outs.flush();
		} catch (TemplateException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将模版输出到文件
	 * 
	 * @param pathFile		模版文件路径
	 * @param map			值
	 * @param filename		结果文件
	 */
	public static void out(String pathFile, Map<?,?> map, String filename){
		try {
			File file = FileUtil.getFile(pathFile);
			String name = file.getName();
			String path = file.getPath().replace(name, "");
			out(getTemplate(path, name), map, new FileOutputStream(FileUtil.createFile(filename)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将示例模版输出到文件
	 * @param type
	 * @param map
	 * @param filename
	 * @throws Exception 
	 */
	public static void exampleOut(String type, Map<?,?> map, String filename) throws Exception{
		out(FileUtil.getURL("ftl/"+type+".ftl").getPath(), map, filename);
	}
}
