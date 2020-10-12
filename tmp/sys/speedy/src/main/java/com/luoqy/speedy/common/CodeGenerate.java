package com.luoqy.speedy.common;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.luoqy.speedy.core.generator.config.CodeConfig;
import com.luoqy.speedy.data.MySqldbUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CodeGenerate {
	/**
	 *
	 * @Title: main
	 * @Description: 代码生成
	 * @param args
	 * @throws IOException
	 */
	public static void codeGenerate(CodeConfig codeConfig) {
		try {
			AutoGenerator mpg = new AutoGenerator();
			Map<String,String> base=ConfigManage.findProperties("base");
			//判断是否本地
			if("true".equals(base.get("localhost"))){
				codeConfig.setLocalhostFlag(true);
			}
			Map<String,String> props=ConfigManage.findProperties("jdbc");
			TemplateConfig templateConfig = new TemplateConfig();
			InjectionConfig injectionConfig = new InjectionConfig() {
				// 自定义属性注入:abc
				// 在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
				@Override
				public void initMap() {
					Map<String, Object> map = new HashMap<>();
					List<Map<String,String>> tableNames=(List<Map<String,String>>)MySqldbUtil.mysqlSelect("select column_name as name,COLUMN_COMMENT as context,(IF(IS_NULLABLE='NO','required','')) as isnull from information_schema.columns where table_name  = '"+codeConfig.getTablename()+"' and table_schema='"+codeConfig.getDbName()+"'", "list",null);
					map.put("context",codeConfig);
					map.put("tableNames",tableNames);
					map.put("date",new SimpleDateFormat("YYYY-MM-dd HH:mm-ss").format(new Date()));
					this.setMap(map);
				}
			};
			// 配置自定义属性注入
			mpg.setCfg(injectionConfig);
			// 全局配置
			GlobalConfig gc = new GlobalConfig();
			//
			if(codeConfig.getLocalhostFlag()){
				//本地路径
				gc.setOutputDir(CodeGenerate.class.getResource("/").getPath().replaceAll("target/classes/", "src/main/java/"));
			}else{
				//编译路径
				gc.setOutputDir(CodeGenerate.class.getResource("/").getPath());
			}
			// 输出文件路径
			gc.setFileOverride(true);
			gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
			gc.setEnableCache(false);// XML 二级缓存
			gc.setBaseResultMap(true);// XML ResultMap
			gc.setBaseColumnList(false);// XML columList
			gc.setAuthor(codeConfig.getAuthor());// 作者
			// 自定义文件命名，注意 %s 会自动填充表实体属性！
			gc.setControllerName("%sController");
			gc.setServiceName("%sService");
			gc.setServiceImplName("%sServiceImpl");
			gc.setMapperName("%sDao");
	    	gc.setXmlName("%sMapper");
			mpg.setGlobalConfig(gc);
			// 数据源配置
			DataSourceConfig dsc = new DataSourceConfig();
			dsc.setDbType(DbType.MYSQL);
			dsc.setDriverName(props.get("driverClassName"));
			dsc.setUsername(props.get("username"));
			dsc.setPassword(props.get("password"));
			dsc.setUrl(props.get("url"));
			mpg.setDataSource(dsc);

			// 策略配置
			StrategyConfig strategy = new StrategyConfig();
			strategy.setTablePrefix(new String[] { 
					codeConfig.getIgnoreTabelPrefix().contains("_")? 
					codeConfig.getIgnoreTabelPrefix() : 
					codeConfig.getIgnoreTabelPrefix() + "_" });// 此处可以修改为您的表前缀
			strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
			strategy.setInclude(new String[] { codeConfig.getTablename() }); // 需要生成的表

			strategy.setSuperServiceClass(null);
			strategy.setSuperServiceImplClass(null);
			strategy.setSuperMapperClass(null);

			mpg.setStrategy(strategy);
			// 包配置
			PackageConfig pc = new PackageConfig();
			
			// "com.luoqy.speedy"
			if("2".equals(codeConfig.getPageType())){
				if (codeConfig.getIndexPageSwitch()) {
					createView("page.html.vm", codeConfig.getModuleName() + ".html", codeConfig);
					createView("page.js.vm", codeConfig.getModuleName() + ".js", codeConfig);
				}
				if (codeConfig.getAddPageSwitch()) {
					createView("pageAdd.html.vm", codeConfig.getModuleName() + "Add.html", codeConfig);
					createView("page_info.js.vm", codeConfig.getModuleName() + "info.js", codeConfig);
				}
				if (codeConfig.getEditPageSwitch()) {
					createView("pageUpdate.html.vm", codeConfig.getModuleName() + "Update.html", codeConfig);
					createView("page_info.js.vm", codeConfig.getModuleName() + "info.js", codeConfig);
				}
			}else{
				createView("pageone.html.vm", codeConfig.getModuleName() + ".html", codeConfig);
				createView("page_info.js.vm", codeConfig.getModuleName() + "info.js", codeConfig);
			}
			pc.setParent(codeConfig.getProjectPackage());
			// 控制层
			if (codeConfig.getControllerSwitch()) {
				
				templateConfig.setController("speedyTemplate/advanced/Controller.java");
				pc.setController("controller");
			}else{
				pc.setController(null);
				templateConfig.setController(null);
			}
			// 服务层
			if (codeConfig.getServiceSwitch()) {
				templateConfig.setService("speedyTemplate/advanced/service.java");
				pc.setService("service");
				templateConfig.setServiceImpl("speedyTemplate/advanced/serviceImpl.java");
				pc.setServiceImpl("service.impl");
			}else{
				pc.setService(null);
				pc.setServiceImpl(null);
				templateConfig.setService(null);
				templateConfig.setServiceImpl(null);
			}
			// 持久层
			
			if (codeConfig.getDaoSwitch()) {
				templateConfig.setMapper("speedyTemplate/advanced/Dao.java");
				pc.setMapper("dao");
			}else{
				pc.setMapper(null);
				templateConfig.setMapper(null);
			}
			// 实体类
			if (codeConfig.getEntitySwitch()) {
				pc.setEntity("model");
			}else{
				templateConfig.setEntity(null);
				pc.setEntity(null);
			}
		    if(codeConfig.getXmlSwich()){ 
		    	pc.setXml("xml"); 
		    }else{
		    	templateConfig.setXml(null);
		    	pc.setXml(null); 
		    }
			
			mpg.setTemplate(templateConfig);
			mpg.setPackageInfo(pc);
			// 执行生成
			mpg.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected static void createView(String templatePage, String fileName, CodeConfig parames) throws IOException {
		Properties p = new Properties();
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "");
		p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
		p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		Velocity.init(p);
		//模板路径
		String templatePath=CodeGenerate.class.getResource("/").getPath()+"speedyTemplate/advanced"+File.separator+templatePage;
		
		if(parames.getLocalhostFlag()){
			templatePath=CodeGenerate.class.getResource("/").getPath().replaceAll("target/classes/", "src/main/resources/")+"speedyTemplate/advanced"+File.separator+templatePage;
		}
		
		Template template = Velocity.getTemplate(templatePath);
		VelocityContext context = new VelocityContext();
		// string
		context.put("context", parames);
		//prefix 处理thymeleaf 模板前缀编制
		context.put("prefix", "${");
		if(templatePage.contains("page")){
			Object tableTitle=MySqldbUtil.mysqlSelect("select column_name as name,COLUMN_COMMENT as context,(IF(IS_NULLABLE='NO','required','')) as isnull from information_schema.columns where table_name  = '"+parames.getTablename()+"' and table_schema='"+parames.getDbName()+"'", "list",null);
			context.put("title", tableTitle);
			
			//符号
			context.put("symbol", "/");
		}
		if(parames.getLocalhostFlag()){
			// 本地路径
			String readyPath = new File("").getAbsolutePath();
			readyPath=readyPath+File.separator + "src/main/webapp/WEB-INF"+ parames.getRequestPath();
			if(fileName.contains("js")){
				readyPath=new File("").getAbsolutePath()+File.separator+"src/main/resources/static"+parames.getRequestPath();
			}
			if(new File(readyPath).exists()){
				readyPath=readyPath+File.separator+fileName;
			}else{
				new File(readyPath).mkdirs();
				readyPath=readyPath+File.separator+fileName;
			}
			BufferedWriter ready = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(readyPath), "UTF-8"));
			template.merge(context, ready);
			ready.flush();
			ready.close();
		}else{
			// 编译路径
			String path=CodeGenerate.class.getResource("/").getPath();
			path =path+"WEB-INF"+parames.getRequestPath();
			if(fileName.contains("js")){
				path=CodeGenerate.class.getResource("/").getPath()+File.separator+"static"+parames.getRequestPath();
			}
			if(new File(path).exists()){
				path=path+File.separator+fileName;
			}else{
				new File(path).mkdirs();
				path=path+File.separator+fileName;
			}
			// 编译路径
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
			template.merge(context, writer);
			writer.flush();
			writer.close();
		}
	}
}
