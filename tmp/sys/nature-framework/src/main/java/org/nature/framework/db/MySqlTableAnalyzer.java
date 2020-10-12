package org.nature.framework.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nature.framework.bean.FieldBean;
import org.nature.framework.bean.TableBean;
import org.nature.framework.core.NatureMap;
import org.nature.framework.core.NatureService;
import org.nature.framework.enums.Strategy;
import org.nature.framework.enums.Types;
import org.nature.framework.helper.DatabaseHelper;
import org.nature.framework.helper.ServiceHelper;
import org.nature.framework.helper.TableBeanHelper;
import org.nature.framework.util.CastUtil;
import org.nature.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库观察者，用来判断数据库的表和字段是否存在
 */
public class MySqlTableAnalyzer {
	private static Logger LOGGER = LoggerFactory.getLogger(MySqlTableAnalyzer.class);
	private static Map<String, List<String>> tableColumns = new HashMap<String, List<String>>();
	private static List<String> sqlList = new ArrayList<String>();
	static {
		NatureService natureService = (NatureService) ServiceHelper.getService(NatureService.class);
			List<NatureMap> query1 = natureService.list("SHOW TABLES");
			for (NatureMap natureMap : query1) {
				Set<String> keySet = natureMap.keySet();
				for (String key : keySet) {
					Object object = natureMap.get(key);
					String tableName = CastUtil.castString(object);
					List<NatureMap> query2 = natureService.list("DESC " + tableName);
					List<String> columns = new ArrayList<String>();
					for (NatureMap natureMap2 : query2) {
						Object object2 = natureMap2.get("field");
						String columnName = CastUtil.castString(object2);
						columns.add(columnName);
					}
					tableColumns.put(tableName, columns);
				}
			}
			generatorSql();
	}
	
	public static List<String> getSqlList() {
		return sqlList;
	}

	public static boolean isTableExists(String tableName) {
		return tableColumns.containsKey(tableName);
	}

	public static boolean isColumnExists(String tableName, String column) {
		return tableColumns.get(tableName).contains(column);
	}

	private static void generatorSql() {
		Map<Class<? extends NatureMap>, TableBean> tableBeanMap = TableBeanHelper.getTableBeanMap();
		Collection<TableBean> tableBeans = tableBeanMap.values();
		for (TableBean tableBean : tableBeans) {
			String tableName = tableBean.getTableName();
			if (isTableExists(tableName)) {
				alertTableSql(tableBean, tableName);
			} else {
				createTableSql(tableBean, tableName);
			}
		}

	}

	private static void alertTableSql(TableBean tableBean, String tableName) {
		String primaryKey = tableBean.getPrimaryKey();
		Strategy strategy = tableBean.getStrategy();
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName);
		Collection<FieldBean> fieldBeans = tableBean.getColumnFieldMap().values();
		StringBuffer key = new StringBuffer();
		StringBuffer columns = new StringBuffer();
		for (FieldBean fieldBean : fieldBeans) {
			String columnName = fieldBean.getColumnName();
			Types columnType = fieldBean.getColumnType();
			String columnLength = fieldBean.getColumnLength();
			String defValue = fieldBean.getDefValue();
			if (!isColumnExists(tableName, columnName)) {
				if (primaryKey.equals(columnName)) {
					key.append(" ADD ").append(primaryKey).append(" ").append(DbTypeTransform.getDbType(columnType)).append("(")
							.append(columnLength).append(")");
					key.append(" NOT NULL ");
					if (strategy == Strategy.ADDSELF) {
						key.append("AUTO_INCREMENT ");
					}
					key.append("PRIMARY KEY ,");
				} else {
					columns.append(" ADD ").append(columnName).append(" ").append(DbTypeTransform.getDbType(columnType)).append("(")
							.append(columnLength).append(")");
					if (StringUtil.isNotEmpty(defValue)) {
						columns.append(" DEFAULT ");
						if (columnType == Types.STRING) {
							columns.append("'").append(defValue).append("'");
						} else {
							columns.append(defValue);
						}
						columns.append(",");
					} else {
						columns.append(" DEFAULT NULL ").append(",");
					}

				}
			}
		}
		if (columns.length()>0||key.length()>0) {
			key.append(columns);
			key.deleteCharAt(key.length() - 1);
			sb.append(key);
			sqlList.add(sb.toString());
		}
	}

	private static void createTableSql(TableBean tableBean, String tableName) {
		String primaryKey = tableBean.getPrimaryKey();
		Strategy strategy = tableBean.getStrategy();
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE ").append(tableName).append(" ( ");
		Collection<FieldBean> fieldBeans = tableBean.getColumnFieldMap().values();
		StringBuffer key = new StringBuffer();
		StringBuffer columns = new StringBuffer();
		for (FieldBean fieldBean : fieldBeans) {
			String columnName = fieldBean.getColumnName();
			Types columnType = fieldBean.getColumnType();
			String columnLength = fieldBean.getColumnLength();
			String defValue = fieldBean.getDefValue();
			if (primaryKey.equals(columnName)) {
				key.append(primaryKey).append(" ").append(DbTypeTransform.getDbType(columnType)).append("(")
						.append(columnLength).append(")");
				key.append(" NOT NULL ");
				if (strategy == Strategy.ADDSELF) {
					key.append("AUTO_INCREMENT ");
				}
				key.append("PRIMARY KEY ,");
			} else {
				columns.append(columnName).append(" ").append(DbTypeTransform.getDbType(columnType)).append("(")
						.append(columnLength).append(")");
				if (StringUtil.isNotEmpty(defValue)) {
					columns.append(" DEFAULT ");
					if (columnType == Types.STRING) {
						columns.append("'").append(defValue).append("'");
					} else {
						columns.append(defValue);
					}
					columns.append(",");
				} else {
					columns.append(" DEFAULT NULL ").append(",");
				}

			}
		}
		
		key.append(columns);
		key.deleteCharAt(key.length() - 1);
		sb.append(key).append(" )");
		sqlList.add(sb.toString());
	}

}
