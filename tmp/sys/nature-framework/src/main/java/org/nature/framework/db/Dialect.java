package org.nature.framework.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nature.framework.bean.FieldBean;
import org.nature.framework.bean.Page;
import org.nature.framework.bean.TableBean;
import org.nature.framework.core.NatureMap;
import org.nature.framework.enums.Strategy;
import org.nature.framework.enums.Types;
import org.nature.framework.helper.TableBeanHelper;
import org.nature.framework.util.CastUtil;
import org.nature.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Dialect implements DialectIntferface {
	private static Logger LOGGER = LoggerFactory.getLogger(MySqlDialect.class);
	
	public String tranformInsertSql(NatureMap natureMap, Class<? extends NatureMap> cls, TableBean tableBean) {
		String tableName = tableBean.getTableName();
		String primaryKey = tableBean.getPrimaryKey();
		Strategy strategy = tableBean.getStrategy();
		if (strategy == Strategy.UUID) {
			PrimarkeyStrategy.putUuid(primaryKey, natureMap);
		}

		Map<String, FieldBean> columnFieldMap = tableBean.getColumnFieldMap();
		Set<String> keySet = columnFieldMap.keySet();
		StringBuffer colums = new StringBuffer();
		StringBuffer values = new StringBuffer();
		for (String column : keySet) {
			if (strategy == Strategy.ADDSELF) {
				if (primaryKey.equals(column)) {
					continue;
				}
			}
			FieldBean fieldBean = columnFieldMap.get(column);
			Types columnType = fieldBean.getColumnType();
			Object value = natureMap.get(column);
			if (value != null) {
				apendColumnAndValue(colums, values, column, columnType, value);
			} else {
				String defaultValue = fieldBean.getDefValue();
				if (StringUtil.isNotBank(defaultValue)) {
					apendColumnAndValue(colums, values, column, columnType, defaultValue);
				}
			}
		}
		StringBuffer sql = new StringBuffer();
		if (colums.length() > 0) {
			colums.deleteCharAt(colums.length() - 1);
			values.deleteCharAt(values.length() - 1);
			sql.append("INSERT INTO ").append(tableName).append(" (");
			sql.append(colums);
			sql.append(") ").append("VALUES (");
			sql.append(values);
			sql.append(")");
		}
		return sql.toString();
	}

	public Object[] tranformInsertSqlWithParams(NatureMap natureMap, Class<? extends NatureMap> cls,
			TableBean tableBean) {
		List<Object> params = new ArrayList<Object>();
		String tableName = tableBean.getTableName();
		String primaryKey = tableBean.getPrimaryKey();
		Strategy strategy = tableBean.getStrategy();
		if (strategy == Strategy.UUID) {
			PrimarkeyStrategy.putUuid(primaryKey, natureMap);
		}

		Map<String, FieldBean> columnFieldMap = tableBean.getColumnFieldMap();
		Set<String> keySet = columnFieldMap.keySet();
		StringBuffer colums = new StringBuffer();
		StringBuffer values = new StringBuffer();
		for (String column : keySet) {
			if (strategy == Strategy.ADDSELF) {
				if (primaryKey.equals(column)) {
					continue;
				}
			}
			FieldBean fieldBean = columnFieldMap.get(column);
			Types columnType = fieldBean.getColumnType();
			Object value = natureMap.get(column);
			if (value != null) {
				apendColumnAndValue(colums, values, column, columnType, value, params);
			} else {
				String defaultValue = fieldBean.getDefValue();
				if (StringUtil.isNotBank(defaultValue)) {
					apendColumnAndValue(colums, values, column, columnType, defaultValue, params);
				}
			}
		}
		StringBuffer sql = new StringBuffer();
		if (colums.length() > 0) {
			colums.deleteCharAt(colums.length() - 1);
			values.deleteCharAt(values.length() - 1);
			sql.append("INSERT INTO ").append(tableName).append(" (");
			sql.append(colums);
			sql.append(") ").append("VALUES (");
			sql.append(values);
			sql.append(")");
		}
		return new Object[] { sql.toString(), params.toArray() };
	}

	public String tranformUpdateSql(NatureMap natureMap) {
		Class<? extends NatureMap> cls = natureMap.getClass();
		TableBean tableBean = TableBeanHelper.getTableBean(cls);
		String tableName = tableBean.getTableName();
		String primaryKey = tableBean.getPrimaryKey();
		Object primaryKeyValue = natureMap.get(primaryKey);
		if (primaryKeyValue == null) {
			LOGGER.error("bean " + cls + " primary is null");
			throw new RuntimeException("bean " + cls + " primary is null");
		}

		Map<String, FieldBean> columnFieldMap = tableBean.getColumnFieldMap();
		Set<String> keySet = columnFieldMap.keySet();
		StringBuffer columValues = new StringBuffer();
		for (String column : keySet) {
			if (!primaryKey.equals(column)) {
				FieldBean fieldBean = columnFieldMap.get(column);
				Types columnType = fieldBean.getColumnType();
				Object value = natureMap.get(column);
				if (value != null) {
					apendColumnEqValue(columValues, column, columnType, value);
				}
			}
		}
		StringBuffer sql = new StringBuffer();
		if (columValues.length() > 0) {
			columValues.deleteCharAt(columValues.length() - 1);
			sql.append("UPDATE ").append(tableName).append(" SET");
			sql.append(columValues);
			sql.append(" WHERE ").append(primaryKey).append(" = '").append(primaryKeyValue).append("'");
		}
		return sql.toString();
	}

	public Object[] tranformUpdateSqlWithParams(NatureMap natureMap) {
		List<Object> params = new ArrayList<Object>();
		Class<? extends NatureMap> cls = natureMap.getClass();
		TableBean tableBean = TableBeanHelper.getTableBean(cls);
		String tableName = tableBean.getTableName();
		String primaryKey = tableBean.getPrimaryKey();
		Object primaryKeyValue = natureMap.get(primaryKey);
		if (primaryKeyValue == null) {
			LOGGER.error("bean " + cls + " primary is null");
			throw new RuntimeException("bean " + cls + " primary is null");
		}

		Map<String, FieldBean> columnFieldMap = tableBean.getColumnFieldMap();
		Set<String> keySet = columnFieldMap.keySet();
		StringBuffer columValues = new StringBuffer();
		for (String column : keySet) {
			if (!primaryKey.equals(column)) {
				FieldBean fieldBean = columnFieldMap.get(column);
				Types columnType = fieldBean.getColumnType();
				Object value = natureMap.get(column);
				if (value != null) {
					apendColumnEqValue(columValues, column, columnType, value, params);
				}
			}
		}
		StringBuffer sql = new StringBuffer();
		if (columValues.length() > 0) {
			columValues.deleteCharAt(columValues.length() - 1);
			sql.append("UPDATE ").append(tableName).append(" SET");
			sql.append(columValues);
			sql.append(" WHERE ").append(primaryKey).append(" = ?");
			params.add(primaryKeyValue);
		}
		return new Object[] { sql.toString(), params.toArray() };
	}

	public String tranformDeleteSql(NatureMap natureMap) {
		Class<? extends NatureMap> cls = natureMap.getClass();
		TableBean tableBean = TableBeanHelper.getTableBean(cls);
		String tableName = tableBean.getTableName();
		String primaryKey = tableBean.getPrimaryKey();
		Object primaryKeyValue = natureMap.get(primaryKey);
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ").append(tableName).append(" WHERE ").append(primaryKey).append(" = '")
				.append(primaryKeyValue).append("'");
		return sql.toString();
	}

	public String tranformByIdSql(NatureMap natureMap) {
		Class<? extends NatureMap> cls = natureMap.getClass();
		TableBean tableBean = TableBeanHelper.getTableBean(cls);
		String tableName = tableBean.getTableName();
		String primaryKey = tableBean.getPrimaryKey();
		Object primaryKeyValue = natureMap.get(primaryKey);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ").append(tableName).append(" WHERE ").append(primaryKey).append(" = '")
				.append(primaryKeyValue).append("'");
		return sql.toString();
	}

	private void apendColumnEqValue(StringBuffer columValues, String column, Types columnType, Object value) {
		if (Types.STRING == columnType) {
			columValues.append(" ").append(column).append(" = '").append(value).append("',");
		} else if (Types.INT == columnType) {// TODO 完善数据库映射类型
			if (StringUtil.isNotBank(CastUtil.castString(value))) {
				columValues.append(" ").append(column).append(" = ").append(value).append(",");
			}

		} else {
			columValues.append(" ").append(column).append(" = ").append(value).append(",");
		}
	}

	private void apendColumnEqValue(StringBuffer columValues, String column, Types columnType, Object value,
			List<Object> params) {
		columValues.append(" ").append(column).append(" = ?,");
		params.add(value);
	}

	private void apendColumnAndValue(StringBuffer colums, StringBuffer values, String column, Types columnType,
			Object value, List<Object> params) {
		colums.append(column).append(",");
		values.append("?,");
		params.add(value);
	}

	private void apendColumnAndValue(StringBuffer colums, StringBuffer values, String column, Types columnType,
			Object value) {
		if (Types.STRING == columnType) {
			colums.append(column).append(",");
			values.append("'").append(value).append("'").append(",");
		} else if (Types.INT == columnType) {
			if (StringUtil.isNotBank(CastUtil.castString(value))) {
				colums.append(column).append(",");
				values.append("'").append(value).append("'").append(",");
			}
		} else {
			colums.append(column).append(",");
			values.append(value).append(",");
		}
	}
	
	public String tranformCntSql(String sql) {
		String orderBySql = sql.substring(sql.toLowerCase().lastIndexOf("order by"));
		String[] fromSqls = sql.replaceFirst(orderBySql, "").split(" [f|F][r|R][o|O][m|M] ");
		StringBuffer fromSql = new StringBuffer();
		int length = fromSqls.length;
		for (int i = 0; i < length; i++) {
			if (i>0) {
				fromSql.append(" FROM ");
				fromSql.append(fromSqls[i]);
			}
		}
		return "SELECT COUNT(*) "+fromSql.toString();
	}

	public abstract String tranformPageSql(Page page, String sql);

	

}
