package com.kiss.freemarker.generatemain;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.kiss.freemarker.templete.FtlTemplateHandler;
import com.kiss.freemarker.templete.TemplateHandler;

/**
 * 
 * @author yong_jiang
 * 该类主要用来在生成具体类之前相关的 属性设置
 */

public abstract class Generator {

	//指定生成模版flt的路径所在
	protected String templateDir = "src/main/java/com/hrrm/kiss/freemarker/resourcestemplete";
	//公司名称
	protected String orgName="com.hrrm.kiss";
	protected String moduleName;
	
	protected String application="baseService";
	protected String sharedProject="sharedProject";
	protected String uiProject ;
	
	protected String targetDir;
	
	protected String entity;
	protected String packageName;
	protected String configFile;
	
	protected String daomodelName;
	

	public String getDaomodelName() {
		return daomodelName;
	}

	public void setDaomodelName(String daomodelName) {
		this.daomodelName = daomodelName;
	}

	public String getTemplateDir() {
		return templateDir;
	}

	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	
	public String getTargetDir() {
		return targetDir;
	}

	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}

	
	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getSharedProject() {
		return sharedProject;
	}

	public void setSharedProject(String sharedProject) {
		this.sharedProject = sharedProject;
	}

	public String getUiProject() {
		return uiProject;
	}

	public void setUiProject(String uiProject) {
		this.uiProject = uiProject;
	}

	public abstract boolean generate();
	public abstract String getTemplateName();
	
	/**
	 * 主要生成类方法
	 * @return
	 * @throws Exception
	 */
	protected String doGenerate() throws Exception{
		TemplateHandler templateHandler = new FtlTemplateHandler();
		templateHandler.setDirectory(templateDir);
		templateHandler.setFilePath(getTemplateName());
		templateHandler.setEncoding("utf-8");
		Map parameters = new HashMap();
		parameters.put("orgName", orgName);

		parameters.put("packageName", packageName);
		parameters.put("entity", entity);
		parameters.put("moduleName", moduleName);
		templateHandler.setParameters(parameters);
		
		String html = templateHandler.getContent();
		
		
		String targetFile = targetDir;
		File d = new File(targetFile);
		if(!d.isDirectory()){
			FileUtils.forceMkdir(d);
		}
		
		targetFile = targetFile.replace("\\\\", "/");
		targetFile = targetFile.replace("\\", "/");
		targetFile = targetFile.replace("//", "/");
		if(targetFile.endsWith("/")){
			targetFile = targetFile.substring(0, targetFile.length()-1);
		}
		

		String orgPath = this.getOrgPath();
		String packagePath = this.getPackagePath();
		
		
		String wholePath = orgPath;
		if(this.moduleName != null){
			wholePath += "/"+this.getModulePath();
		}
		
		wholePath += "/"+packagePath;
		
		if(!targetFile.endsWith(wholePath)){
			targetFile += "/"+wholePath;
		}
		
		//System.out.println(targetFile);
		targetDir = targetFile;
		
		return html;

	}
	
	/***
	 * 获取模块路径
	 * @return String
	 */
	protected String getModulePath(){
		String packagePath = "";
		if(moduleName.contains(".")){
			String[] split = moduleName.split("\\.");
			for(int i=0;i<split.length;i++){
				packagePath += split[i];
				if(i != (split.length-1)){
					packagePath += "/";
				}
			}
		}else{
			packagePath = moduleName;
		}
		return packagePath;
	}
	

	/***
	 * 获取包名路径
	 * @return String
	 */
	protected String getPackagePath(){
		String packagePath = "";
		if(packageName.contains(".")){
			String[] split = packageName.split("\\.");
			for(int i=0;i<split.length;i++){
				packagePath += split[i];
				if(i != (split.length-1)){
					packagePath += "/";
				}
			}
		}else{
			packagePath = packageName;
		}
		return packagePath;
	}
	
	/**
	 * 获取组织(公司)路径
	 * @return
	 */
	protected String getOrgPath(){
		String orgPath = "";
		if(orgName.contains(".")){
			String[] split = orgName.split("\\.");
			for(int i=0;i<split.length;i++){
				orgPath += split[i];
				if(i != (split.length-1)){
					orgPath += "/";
				}
			}
		}else{
			orgPath = orgName;
		}
		return orgPath;
	}

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	
}
