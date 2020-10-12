package com.kiss.freemarker.templete;

import java.util.Map;
import java.util.HashMap;
import java.util.Locale;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/***
 * FtlTemplate模版中间类(ftl生成中的参数需要从此类中设置)
 *
 */
public class FtlTemplateHandler implements TemplateHandler{
	private String directory;
	private String filePath;
	private String encoding;
	private Map parameters;
	
	
	public String getContent() {
		try{
			Configuration freemarkerCfg = new Configuration();
	        freemarkerCfg.setDirectoryForTemplateLoading(new File(directory));
	        freemarkerCfg.setEncoding(Locale.getDefault(),encoding);
	        Template template = freemarkerCfg.getTemplate(filePath);
            template.setEncoding(encoding);
            
            HashMap root = new HashMap();			
            root.put("parameters", parameters);
            
            StringWriter writer = new StringWriter();			
            template.process(root, writer);
            return writer.toString();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}


	public void setDirectory(String directory) {
		this.directory = directory;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
}
