package com.luoqy.speedy.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @author luoqy
 * @date 2019年9月14日
 * WEB端代码生成
 */
public class WebCodeGenerate {
	/**
	 * @param templatePage 模板页面
	 * @param fileName 文件名称
	 * @throws IOException
	 */
	public static boolean createView(String templatePage, String fileName,Object content){
		try{
			templatePage="article.html.vm";
			Properties p = new Properties();
			p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "");
			p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
			p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
			Velocity.init(p);
			//模板路径
			String templatePath=WebCodeGenerate.class.getResource("/").getPath()+"speedyTemplate/advanced"+File.separator+templatePage;

			/*if(parames.getLocalhostFlag()){
				templatePath=WebCodeGenerate.class.getResource("/").getPath().replaceAll("target/classes/", "src/main/resources/")+"speedyTemplate/advanced"+File.separator+templatePage;
			}*/

			Template template = Velocity.getTemplate(templatePath);
			VelocityContext context = new VelocityContext();
			// string
			//prefix 处理thymeleaf 模板前缀编制
			context.put("prefix", "${");
			context.put("content", content);
			Map<String,String> path=ConfigManage.findProperties("path");

			String readyPath =path.get("articlePath");
			if(new File(readyPath).exists()){
				readyPath=readyPath+fileName+".html";
			}else{
				new File(readyPath).mkdirs();
				readyPath=readyPath+fileName+".html";
			}
			BufferedWriter ready = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(readyPath), "UTF-8"));
			template.merge(context, ready);
			ready.flush();
			ready.close();
			return true;
		}catch (Exception e){
			System.err.println(e);
			return false;
		}
	}

}
