package com.yonge.crud.core.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.yonge.crud.core.db.model.Column;
import com.yonge.crud.core.db.model.Table;
import com.yonge.crud.core.generator.module.JavaFileGenerator;
import com.yonge.crud.core.generator.module.SpringGenerator;
import com.yonge.crud.core.generator.module.SqlMapConfigGenerator;
import com.yonge.crud.core.util.JavaBeansUtil;

public class ModuleGenerateIntrospector {

	private static final String DAO_TEMPLATE_NAME = "dao.ftl";

	private static final String POJO_TEMPLATE_NAME = "pojo.ftl";

	private static final String SERVICE_IMPL_TEMPLATE_NAME = "service_impl.ftl";

	private static final String SERVICE_TEMPLATE_NAME = "service.ftl";

	private static final String SQLMAP_TEMPLATE_NAME = "sqlmap.ftl";

	private static final String SQLMAPCONFIG_TEMPLATE_NAME = "sqlmapConfig.ftl";

	private static final String SPRING_TEMPLATE = "spring.ftl";

	private static final String POJO_FILE_SUFFIX = ".java";

	private static final String SQLMAP_FILE_PREFIX = "SqlMap_";

	private static final String XML_FILE_SUFFIX = ".xml";

	private static final String DAO_FILE_SUFFIX = "Dao.java";

	private static final String SERVICE_FILE_SUFFIX = "Service.java";

	private static final String SERVICE_IMPL_FILE_SUFFIX = "ServiceImpl.java";

	private static final String SQLMAP_CONFIG_FILE = "SqlMapConfig.xml";

	private static final String SPRING_CONFIG_FILE = "spring-crud.xml";

	public static void generatePOJO(Table table, String srcBase,
			String packageName) {
		List<Column> columns = table.getColumns();
		for (Column col : columns) {
			// 转换类型
			col.setJavaTypeName(JavaBeansUtil.fromJdbcToJava(col));
			// 转换属性名称
			col.setPropertyName(JavaBeansUtil.getCamelCaseString(
					col.getFieldName(), false));
		}
		table.setColumns(columns);
		table.setPackageName(packageName);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("props", columns);
		params.put("table", table);
		// 生成pojo
		JavaFileGenerator entiryGenerator = new JavaFileGenerator();
		entiryGenerator.generate(params, srcBase, packageName,
				table.getClassName() + POJO_FILE_SUFFIX, POJO_TEMPLATE_NAME);
	}

	public static void generatSqlmap(String driverClass, Table table,
			String srcBase, String sqlMapPackageName, String daoPackageName) {
		Map<String, Object> params = new HashMap<String, Object>();
		String className = table.getClassName();
		params.put("namespace", daoPackageName + "." + className + "Dao");
		params.put("fullClassName", table.getPackageName() + "." + className);
		params.put("alias", Character.toLowerCase(className.charAt(0))
				+ className.substring(1));
		params.put("columns", table.getColumns());

		String tableName = table.getName();
		params.put("tableName", tableName);

		// 构建sql语句
		String fields = "", properties = "", criteria = "", orderbyStr = "";
		Map<String, String> fieldPropMapping = new HashMap<String, String>();
		int keys = 0;
		for (Column col : table.getColumns()) {
			fields += (col.getFieldName() + ",");
			properties += ("#" + col.getPropertyName() + "#,");
			fieldPropMapping.put(col.getFieldName(), col.getPropertyName());
			if (col.isPrimaryKey()) {
				criteria += (col.getFieldName() + " = #"
						+ col.getPropertyName() + "# AND ");
				++keys;
				orderbyStr += (col.getFieldName() + ",");
			}
		}
		if (keys > 1) {
			params.put("paramType", Map.class.getName());
		}
		// 清除最后一个"AND"
		if (StringUtils.isNotBlank(criteria)) {
			criteria = criteria.substring(0, criteria.length() - 4);
		}
		// 删除最后一个“，”
		fields = fields.substring(0, fields.length() - 1);
		properties = properties.substring(0, properties.length() - 1);

		String queryBaseSQL = "SELECT * FROM " + tableName;
		// 全查询
		String queryFullSQL = queryBaseSQL;
		if (StringUtils.isNotBlank(orderbyStr)) {
			orderbyStr = orderbyStr.substring(0, orderbyStr.length() - 1);
			queryFullSQL += " ORDER BY " + orderbyStr;
		}
		params.put("queryFullSQL", queryFullSQL);
		// 插入
		String insertSQL = "INSERT INTO " + tableName + " (" + fields
				+ ") VALUES(" + properties + ")";
		params.put("insertSQL", insertSQL);
		if (StringUtils.isNotBlank(criteria)) {
			// 修改
			String updateSQL = "UPDATE " + tableName + " SET ";
			for (Entry<String, String> entry : fieldPropMapping.entrySet()) {
				updateSQL += (entry.getKey() + " = #" + entry.getValue() + "#,");
			}
			// 删除最后一个“，”
			updateSQL = updateSQL.substring(0, updateSQL.length() - 1);
			updateSQL += (" WHERE " + criteria);
			params.put("updateSQL", updateSQL);
			// 根据主键查询
			String querySingleSQL = queryBaseSQL + " WHERE " + criteria;
			params.put("querySingleSQL", querySingleSQL);
			// 根据主键删除
			String deleteSQL = "DELETE FROM " + tableName + " WHERE "
					+ criteria;
			params.put("deleteSQL", deleteSQL);
		}
		// 分页查询语句
		String queryPage = "";

		if (StringUtils.equals(driverClass, "com.mysql.jdbc.Driver")) {
			// mysql
			queryPage = queryFullSQL + " LIMIT #startLine#,#endLine#";
		} else if (StringUtils.equals(driverClass,
				"oracle.jdbc.driver.OracleDriver")) {
			// oracle
			queryPage = "SELECT * FROM (SELECT A.*,ROWNUM FROM " + tableName
					+ " A WHERE ROWNUM <= #endLine# ORDER BY " + orderbyStr
					+ ") WHERE ROWNUM >= #startLine#";
		} else if (StringUtils.equals(driverClass, "com.ibm.db2.jcc.DB2Driver")) {
			// db2
			queryPage = "SELECT * FROM (SELECT B.*,ROWNUMBER() OVER(ORDER BY "
					+ orderbyStr
					+ ") AS RN FROM "
					+ tableName
					+ " AS B) AS A WHERE A.RN BETWEEN #startLine# AND #endLine#";
		}
		if (StringUtils.isNotBlank(queryPage)) {
			params.put("queryPage", queryPage);
		}
		// 生成sqlmap
		JavaFileGenerator sqlMapGenerator = new JavaFileGenerator();
		sqlMapGenerator.generate(params, srcBase, sqlMapPackageName,
				SQLMAP_FILE_PREFIX + tableName + XML_FILE_SUFFIX,
				SQLMAP_TEMPLATE_NAME);
	}

