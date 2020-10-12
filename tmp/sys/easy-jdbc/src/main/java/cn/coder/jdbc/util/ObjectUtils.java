package cn.coder.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.jdbc.core.BeanMapping;

public final class ObjectUtils {
	private static final Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

	public static Object[] mergeArray(Object[] array, Object... objs) {
		if (array.length == 0 && objs.length == 0)
			return new Object[0];
		if (array.length == 0)
			return objs;
		if (objs.length == 0)
			return array;
		Object[] temp = new Object[array.length + objs.length];
		System.arraycopy(array, 0, temp, 0, array.length);
		System.arraycopy(objs, 0, temp, array.length, objs.length);
		return temp;
	}

	public static Integer toInteger(Object value) {
		if ("".equals(value))
			return null;
		return Integer.parseInt(value.toString());
	}

	public static Long toLong(Object value) {
		if ("".equals(value))
			return null;
		if (value instanceof Date)
			return ((Date) value).getTime();
		return Long.parseLong(value.toString());
	}

	public static Double toDouble(Object value) {
		if ("".equals(value))
			return null;
		return Double.parseDouble(value.toString());
	}

	public static Float toFloat(Object value) {
		if ("".equals(value))
			return null;
		return Float.parseFloat(value.toString());
	}

	public static Boolean toBoolean(Object value) {
		if ("".equals(value))
			return null;
		return Boolean.parseBoolean(value.toString());
	}

	public static Short toShort(Object value) {
		if ("".equals(value))
			return null;
		return Short.parseShort(value.toString());
	}

	public static Byte toByte(Object value) {
		if ("".equals(value))
			return null;
		return Byte.parseByte(value.toString());
	}

	public static Character toChar(Object value) {
		if ("".equals(value))
			return null;
		return Character.valueOf((char) value);
	}

	public static Date toDate(Object value) {
		if (value == null)
			return null;
		if (value instanceof Timestamp)
			return (Date) value;
		int len = value.toString().length();
		String format;
		if (len == 8)
			format = "yyyyMMdd";
		else if (len == 10)
			format = "yyyy-MM-dd";
		else if (len == 14)
			format = "yyyyMMddHHmmss";
		else if (len == 19)
			format = "yyyy-MM-dd HH:mm:ss";
		else
			throw new NullPointerException("Unsuppord date length " + len);
		return toDate(value.toString(), format);
	}

	public static Date toDate(String str, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(str);
		} catch (ParseException e) {
			logger.error("Parse date faild", e);
			return null;
		}
	}

	public static Object createBean(Class<?> target, String[] labels, ResultSet rs, BeanMapping mappings) {
		try {
			Object bean = target.newInstance();
			Object obj;
			for (String label : labels) {
				obj = rs.getObject(label);
				if (obj != null) {
					FieldUtils.setValue(mappings.get(label), bean, obj);
				}
			}
			return bean;
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			logger.error("Create bean '" + target.getName() + "' faild.");
			return null;
		}
	}

	public static <T> T copyBean(Class<T> clazz, Object obj) throws SQLException {
		try {
			T t = clazz.newInstance();
			Set<Field> fields = FieldUtils.getDeclaredFields(clazz);
			Set<Field> fields2 = FieldUtils.getDeclaredFields(obj.getClass());
			for (Field field : fields) {
				for (Field field2 : fields2) {
					if (field.getName().equals(field2.getName())) {
						if (!field2.isAccessible())
							field2.setAccessible(true);
						FieldUtils.setValue(field, t, field2.get(obj));
					}
				}
			}
			return t;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SQLException e) {
			throw new SQLException("Copy bean faild", e);
		}
	}

	public static Properties loadProperties(String conf) {
		try {
			InputStream input = ObjectUtils.class.getClassLoader().getResourceAsStream(conf);
			if (input != null) {
				Properties properties = new Properties();
				properties.load(input);
				input.close();
				return properties;
			}
		} catch (IOException e) {
			if (logger.isErrorEnabled())
				logger.error("Can not find the '{}' file", conf);
		}
		return null;
	}

}
