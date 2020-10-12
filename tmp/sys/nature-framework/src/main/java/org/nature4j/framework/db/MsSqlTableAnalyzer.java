package org.nature4j.framework.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nature4j.framework.bean.FieldBean;
import org.nature4j.framework.bean.TableBean;
import org.nature4j.framework.core.NatureMap;
import org.nature4j.framework.core.NatureService;
import org.nature4j.framework.enums.Strategy;
import org.nature4j.framework.enums.Types;
import org.nature4j.framework.helper.DatabaseHelper;
import org.nature4j.framework.helper.ServiceHelper;
import org.nature4j.framework.helper.TableBeanHelper;
import org.nature4j.framework.util.ArrayUtil;
import org.nature4j.framework.util.CastUtil;
import org.nature4j.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库观察者，用来判断数据库的表和字段是否存在
 */
public class MsSqlTableAnalyzer {
	private static Logger LOGGER = LoggerFactory.getLogger(MsSqlTableAnalyzer.class);
	private static Map<String, List<String>> tableColumns = new HashMap<String, List<String>>();
	private static List<String> sqlList = new ArrayList<String>();
	Db natureDb ;
	public static MsSqlTableAnalyzer use(Db natureDb) {
		MsSqlTableAnalyzer sqlTableAnalyzer = new MsSqlTableAnalyzer();
		sqlTableAnalyzer.natureDb = natureDb;
		return sqlTableAnalyzer;
	}

	public List<String> getSqlList() {
		String jdbcDriver = DatabaseHelper.use(natureDb).jdbcDriver;
		NatureService natureService = (NatureService) ServiceHelper.getService(NatureService.class);
		List<NatureMap> query1 = natureService.list(natureDb,"SELECT name FROM SysObjects WHERE XType='U' ");
		for (NatureMap natureMap : query1) {
			Set<String> keySet = natureMap.keySet();
			for (String key : keySet) {
				Object object = natureMap.get(key);
				String tableName = CastUtil.castString(object);
				List<NatureMap> query2 = natureService.list(natureDb,"SELECT name FROM SysColumns WHERE ID=Object_Id('" + tableName + "')");
				List<String> columns = new ArrayList<String>();
				for (NatureMap natureMap2 : query2) {
					Object object2 = natureMap2.get("name");
					String columnName = CastUtil.castString(object2);
					columns.add(columnName);
				}
				tableColumns.put(tableName, columns);
			}
		}
		generatorSql(jdbcDriver);
		return sqlList;
	}

	public boolean isTableExists(String tableName) {
		return tableColumns.containsKey(tableName);
	}

	public boolean isColumnExists(String tableName, String column) {
		return tableColumns.get(tableName).contains(column);
	}

	private void generatorSql(String jdbcDriver) {
		Map<Class<? extends NatureMap>, TableBean> tableBeanMap = TableBeanHelper.getTableBeanMap();
		Collection<TableBean> tableBeans = tableBeanMap.values();
		String dbName = natureDb.name();
		for (TableBean tableBean : tableBeans) {
			if(!ArrayUtil.isExsit(tableBean.getDbs(), dbName.replace(".", ""))){
				continue;
			}
			String tableName = tableBean.getTableName();
			if (isTableExists(tableName)) {
				alertTableSql(tableBean, tableName,jdbcDriver);
			} else {
				createTableSql(tableBean, tableName,jdbcDriver);
			}
		}

	}

	private void alertTableSql(TableBean tableBean, String tableName,String jdbcDriver) {
		String primaryKey = tableBean.getPrimaryKey();
		Strategy strategy = tableBean.getStrategy();
		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName).append(" ADD ");
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
					key.append(primaryKey).append(" ").append(DbTypeTransform.getDbType(columnType,columnLength,jdbcDriver));
					if (strategy == Strategy.ADDSELF) {
						key.append("IDENTITY(1,1) ");
					}
					key.append(" NOT NULL ");
					key.append("PRIMARY KEY ,");
				} else {
					columns.append(columnName).append(" ").append(DbTypeTransform.getDbType(columnType,columnLength,jdbcDriver)).append(" ");
					if (StringUtil.isNotEmpty(defValue)) {
						columns.append(" DEFAULT ");
						if (columnType == Types.STRING) {
							columns.append("'").append(defValue).append("'");
						} else {
							columns.append(defValue);
						}
					}
					columns.append(",");
				}
			}
		}
		if (columns.length() > 0 || key.length() > 0) {
			key.append(columns);
			key.deleteCharAt(key.length() - 1);
			sb.append(key);
			sqlList.add(sb.toString());
		}
	}

	private void createTableSql(TableBean tableBean, String tableName,String jdbcDriver) {
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
				key.append(primaryKey).append(" ").append(DbTypeTransform.getDbType(columnType,columnLength,jdbcDriver));
				if (strategy == Strategy.ADDSELF) {
					key.append(" IDENTITY(1,1) ");
				}
				key.append(" NOT NULL PRIMARY KEY ,");
			} else {
				columns.append(columnName).append(" ").append(DbTypeTransform.getDbType(columnType,columnLength,jdbcDriver)).append(" ");
				if (StringUtil.isNotEmpty(defValue)) {
					columns.append(" DEFAULT ");
					if (columnType == Types.STRING) {
						columns.append("'").append(defValue).append("'");
					} else {
						columns.append(defValue);
					}
				} 
				columns.append(",");
			}
		}
		key.append(columns);
		key.deleteCharAt(key.length() - 1);
		sb.append(key).append(" )");
		sqlList.add(sb.toString());
	}

}
