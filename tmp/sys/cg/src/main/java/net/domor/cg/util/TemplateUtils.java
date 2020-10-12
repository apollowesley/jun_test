package net.domor.cg.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class TemplateUtils {
	
	private static String mybatisTemplate = null;
	
	private static String daoTemplate = null;
	
	private static String serviceTemplate = null;
	
	private static String serviceImplTemplate = null;
	
	private static String aoTemplate = null;
	
	private static String aoImplTemplate = null;
	
	private static String springmvcTemplate = null;
	
	@SuppressWarnings("unchecked")
	public static String mybatisTemplate(Map<String, Object> entity) {
		if(StringUtils.isBlank(mybatisTemplate)) {
			mybatisTemplate = FileUtils.readClasspathFile("/mybatisTemplate");
		}
		String mybatisContent = mybatisTemplate;
		mybatisContent = mybatisContent.replaceAll("\\$daoName", (String) entity.get("daoClassName"));
		mybatisContent = mybatisContent.replaceAll("\\$entityName", (String) entity.get("entityName"));
		mybatisContent = mybatisContent.replaceAll("\\$tableName", (String) entity.get("tableName"));
		StringBuilder idSql = new StringBuilder();
		idSql.append((String) entity.get("primaryKey")).append(" = ").append("#{").append((String) entity.get("primaryKey")).append("}");
		mybatisContent = mybatisContent.replaceAll("\\$idSql", idSql.toString());
		StringBuilder addFields = new StringBuilder();
		StringBuilder addSql = new StringBuilder();
		StringBuilder updateFieldsSql = new StringBuilder();
		List<String> fieldlist = (List<String>) entity.get("fields");
		for(int i = 0; i < fieldlist.size(); i++) {
			if(i > 0) {
				addFields.append(", ");
				addSql.append(", ");
				updateFieldsSql.append(", \r\n\t\t\t");
			}
			addFields.append((String) fieldlist.get(i));
			addSql.append("#{").append((String) fieldlist.get(i)).append("}");
			updateFieldsSql.append((String) fieldlist.get(i)).append(" = #{").append((String) fieldlist.get(i)).append("}");
		}
		mybatisContent = mybatisContent.replaceAll("\\$addFields", addFields.toString());
		mybatisContent = mybatisContent.replaceAll("\\$addSql", addSql.toString());
		mybatisContent = mybatisContent.replaceAll("\\$updateFieldsSql", updateFieldsSql.toString());
		mybatisContent = mybatisContent.replaceAll("\\$type", (String) entity.get("type"));
		return mybatisContent;
	}

	public static String daoTemplate(Map<String, Object> entityMap) {
		if(StringUtils.isBlank(daoTemplate)) {
			daoTemplate = FileUtils.readClasspathFile("/daoTemplate");
		}
		String daoContent = daoTemplate;
		daoContent = daoContent.replaceAll("\\$daoPackageName", (String) entityMap.get("daoPackageName"));
		daoContent = daoContent.replaceAll("\\$importClassName", (String) entityMap.get("name"));
		daoContent = daoContent.replaceAll("\\$daoClassName", (String) entityMap.get("entityName") + "Dao");
		daoContent = daoContent.replaceAll("\\$entityName", (String) entityMap.get("entityName"));
		daoContent = daoContent.replaceAll("\\$lowerEntityName", (String) entityMap.get("lowerEntityName"));
		daoContent = daoContent.replaceAll("\\$primaryKey", (String) entityMap.get("primaryKey"));
		daoContent = daoContent.replaceAll("\\$type", (String) entityMap.get("type"));
		return daoContent;
	}
	
	public static String serviceTemplate(Map<String, Object> entity) {
		if(StringUtils.isBlank(serviceTemplate)) {
			serviceTemplate = FileUtils.readClasspathFile("/serviceTemplate");
		}

		String serviceContent = serviceTemplate;

		serviceContent = serviceContent.replaceAll("\\$servicePackageName", (String) entity.get("servicePackageName"));
		serviceContent = serviceContent.replaceAll("\\$importClassName", (String) entity.get("name"));
		serviceContent = serviceContent.replaceAll("\\$serviceClassName", (String) entity.get("entityName") + "Service");
		serviceContent = serviceContent.replaceAll("\\$entityName", (String) entity.get("entityName"));
		serviceContent = serviceContent.replaceAll("\\$lowerEntityName", (String) entity.get("lowerEntityName"));
		serviceContent = serviceContent.replaceAll("\\$primaryKey", (String) entity.get("primaryKey"));
		serviceContent = serviceContent.replaceAll("\\$type", (String) entity.get("type"));

		return serviceContent;
	}

	public static String serviceImplTemplate(Map<String, Object> entity) {
		if(StringUtils.isBlank(serviceImplTemplate)) {
			serviceImplTemplate = FileUtils.readClasspathFile("/serviceImplTemplate");
		}

		String serviceImplContent = serviceImplTemplate;

		serviceImplContent = serviceImplContent.replaceAll("\\$servicePackageName", (String) entity.get("servicePackageName"));
		serviceImplContent = serviceImplContent.replaceAll("\\$daoName", entity.get("basePackage") + "dao." + entity.get("entityName") + "Dao");
		serviceImplContent = serviceImplContent.replaceAll("\\$importClassName", (String) entity.get("name"));
		serviceImplContent = serviceImplContent.replaceAll("\\$entityName", (String) entity.get("entityName"));
		serviceImplContent = serviceImplContent.replaceAll("\\$lowerEntityName", (String) entity.get("lowerEntityName"));
		serviceImplContent = serviceImplContent.replaceAll("\\$primaryKey", (String) entity.get("primaryKey"));
		serviceImplContent = serviceImplContent.replaceAll("\\$type", (String) entity.get("type"));

		return serviceImplContent;
	}

	public static String aoTemplate(Map<String, Object> entity) {
		if(StringUtils.isBlank(aoTemplate)) {
			aoTemplate = FileUtils.readClasspathFile("/aoTemplate");
		}

		String aoContent = aoTemplate;

		aoContent = aoContent.replaceAll("\\$aoPackageName", (String) entity.get("aoPackageName"));
		aoContent = aoContent.replaceAll("\\$importClassName", (String) entity.get("name"));
		aoContent = aoContent.replaceAll("\\$entityName", (String) entity.get("entityName"));
		aoContent = aoContent.replaceAll("\\$lowerEntityName", (String) entity.get("lowerEntityName"));
		aoContent = aoContent.replaceAll("\\$primaryKey", (String) entity.get("primaryKey"));
		aoContent = aoContent.replaceAll("\\$type", (String) entity.get("type"));

		return aoContent;
	}

	@SuppressWarnings("unchecked")
	public static String aoImplTemplate(Map<String, Object> entity) {
		if(StringUtils.isBlank(aoImplTemplate)) {
			aoImplTemplate = FileUtils.readClasspathFile("/aoImplTemplate");
		}

		String aoImplContent = aoImplTemplate;

		aoImplContent = aoImplContent.replaceAll("\\$aoPackageName", (String) entity.get("aoPackageName"));
		aoImplContent = aoImplContent.replaceAll("\\$serviceName", entity.get("basePackage") + "service." + entity.get("entityName") + "Service");
		aoImplContent = aoImplContent.replaceAll("\\$importClassName", (String) entity.get("name"));
		aoImplContent = aoImplContent.replaceAll("\\$entityName", (String) entity.get("entityName"));
		aoImplContent = aoImplContent.replaceAll("\\$lowerEntityName", (String) entity.get("lowerEntityName"));
		aoImplContent = aoImplContent.replaceAll("\\$primaryKey", (String) entity.get("primaryKey"));
		aoImplContent = aoImplContent.replaceAll("\\$type", (String) entity.get("type"));

		List<String> fieldlist = (List<String>) entity.get("fields");
		StringBuilder sb = new StringBuilder();

		for(String field : fieldlist) {
			sb.append("\t\t").append(entity.get("lowerEntityName")).append("Temp.set").append(org.apache.commons.lang3.StringUtils.capitalize(field));
			sb.append("(").append(entity.get("lowerEntityName")).append(".get");
			sb.append(org.apache.commons.lang3.StringUtils.capitalize(field)).append("());").append("\r\n");
		}

		aoImplContent = aoImplContent.replaceAll("\\$updateCode", sb.toString());

		return aoImplContent;
	}

	public static String springMVCTemplate(Map<String, Object> entity) {
		if(StringUtils.isBlank(springmvcTemplate)) {
			springmvcTemplate = FileUtils.readClasspathFile("/springmvcTemplate");
		}

		String springmvcContent = springmvcTemplate;

		springmvcContent = springmvcContent.replaceAll("\\$actionPackageName", (String) entity.get("actionPackageName"));
		springmvcContent = springmvcContent.replaceAll("\\$aoName", entity.get("basePackage") + "ao." + entity.get("entityName") + "AO");
		springmvcContent = springmvcContent.replaceAll("\\$importClassName", (String) entity.get("name"));
		springmvcContent = springmvcContent.replaceAll("\\$entityName", (String) entity.get("entityName"));
		springmvcContent = springmvcContent.replaceAll("\\$lowerEntityName", (String) entity.get("lowerEntityName"));
		springmvcContent = springmvcContent.replaceAll("\\$primaryKey", (String) entity.get("primaryKey"));
		springmvcContent = springmvcContent.replaceAll("\\$type", (String) entity.get("type"));

		return springmvcContent;
	}
}
