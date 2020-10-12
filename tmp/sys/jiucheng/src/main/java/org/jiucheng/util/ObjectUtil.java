/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jiucheng
 *
 */
public class ObjectUtil {

	public static byte[] objectToBytes(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutput oo = null;
		try {
			oo = new ObjectOutputStream(baos);
			oo.writeObject(obj);
			oo.flush();
			bytes = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != oo) {
					oo.close();
				}
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return bytes;
	}
	
	public static Object bytesToObject(byte[] bytes) {
		Object obj = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInput oi = null;
		try {
			oi = new ObjectInputStream(bais);
			obj = oi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				if(null != oi) {
					oi.close();
				}
				bais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
	
	//boolean byte char short int long float double 
	@SuppressWarnings("unchecked")
    public static <T> T toThis(Class<T> clazz, Object obj) {
		if(obj == null || clazz == null) {
			return null;
		}
		if(clazz.isInstance(obj)) {
			return (T) obj;
		}
		if(clazz == String.class) {
			return (T) obj.toString();
		}
		if(clazz == Boolean.class) {
			return (T) toBoolean(obj);
		}
		if(clazz == Byte.class) {
		    return (T) toByte(obj);
		}
		//char
		if(clazz == Character.class) {
		    return (T) toCharacter(obj);
		}
		if(clazz == Short.class) {
		    return (T) toShort(obj);
		}
		if(clazz == Integer.class) {
			return (T) toInteger(obj);
		}
		if(clazz == Long.class) {
			return (T) toLong(obj);
		}
		if(clazz == BigDecimal.class) {
			return (T) toBigDecimal(obj);
		}
        if(clazz == Float.class) {
            return (T) toFloat(obj);
        }
        if(clazz == Double.class) {
            return (T) toDouble(obj);
        }
		if(clazz == Date.class) {
			return (T) obj;
		}
		return null;
	}
	
	public static Boolean toBoolean(Object obj) {
		if(obj == null) {
			return null;
		}
		if(obj instanceof Boolean) {
			return (Boolean) obj;
		}
		if("true".equalsIgnoreCase(obj.toString())) {
			return Boolean.TRUE;
		}
		if("false".equalsIgnoreCase(obj.toString())) {
			return Boolean.FALSE;
		}
		return null;
	}
	
	public static Byte toByte(Object obj) {
	    if(obj == null) {
	        return null;
	    }
	    if(obj instanceof Byte) {
	        return (Byte) obj;
	    }
	    String objStr = obj.toString();
        if(objStr.matches("[-+]?[0-9]+")) {
            return Byte.parseByte(objStr);
        }
        return null;
	}
	
	public static Character toCharacter(Object obj) {
        if(obj == null) {
            return null;
        }
        String s = obj.toString();
        if(s.length() == 1) {
            return s.charAt(0);
        }
        return null;
	}
	
	public static Short toShort(Object obj) {
	    if(obj == null) {
	        return null;
	    }
	    String objStr = obj.toString();
        if(objStr.matches("[-+]?[0-9]+")) {
            return Short.parseShort(objStr);
        }
	    return null;
	}
	
	public static Integer toInteger(Object obj) {
	    if(obj == null) {
	        return null;
	    }
	    if(obj instanceof Integer) {
	        return (Integer) obj;
	    }
        String objStr = obj.toString();
        if(objStr.matches("[-+]?[0-9]+")) {
            return Integer.parseInt(objStr);
        }
        return null;
	}
	
	public static Long toLong(Object obj) {
		if(obj == null) {
			return null;
		}
		if(obj instanceof Long) {
			return (Long) obj;
		}
		if(obj instanceof Integer || obj instanceof Short || obj instanceof Byte) {
			return Long.parseLong(obj.toString());
		}
		String s = obj.toString();
		if(s.matches("[-+]?[0-9]+")) {
			return Long.parseLong(s);
		}
		return null;
	}
	
	public static BigDecimal toBigDecimal(Object obj) {
		if(obj == null) {
			return null;
		}
		if(obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}
		if(obj instanceof Integer) {
			return new BigDecimal((Integer)obj);
		}
		if(obj instanceof Long) {
			return new BigDecimal((Long)obj);
		}
		if(obj instanceof Double) {
			return new BigDecimal((Double)obj);
		}
		if(obj.toString().matches(".*[0-9]+.*") && obj.toString().matches("[-+]?[0-9]*.[0-9]*")) {
			return new BigDecimal(obj.toString());
		}
		return null;
	}
	
	public static Float toFloat(Object obj) {
	    if(obj == null) {
	        return null;
	    }
	    if(obj instanceof Float) {
	        return (Float) obj;
	    }
	    String s = obj.toString();
	    if(s.matches("[-+]?[0-9]*.[0-9]*[f]?")) {
	        return Float.parseFloat(s);
	    }
	    return null;
	}
	
	public static Double toDouble(Object o) {
	    if(o == null) {
	        return null;
	    }
	    if(o instanceof Double) {
	        return (Double) o;
	    }
        String s = o.toString();
        if(s.matches("[-+]?[0-9]*.[0-9]*[d]?")) {
            return Double.parseDouble(s);
        }
        return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> Map entityToMap(T t) {
		Map map = new HashMap<String, Object>();
		if(null == t) {
			return null;
		}
		List<Field> listField = ClassUtil.listField(t.getClass());
		try {
			for (Field field : listField) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(t));
			}
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		return map;
	}
	
	public static <T> T mapToEntity(@SuppressWarnings("rawtypes") Map map, Class<T> clazz) {
		if(map == null || map.size() == 0) {
			return null;
		}
		try {
			T t = clazz.newInstance();
			List<Field> listField = ClassUtil.listField(clazz);
			Object obj;
			for(Field field : listField) {
				obj = map.get(field.getName());
				if(null != obj) {
					field.setAccessible(true);
					field.set(t, toThis(field.getType(), obj));
				}
			}
			return t;
		}catch (InstantiationException e) {
		}catch (IllegalAccessException e) {
		}
		return null;
	}
}
