package org.nature.framework.helper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.nature.framework.annotation.Id;
import org.nature.framework.annotation.Table;
import org.nature.framework.annotation.Type;
import org.nature.framework.bean.FieldBean;
import org.nature.framework.bean.TableBean;
import org.nature.framework.core.NatureMap;
import org.nature.framework.enums.Strategy;
import org.nature.framework.enums.Types;
import org.nature.framework.util.CastUtil;
import org.nature.framework.util.ClassUtil;
import org.nature.framework.util.ReflectionUtil;
import org.nature.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableBeanHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(TableBeanHelper.class);
	private static Map<Class<? extends NatureMap>, TableBean> tableBeanMap = new HashMap<Class<? extends NatureMap>, TableBean>();

	@SuppressWarnings("unchecked")
	public static void initTableBeans() {
		Set<Class<?>> classSet = ClassUtil.getClassSet(ConfigHelper.getAppBasePackage(), Table.class);
		for (Class<?> cls : classSet) {
			String tableName = cls.getAnnotation(Table.class).name();
			if (StringUtil.isBank(tableName)) {
				LOGGER.error("class " + cls + " @Table's name can not null or bank ");
				throw new RuntimeException("class " + cls + " @Table's name can not null or bank ");
			}
			Object instance = ReflectionUtil.newInstance(cls);
			Map<String, FieldBean> columnFieldMap = new HashMap<String, FieldBean>();
			Field[] fields = cls.getFields();
			String primaryKey = null;
			Strategy strategy = null;
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				String columnName = CastUtil.castString(ReflectionUtil.getFieldValue(instance, field));
				Types columnType = Types.STRING;
				String columnLenght ="255";
				String defaultValue = null;
				if (field.isAnnotationPresent(Type.class)) {
					Type typeInfo = field.getAnnotation(Type.class);
					columnLenght = typeInfo.length();
					columnType = typeInfo.type();
					defaultValue = typeInfo.def();
					if ("".equals(columnType)) {
						columnType=Types.STRING;
					}
				}
				
				if (field.isAnnotationPresent(Id.class)) {
					Id id = field.getAnnotation(Id.class);
					strategy = id.strategy();
					primaryKey = columnName;
				}
				
				FieldBean fieldBean = new FieldBean(fieldName, columnName, columnType, columnLenght,defaultValue);
				columnFieldMap.put(columnName, fieldBean);
			}
			if (StringUtil.isBank(primaryKey)) {
				LOGGER.error("the bean "+cls.getName()+" has not a primaryKey ");
				throw new RuntimeException("the bean "+cls.getName()+" has not a primaryKey ");
			}
			TableBean tableBean = new TableBean(tableName, columnFieldMap,primaryKey,strategy);
			tableBeanMap.put((Class<? extends NatureMap>) cls, tableBean);
		}
	}
	
	public static Map<Class<? extends NatureMap>, TableBean> getTableBeanMap() {
		return tableBeanMap;
	}

	public static TableBean getTableBean(Class<? extends NatureMap> cls) {
		TableBean tableBean = tableBeanMap.get(cls);
		if (tableBean == null) {
			LOGGER.error(cls.getName() + " is not init or forget add @Table annotation ");
		}
		return tableBean;
	}
}
