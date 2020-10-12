package site.yaotang.xgen.orm.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import site.yaotang.xgen.orm.mapping.Id;
import site.yaotang.xgen.orm.mapping.Property;

/**
 * 反射工具
 * @author hyt
 * @date 2018年1月1日
 */
@Data
@Slf4j
public class ReflectUtil {

	public static Object getValue(String className, Object object, String fieldName) {
		try {
			return getValue(Class.forName(className), object, fieldName);
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException();
		}
	}

	public static Object getValue(Class<?> clazz, Object object, String fieldName) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(object);
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException();
		}
	}

	public static Object getValue(Object object, String fieldName) {
		try {
			Class<?> clazz = object.getClass();
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(object);
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException();
		}
	}

	public static void setValue(String className, Object object, String fieldName, Object value) {
		try {
			setValue(Class.forName(className), object, fieldName, value);
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException();
		}
	}

	public static void setValue(Class<?> clazz, Object object, String fieldName, Object value) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(object, value);
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException();
		}
	}

	public static void setIdValue(Class<?> clazz, Object object, Id id, ResultSet resultSet) {
		try {
			ReflectUtil.setValue(clazz, object, id.getName(), resultSet.getObject(id.getColumn()));
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException();
		}
	}

	public static void setPropertyValue(Class<?> clazz, Object object, Property property, ResultSet resultSet) {
		try {
			ReflectUtil.setValue(clazz, object, property.getName(), resultSet.getObject(property.getColumn()));
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException();
		}
	}

	public static void setPropertyValue(Class<?> clazz, Object object, List<Property> properties, ResultSet resultSet) {
		try {
			for (Property property : properties) {
				ReflectUtil.setValue(clazz, object, property.getName(), resultSet.getObject(property.getColumn()));
			}
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException();
		}
	}

	public static Object newInstance(String className) {
		try {
			return Class.forName(className).newInstance();
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException(e);
		}
	}

	public static Object newInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException(e);
		}
	}

	public static Field getField(Class<?> clazz, String name) {
		try {
			Field[] declaredFields = clazz.getDeclaredFields();
			for (Field field : declaredFields) {
				if (field.getName().equals(name)) {
					return field;
				}
			}
			return null;
		} catch (Exception e) {
			LogUtil.error(log, e);
			throw new RuntimeException(e);
		}
	}
}
