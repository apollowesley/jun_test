package net.oschina.durcframework.easymybatis.generator.run;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import net.oschina.durcframework.easymybatis.generator.entity.ClientParam;
import net.oschina.durcframework.easymybatis.generator.entity.DataBaseConfig;
import net.oschina.durcframework.easymybatis.generator.entity.TplInfo;
import net.oschina.durcframework.easymybatis.generator.generator.SQLContext;
import net.oschina.durcframework.easymybatis.generator.generator.SQLService;
import net.oschina.durcframework.easymybatis.generator.generator.SQLServiceFactory;
import net.oschina.durcframework.easymybatis.generator.generator.TableDefinition;
import net.oschina.durcframework.easymybatis.generator.generator.TableSelector;
import net.oschina.durcframework.easymybatis.generator.util.FileUtil;
import net.oschina.durcframework.easymybatis.generator.util.VelocityUtil;
import net.oschina.durcframework.easymybatis.generator.util.XmlFormat;

public class Generator {
	
	private static Log logger = LogFactory.getLog(Generator.class);
	
	/**
	 * 生成全部
	 * @param clientParam
	 * @param dest
	 */
	public void generateCodeAll(ClientParam clientParam, String dest) {
		DataBaseConfig dataBaseConfig = clientParam.buildDataBaseConfig();
		SQLService service = SQLServiceFactory.build(dataBaseConfig);
		
		List<TableDefinition> allTable = service.getTableSelector(dataBaseConfig).getAllTableDefinitions();
		
		for (TableDefinition td : allTable) {
			clientParam.setTableName(td.getTableName());
			this.generateCode(clientParam, dest);
		}
	}
	
	public void generateCode(ClientParam clientParam, String dest) {
		DataBaseConfig dataBaseConfig = clientParam.buildDataBaseConfig();
		SQLContext sqlContext = this.buildClientSQLContextList(clientParam, dataBaseConfig);

		List<String> tpls = clientParam.getTplList();

		setPackageName(sqlContext, clientParam.getPackageName());

		FileUtil.createFolder(dest);

		for (String tplFile : tpls) {
			TplInfo template = this.buildTempObj(tplFile.trim());
			if (template == null) {
				continue;
			}
			String content = doGenerator(sqlContext, template.getContent());
			String fileName = doGenerator(sqlContext, template.getFileName());
			String savePath = doGenerator(sqlContext,template.getSavePath());

			content = this.doFormat(fileName, content);

			String saveDir = dest + File.separator + savePath;
			
			FileUtil.createFolder(saveDir);

			String filePathName = saveDir + File.separator +fileName;
			FileUtil.write(content, filePathName, clientParam.getCharset());
		}

	}

	private TplInfo buildTempObj(String tplFile) {
		if (StringUtils.isEmpty(tplFile)) {
			return null;
		}
		
		String json = FileUtil.readFromClassPath("/tpl/" + tplFile);
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		
		TplInfo temp = JSON.parseObject(json, TplInfo.class);
		String contentFileName = temp.getContentFileName();
		
		if(StringUtils.isEmpty(contentFileName)) {
			contentFileName = tplFile + "_cont";
			temp.setContentFileName(contentFileName);
		}

		if (temp != null) {
			String contentClassPath = "/tpl/" + temp.getContentFileName();
			String content = FileUtil.readFromClassPath(contentClassPath);
			temp.setContent(content);
		}

		return temp;
	}
	
	/**
	 * 返回SQL上下文列表
	 * 
	 * @param tableNames
	 * @return
	 */
	private SQLContext buildClientSQLContextList(ClientParam generatorParam, DataBaseConfig dataBaseConfig) {

		List<String> tableNames = Arrays.asList(generatorParam.getTableName().trim());

		SQLService service = SQLServiceFactory.build(dataBaseConfig);

		TableSelector tableSelector = service.getTableSelector(dataBaseConfig);
		tableSelector.setSchTableNames(tableNames);

		List<TableDefinition> tableDefinitions = tableSelector.getTableDefinitions();

		SQLContext context = new SQLContext(tableDefinitions.get(0));
		context.setParam(generatorParam.getParam());

		return context;
	}
	
	private void setPackageName(SQLContext sqlContext, String packageName) {
		if (StringUtils.hasText(packageName)) {
			sqlContext.setPackageName(packageName);
		}
	}

	private String doGenerator(SQLContext sqlContext, String template) {
		if (template == null) {
			return "";
		}
		VelocityContext context = new VelocityContext();
		
		TableDefinition tableDefinition = sqlContext.getTableDefinition();

		context.put("context", sqlContext);
		context.put("table", tableDefinition);
		context.put("pk", tableDefinition.getPkColumn());
		context.put("columns", tableDefinition.getColumnDefinitions());

		return VelocityUtil.generate(context, template);
	}

	private String doFormat(String fileName, String content) {
		if (fileName.endsWith(".xml")) {
			logger.info("格式化XML代码:" + fileName);
			return XmlFormat.format(content);
		}
		return content;
	}
}
