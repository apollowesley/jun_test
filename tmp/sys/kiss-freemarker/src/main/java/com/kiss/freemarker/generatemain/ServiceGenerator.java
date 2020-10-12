package com.kiss.freemarker.generatemain;

import java.io.File;
import org.apache.commons.io.FileUtils;

/**
 *  Service生成类
 * @author yong_jiang
 *
 */
public class ServiceGenerator extends Generator{
	private String templateName = "Service.ftl";
	
	@Override
	public boolean generate() {
		if(targetDir == null || entity == null || packageName == null){
			return false;
		}
		try{
			targetDir = targetDir+"/src/main/java";
			String content = super.doGenerate();
			String targetFile = targetDir;
			targetFile += "/service/I"+entity+"Service.java";
			FileUtils.writeStringToFile(new File(targetFile), content,"utf-8");
			System.out.println("生成"+targetFile+"...成功");
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public String getTemplateName() {
		return templateName;
	}
	
}
