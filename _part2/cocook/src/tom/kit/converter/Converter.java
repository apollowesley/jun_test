package tom.kit.converter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/**
 *  converters for class.
 */
public class Converter{

	/* 静态方法反射 调用, 无需初始化 */
	@SuppressWarnings("serial")
	private static final HashMap<Class<?>, Method> class2Value = new HashMap<Class<?>, Method>() {
		{
			try {
				Class<?> s = java.lang.String.class;
				/* boolean.class === java.lang.Boolean.TYPE */
				put(java.lang.Boolean.TYPE, java.lang.Boolean.class.getMethod("parseBoolean", s));
				put(java.lang.Byte.TYPE, java.lang.Byte.class.getMethod("parseByte", s));
				put(java.lang.Double.TYPE, java.lang.Double.class.getMethod("parseDouble", s));
				put(java.lang.Float.TYPE, java.lang.Float.class.getMethod("parseFloat", s));
				put(java.lang.Integer.TYPE, java.lang.Integer.class.getMethod("parseInt", s));
				put(java.lang.Long.TYPE, java.lang.Long.class.getMethod("parseLong", s));
				put(java.lang.Short.TYPE, java.lang.Short.class.getMethod("parseShort", s));

				put(java.lang.String.class, java.lang.String.class.getMethod("valueOf", Object.class));
				put(java.lang.Boolean.class, java.lang.Boolean.class.getMethod("parseBoolean", s));
				put(java.lang.Byte.class, java.lang.Byte.class.getMethod("parseByte", s));
				put(java.lang.Double.class, java.lang.Double.class.getMethod("parseDouble", s));
				put(java.lang.Float.class, java.lang.Float.class.getMethod("parseFloat", s));
				put(java.lang.Integer.class, java.lang.Integer.class.getMethod("parseInt", s));
				put(java.lang.Long.class, java.lang.Long.class.getMethod("parseLong", s));
				put(java.lang.Short.class, java.lang.Short.class.getMethod("parseShort", s));

				put(java.util.Date.class, Converter.class.getMethod("parserDate", s));
				put(java.sql.Timestamp.class, Converter.class.getMethod("parserDateTime", s));

			} catch (Exception e) {
				throw new Error(e);
			}

		}
	};

