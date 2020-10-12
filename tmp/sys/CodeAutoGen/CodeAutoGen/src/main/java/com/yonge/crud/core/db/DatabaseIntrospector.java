package com.yonge.crud.core.db;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonge.crud.core.db.model.Column;
import com.yonge.crud.core.db.model.Table;
import com.yonge.crud.core.util.JavaBeansUtil;

public class DatabaseIntrospector {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DatabaseIntrospector.class);

	private DatabaseMetaData dbMetaData;

	public DatabaseIntrospector(DatabaseMetaData dbMetaData) {
		this.dbMetaData = dbMetaData;
	}

	public List<Table> getTables(String catalog, String schema) {
		List<Table> tables = new ArrayList<Table>();
		if (dbMetaData == null) {
			LOGGER.warn("DatabaseMetaData对象不能为空");
			return tables;
		}
		ResultSet resultSet = null;
		try {
			resultSet = dbMetaData.getTables(catalog, schema, null,
					new String[] { "TABLE" });
			Table table = null;
			while (resultSet.next()) {
				table = new Table();
				table.setName(resultSet.getString("TABLE_NAME"));
				table.setCatalog(resultSet.getString("TABLE_CAT"));
				table.setRemarks(resultSet.getString("REMARKS"));
				table.setSchema(resultSet.getString("TABLE_SCHEM"));
				table.setClassName(JavaBeansUtil.getCamelCaseString(
						table.getName(), true));

				table.setColumns(getColumns(table));
				tables.add(table);
			}
		} catch (SQLException e) {
			LOGGER.warn("获取数据库表失败", e);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					LOGGER.warn("关闭ResultSet对象出现异常", e);
				}
			}
		}
		return tables;
	}

	private List<Column> getColumns(Table table) {
		List<Column> columns = new ArrayList<Column>();
		if (table == null) {
			LOGGER.warn("参数不能为空");
			return columns;
		}
		String tableName = table.getName();
		String schema = table.getSchema();
		String name = tableName.toUpperCase();
		if (dbMetaData == null) {
			LOGGER.warn("DatabaseMetaData对象不能为空");
		}
		ResultSet resultSet = null;
		Column column = null;
		try {
			resultSet = dbMetaData.getPrimaryKeys(table.getCatalog(), schema,
					tableName);
			List<String> keyColumns = new ArrayList<String>();
			while (resultSet.next()) {
				keyColumns.add(resultSet.getString("COLUMN_NAME"));
			}

			resultSet = dbMetaData.getColumns(null, schema, name, null);
			while (resultSet.next()) {
				column = new Column();
				// 获得字段名称
				column.setFieldName(resultSet.getString("COLUMN_NAME"));
				// 获得字段类型名称
				column.setJdbcTypeName(resultSet.getString("TYPE_NAME"));
				// 数据类型
				column.setDataType(resultSet.getInt("DATA_TYPE"));
				// 获得字段大小
				column.setLength(resultSet.getInt("COLUMN_SIZE"));
				column.setNullable((resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable));
				column.setScale(resultSet.getInt("DECIMAL_DIGITS"));
				column.setRemarks(resultSet.getString("REMARKS"));
				for (String key : keyColumns) {
					if (StringUtils
							.equalsIgnoreCase(key, column.getFieldName())) {
						column.setIsPrimaryKey(true);
					}
				}
				columns.add(column);
			}
		} catch (SQLException e) {
			LOGGER.warn("在" + schema + "中获取" + name + "表字段出现异常", e);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					LOGGER.warn("关闭ResultSet对象出现异常", e);
				}
			}
		}
		return columns;
	}

}