	public static void generateSqlMapConfig(List<Table> tables, String srcBase,
			String sqlmapPackageName, String sqlMapConfigPackageName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sqlmapPackageName", sqlmapPackageName);
		params.put("tables", tables);
		// 生成sqlmap-config
		SqlMapConfigGenerator sqlMapConfigGenerator = new SqlMapConfigGenerator();
		sqlMapConfigGenerator.createSqlMapConfigFile(sqlmapPackageName, tables,
				params, srcBase, sqlMapConfigPackageName, SQLMAP_CONFIG_FILE,
				SQLMAPCONFIG_TEMPLATE_NAME);
	}

	public static void generateDaoAndService(Table table, String srcBase,
			String daoPackageName, String servicePackageName) {
		Map<String, Object> params = new HashMap<String, Object>();
		String className = table.getClassName();
		String pojoFullClassName = table.getPackageName() + "."
				+ table.getClassName();

		List<Column> primarykeys = new ArrayList<Column>();
		for (Column col : table.getColumns()) {
			if (col.isPrimaryKey()) {
				primarykeys.add(col);
			}
		}
		String paramType = null;
		if (primarykeys.size() == 1) {
			paramType = primarykeys.get(0).getJavaTypeName();
		} else if (primarykeys.size() > 1) {
			paramType = Map.class.getName();
		}
		params.put("pojoFullClassName", pojoFullClassName);
		params.put("className", className);
		params.put("paramType", paramType);
		params.put("daoPackageName", daoPackageName);

		JavaFileGenerator daoGenerator = new JavaFileGenerator();
		daoGenerator.generate(params, srcBase, daoPackageName, className
				+ DAO_FILE_SUFFIX, DAO_TEMPLATE_NAME);

		params.put("servicePackageName", servicePackageName);

		JavaFileGenerator serviceGenerator = new JavaFileGenerator();
		serviceGenerator.generate(params, srcBase, servicePackageName,
				className + SERVICE_FILE_SUFFIX, SERVICE_TEMPLATE_NAME);

		JavaFileGenerator serviceImplGenerator = new JavaFileGenerator();
		serviceImplGenerator.generate(params, srcBase, servicePackageName
				+ File.separator + "impl",
				className + SERVICE_IMPL_FILE_SUFFIX,
				SERVICE_IMPL_TEMPLATE_NAME);
	}

	public static void generateSpring(List<Table> tables, String srcBase,
			String packageName, String daoPackageName, String servicePackageName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("daoPackageName", daoPackageName);
		params.put("servicePackageName", servicePackageName);
		params.put("tables", tables);
		SpringGenerator springGenerator = new SpringGenerator();
		springGenerator.createSpringFile(daoPackageName, servicePackageName,
				tables, params, srcBase, packageName, SPRING_CONFIG_FILE,
				SPRING_TEMPLATE);
	}
}