	public static Date parserDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date parserDateTime(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean canConvertValue(Class<?> clazz) {
		return class2Value.containsKey(clazz);
	}

	/**
	 * 静态方法反射 调用, 有参无需初始化 
	 * @param <T>
	 * @param _class
	 * @param obj == null 实例
	 * @param param !=null 反射参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T coverterClass2Value(Class<T> _class, Object obj, Object... param) {
		try {
			return (T) class2Value.get(_class).invoke(obj, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/* -------------------------type2Class----------------------------------- */
	private static final HashMap<Type, Class<?>> type2Class = new HashMap<Type, Class<?>>();
	static {
		type2Class.put(java.lang.Boolean.TYPE, java.lang.Boolean.class);
		type2Class.put(java.lang.Byte.TYPE, java.lang.Byte.class);
		type2Class.put(java.lang.Character.TYPE, java.lang.Character.class);
		type2Class.put(java.lang.Double.TYPE, java.lang.Double.class);
		type2Class.put(java.lang.Float.TYPE, java.lang.Float.class);
		type2Class.put(java.lang.Integer.TYPE, java.lang.Integer.class);
		type2Class.put(java.lang.Long.TYPE, java.lang.Long.class);
		type2Class.put(java.lang.Short.TYPE, java.lang.Short.class);
	}

	public static Type convertType2Class(Type type) {
		if(type2Class.containsKey(type)){
			return type2Class.get(type);
		}
		return type;
	}
	

	/* -------------------------name2Class----------------------------------- */
	private static final HashMap<String, Class<?>> name2Class = new HashMap<String, Class<?>>();
	static {
		
		name2Class.put("java.lang.Boolean.TYPE", java.lang.Boolean.class);
		name2Class.put("java.lang.Byte.TYPE", java.lang.Byte.class);
		name2Class.put("java.lang.Character.TYPE", java.lang.Character.class);
		name2Class.put("java.lang.Double.TYPE", java.lang.Double.class);
		name2Class.put("java.lang.Float.TYPE", java.lang.Float.class);
		name2Class.put("java.lang.Integer.TYPE", java.lang.Integer.class);
		name2Class.put("java.lang.Long.TYPE", java.lang.Long.class);
		name2Class.put("java.lang.Short.TYPE", java.lang.Short.class);
		
		name2Class.put("boolean", java.lang.Boolean.class);
		name2Class.put("byte", java.lang.Byte.class);
		name2Class.put("char", java.lang.Character.class);
		name2Class.put("double", java.lang.Double.class);
		name2Class.put("float", java.lang.Float.class);
		name2Class.put("int", java.lang.Integer.class);
		name2Class.put("long", java.lang.Long.class);
		name2Class.put("short", java.lang.Short.class);
		
		name2Class.put("java.lang.Boolean", java.lang.Boolean.class);
		name2Class.put("java.lang.Byte", java.lang.Byte.class);
		name2Class.put("java.lang.Character", java.lang.Character.class);
		name2Class.put("java.lang.Double", java.lang.Double.class);
		name2Class.put("java.lang.Float", java.lang.Float.class);
		name2Class.put("java.lang.Integer", java.lang.Integer.class);
		name2Class.put("java.lang.Long", java.lang.Long.class);
		name2Class.put("java.lang.Short", java.lang.Short.class);

		name2Class.put("Boolean", java.lang.Boolean.class);
		name2Class.put("Byte", java.lang.Byte.class);
		name2Class.put("Character", java.lang.Character.class);
		name2Class.put("Double", java.lang.Double.class);
		name2Class.put("Float", java.lang.Float.class);
		name2Class.put("Integer", java.lang.Integer.class);
		name2Class.put("Long", java.lang.Long.class);
		name2Class.put("Short", java.lang.Short.class);

		name2Class.put(null, java.lang.Void.TYPE);
		name2Class.put("string", java.lang.String.class);
		name2Class.put("String", java.lang.String.class);
		name2Class.put("java.lang.String", java.lang.String.class);
	}
	
	
	
	public static Class<?> convertName2Class(String name) {
		if(name2Class.containsKey(name)){
			return name2Class.get(name);
		}
		return null;
	}

	/* ------------------------------------------------------------ */
	private static final HashMap<Class<?>, String> class2Name = new HashMap<Class<?>, String>();
	static {
		class2Name.put(java.lang.Boolean.TYPE, "boolean");
		class2Name.put(java.lang.Byte.TYPE, "byte");
		class2Name.put(java.lang.Character.TYPE, "char");
		class2Name.put(java.lang.Double.TYPE, "double");
		class2Name.put(java.lang.Float.TYPE, "float");
		class2Name.put(java.lang.Integer.TYPE, "int");
		class2Name.put(java.lang.Long.TYPE, "long");
		class2Name.put(java.lang.Short.TYPE, "short");
		class2Name.put(java.lang.Void.TYPE, "void");

		class2Name.put(java.lang.Boolean.class, "java.lang.Boolean");
		class2Name.put(java.lang.Byte.class, "java.lang.Byte");
		class2Name.put(java.lang.Character.class, "java.lang.Character");
		class2Name.put(java.lang.Double.class, "java.lang.Double");
		class2Name.put(java.lang.Float.class, "java.lang.Float");
		class2Name.put(java.lang.Integer.class, "java.lang.Integer");
		class2Name.put(java.lang.Long.class, "java.lang.Long");
		class2Name.put(java.lang.Short.class, "java.lang.Short");

		class2Name.put(null, "void");
		class2Name.put(java.lang.String.class, "java.lang.String");
	}

	
	
	public static String convertclass2Name(String name) {
		if(class2Name.containsKey(name)){
			return class2Name.get(name);
		}
		return null;
	}
	
	
	public static void main(String[] args) {
//		Date f = coverterClass2Value(java.sql.Timestamp.class,null, "2013-10-10 23:59:59");
//		System.out.println(System.currentTimeMillis());
//		System.out.println(f);
//		System.out.println(coverterClass2Value(int.class,null, "123123"));
//		System.out.println(System.currentTimeMillis());
		
	}

}
