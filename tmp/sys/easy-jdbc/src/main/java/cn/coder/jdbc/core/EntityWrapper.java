package cn.coder.jdbc.core;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.jdbc.annotation.Column;
import cn.coder.jdbc.annotation.Id;
import cn.coder.jdbc.annotation.Table;
import cn.coder.jdbc.util.FieldUtils;

public final class EntityWrapper {
	private static final Logger logger = LoggerFactory.getLogger(EntityWrapper.class);

	private String tableName;
	private Field generateKeyField;
	private ArrayList<String> primaryKeys;
	private HashMap<String, Object> columns;
	private Object obj;
	private SQLType type;
	private Object[] data;
	private String sql;

	public EntityWrapper(Class<?> clazz) {
		findTable(clazz);
		findColumns(clazz);
	}

	private void findTable(Class<?> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		if (table == null)
			throw new NullPointerException("The 'Table' annotation not found");
		this.tableName = table.value();
	}

	public EntityWrapper setObject(Object data, SQLType type) {
		this.obj = data;
		this.type = type;
		fillColumns();
		switch (type) {
		case SELECT:
			buildSelect();
			break;
		case INSERT:
			buildInsert();
			break;
		case UPDATE:
			buildUpdate();
			break;
		case DELETE:
			buildDelete();
			break;
		default:
			break;
		}
		return this;
	}

	public String getSql() {
		return this.sql;
	}

	public SQLType getSqlType() {
		return this.type;
	}

	public Object[] getData() {
		return this.data;
	}

	public boolean returnGeneratedKey() {
		return type == SQLType.INSERT && generateKeyField != null;
	}

	public void setGeneratedKey(int value) {
		try {
			FieldUtils.setValue(this.generateKeyField, this.obj, value);
		} catch (SQLException e) {
			logger.error("主键赋值失败", e);
		}
	}

	public void clear() {
		Set<String> keys = columns.keySet();
		for (String key : keys) {
			columns.put(key, null);
		}
		this.obj = null;
		this.type = null;
		this.sql = null;
		this.data = null;
	}

	private void findColumns(Class<?> clazz) {
		Column col;
		this.columns = new HashMap<>();
		this.primaryKeys = new ArrayList<>();
		Set<Field> fields = FieldUtils.getDeclaredFields(clazz);
		int num = 0;
		for (Field field : fields) {
			col = field.getAnnotation(Column.class);
			if (col != null) {
				num++;
				columns.put(col.value(), null);
				Id id = field.getAnnotation(Id.class);
				if (id != null) {
					primaryKeys.add(col.value());
					if (id.value()) {
						this.generateKeyField = field;
					}
				}
			}
		}
		if (num == 0)
			throw new NullPointerException("The 'Column' annotation not found");
	}

	private void fillColumns() {
		Column col;
		Set<Field> fields = FieldUtils.getDeclaredFields(obj.getClass());
		for (Field field : fields) {
			col = field.getAnnotation(Column.class);
			if (col != null && columns.containsKey(col.value())) {
				try {
					if (!field.isAccessible())
						field.setAccessible(true);
					columns.put(col.value(), field.get(obj));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.error("字段赋值失败", e);
				}
			}
		}
	}

	private void buildSelect() {
		try {
			long start = System.nanoTime();
			List<Object> args = new ArrayList<>();
			StringBuffer sql1 = new StringBuffer();
			Object obj = null;
			Set<String> keys = columns.keySet();
			for (String key : keys) {
				obj = columns.get(key);
				if (obj != null) {
					sql1.append("`" + key + "`=? AND ");
					args.add(obj);
				}
			}
			if (sql1.length() > 0)
				sql1.delete(sql1.length() - 4, sql1.length());
			this.sql = "SELECT COUNT(1) FROM `" + tableName + "` WHERE " + sql1.toString();
			if (logger.isDebugEnabled())
				logger.debug("Select(" + (System.nanoTime() - start) + "ns) => " + sql);
			this.data = new Object[args.size()];
			args.toArray(data);
		} catch (Exception e) {
			logger.error("Create select sql faild", e);
		}
	}

	private void buildInsert() {
		try {
			long start = System.nanoTime();
			List<Object> args = new ArrayList<>();
			StringBuffer sql1 = new StringBuffer("(");
			StringBuffer sql2 = new StringBuffer("(");
			Object obj = null;
			Set<String> keys = columns.keySet();
			for (String key : keys) {
				obj = columns.get(key);
				if (obj != null) {
					sql1.append("`" + key + "`,");
					sql2.append("?,");
					args.add(obj);
				}
			}
			sql1.deleteCharAt(sql1.length() - 1);
			sql1.append(")");
			sql2.deleteCharAt(sql2.length() - 1);
			sql2.append(")");
			this.sql = "INSERT INTO `" + tableName + "`" + sql1.toString() + " VALUES " + sql2.toString();
			if (logger.isDebugEnabled())
				logger.debug("Insert(" + (System.nanoTime() - start) + "ns) => " + sql);
			this.data = new Object[args.size()];
			args.toArray(data);
		} catch (Exception e) {
			logger.error("Create insert sql faild", e);
		}
	}

	private void buildUpdate() {
		try {
			long start = System.nanoTime();
			List<Object> args = new ArrayList<>();
			StringBuffer sql1 = new StringBuffer(" set ");
			Object obj = null;
			Set<String> keys = columns.keySet();
			for (String key : keys) {
				obj = columns.get(key);
				if (obj != null && !primaryKeys.contains(key)) {
					sql1.append("`" + key + "`=?,");
					args.add(obj);
				}
			}
			sql1.deleteCharAt(sql1.length() - 1);
			StringBuffer sql2 = new StringBuffer();
			if (!primaryKeys.isEmpty()) {
				for (String key : primaryKeys) {
					sql2.append("`" + key + "`=? AND ");
					args.add(columns.get(key));
				}
			}
			if (sql2.length() > 0)
				sql2.delete(sql2.length() - 4, sql2.length());
			this.sql = "UPDATE `" + tableName + "`" + sql1.toString() + " WHERE " + sql2.toString();
			if (logger.isDebugEnabled())
				logger.debug("Update(" + (System.nanoTime() - start) + "ns) => " + sql);
			this.data = new Object[args.size()];
			args.toArray(data);
		} catch (Exception e) {
			logger.error("Create update sql faild", e);
		}
	}

	private void buildDelete() {
		try {
			long start = System.nanoTime();
			List<Object> args = new ArrayList<>();
			StringBuffer sql1 = new StringBuffer();
			Object obj = null;
			Set<String> keys = columns.keySet();
			for (String key : keys) {
				obj = columns.get(key);
				if (obj != null) {
					sql1.append("`" + key + "`=? AND ");
					args.add(obj);
				}
			}
			if (sql1.length() > 0)
				sql1.delete(sql1.length() - 4, sql1.length());
			this.sql = "DELETE FROM `" + tableName + "` WHERE " + sql1.toString();
			if (logger.isDebugEnabled())
				logger.debug("Delete(" + (System.nanoTime() - start) + "ns) => " + sql);
			this.data = new Object[args.size()];
			args.toArray(data);
		} catch (Exception e) {
			logger.error("Create delete sql faild", e);
		}
	}

	public enum SQLType {
		SELECT, INSERT, UPDATE, DELETE
	}
}