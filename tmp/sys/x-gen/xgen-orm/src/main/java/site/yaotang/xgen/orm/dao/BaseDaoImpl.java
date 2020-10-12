package site.yaotang.xgen.orm.dao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import site.yaotang.xgen.orm.cache.CacheFactory;
import site.yaotang.xgen.orm.helper.DBHelper;
import site.yaotang.xgen.orm.mapping.Id;
import site.yaotang.xgen.orm.mapping.ManyToOne;
import site.yaotang.xgen.orm.mapping.Property;
import site.yaotang.xgen.orm.mapping.Table;
import site.yaotang.xgen.orm.sqltools.BQLTools;
import site.yaotang.xgen.orm.sqltools.DetachCriteria;
import site.yaotang.xgen.orm.tools.Constant;
import site.yaotang.xgen.orm.utils.CollectionUtil;
import site.yaotang.xgen.orm.utils.LogUtil;
import site.yaotang.xgen.orm.utils.ReflectUtil;

/**
 * 书本数据库库操作类
 * @author hyt
 * @date 2017年12月31日
 */
@Slf4j
public class BaseDaoImpl implements BaseDao {

	@Override
	public void save(Object obj) {
		Class<?> clazz = obj.getClass();
		// 获取表信息
		Table table = Constant.TABLEMAP.get(clazz.getName());
		Id id = table.getId();
		List<ManyToOne> manyList = table.getManyList();
		List<Property> properties = table.getProperties();
		// sql模板
		String sql = "insert into %s (%s) values (%s)";
		List<String> columns = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			try {
				field.setAccessible(true);
				// 属性值
				Object object = field.get(obj);
				// 属性名
				String name = field.getName();
				if (id.getName().equals(name)) {
					if (!id.isAutoIncrement()) {
						// 反射ID
						String column = id.getColumn();
						columns.add(column);
						values.add(object);
						continue;
					}
				} else {
					for (Property property : properties) {
						// 反射属性
						if (property.getName().equals(name)) {
							String column = property.getColumn();
							if (object != null) {
								columns.add(column);
								values.add(object);
							} else {
								Object defaultValue = property.getDefaultValue();
								if (defaultValue != null) {
									columns.add(column);
									values.add(defaultValue);
								}
							}
							break;
						}
					}
					// ManyToOne 多对一，在数据库中只是保留外键的值
					if (object != null) {
						for (ManyToOne manyToOne : manyList) {
							// 反射外键
							String mName = manyToOne.getName();
							if (name.equals(mName)) {
								Object mValue = ReflectUtil.getValue(object, manyToOne.getField());
								if (mValue != null) {
									columns.add(manyToOne.getColumn());
									values.add(mValue);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				LogUtil.error(log, e);
				throw new RuntimeException(e);
			}
		}
		if (CollectionUtil.isEmpty(columns)) {
			LogUtil.error(log, "没有插入的字段");
			throw new RuntimeException("没有插入的字段");
		}
		String formatSql = String.format(sql, table.getTableName(), BQLTools.joinColumns(columns), BQLTools.joinValues(values));
		DBHelper.executeWrite(formatSql);
	}

	@Override
	public void update(Object obj) {
		Class<?> clazz = obj.getClass();
		// 获取表信息
		Table table = Constant.TABLEMAP.get(clazz.getName());

		Id id = table.getId();
		List<ManyToOne> manyList = table.getManyList();
		List<Property> properties = table.getProperties();
		// sql
		String sql = "update " + table.getTableName() + " set ";
		String whereSql = " where ";
		List<String> columns = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			try {
				field.setAccessible(true);
				// 属性值
				Object object = field.get(obj);
				// 属性名
				String name = field.getName();
				if (id.getName().equals(name)) {
					if (object instanceof String) {
						object = "'" + object + "'";
					}
					// where后面只支持id
					whereSql += id.getColumn() + "=" + object;
					continue;
				} else {
					for (Property property : properties) {
						// 反射属性
						if (property.getName().equals(name)) {
							if (object != null) {
								String column = property.getColumn();
								columns.add(column);
								values.add(object);
							}
							break;
						}
					}
					// ManyToOne 多对一，在数据库中只是保留外键的值
					if (object != null) {
						for (ManyToOne manyToOne : manyList) {
							// 反射外键
							String mName = manyToOne.getName();
							if (name.equals(mName)) {
								Object mValue = ReflectUtil.getValue(object, manyToOne.getField());
								columns.add(manyToOne.getColumn());
								values.add(mValue);
							}
						}
					}
				}
			} catch (Exception e) {
				LogUtil.error(log, e);
				throw new RuntimeException(e);
			}
		}
		if (CollectionUtil.isEmpty(columns)) {
			LogUtil.error(log, "没有更新的字段");
			throw new RuntimeException("没有更新的字段");
		}
		String executeSql = sql + BQLTools.joinColumnsValues(columns, values) + whereSql;
		DBHelper.executeWrite(executeSql);
	}

	@Override
	public void delete(Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object query(String sql) {
		// 懒得做结果解析了
		try {
			Object result = null;
			ResultSet resultSet = DBHelper.executeRead(sql);
			while (resultSet.next()) {
				result = resultSet.getObject(1);
			}
			return result;
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<?> queryList(String sql) {
		// 懒得做结果解析了
		try {
			List<Object> list = new ArrayList<>();
			ResultSet resultSet = DBHelper.executeRead(sql);
			while (resultSet.next()) {
				list.add(resultSet.getObject(1));
			}
			return list;
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<?> queryList(DetachCriteria dc) {
		return queryList(dc.getSQLString());
	}

	@Override
	public Object queryFromCache(String name, String sql, long seconds) {
		Object result = CacheFactory.get(name);
		if (result == null) {
			result = query(sql);
			CacheFactory.put(name, result, seconds);
		}
		return result;
	}

}
