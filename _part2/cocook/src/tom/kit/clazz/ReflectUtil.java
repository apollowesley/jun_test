package tom.kit.clazz;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


import tom.cocook.core.CocookException;
import tom.kit.converter.Converter;

public class ReflectUtil {

	/* 首字母大写 */
	public static String toTitleCase(String name) {
		return Character.toTitleCase(name.charAt(0)) + name.substring(1);
	}

	/* 获取setXxx的方法名称 */
	public static String toSetMethod(String name) {
		return "set" + toTitleCase(name);
	}

	/* 获取getXxx的方法名称 */
	public static String toGetMethod(String name) {
		return "get" + toTitleCase(name);
	}

	/**
	 * method.setAccessible(true); 执行方法 method.invoke(obj, params...) 返回方法的执行结果,
	 * void 返回null, obj为实例[静态方法可为NULL], params 为参数,无参可为0[NULL]
	 */
	public static Object invokeMethodByConvert(final Object object, final Method method, Object... params) {
		convert(method, params);
		try{
			return method.invoke(object, params);
		}catch(Exception e){
			throw new CocookException(e);
		}
	}
	
	public static Object invokeMethod(final Object object, final Method method, Object... params){
		try{
			return method.invoke(object, params);
		}catch(Exception e){
			throw new CocookException(e);
		}
	}

	/**
	 * 转换方法的所有参数类型
	 * 
	 * @param method
	 * @param params
	 * @return
	 */
	public static Object[] convert(final Method method, Object... params) {
		Class<?>[] _class = method.getParameterTypes(); // 查找 方法形参类型
		if (_class.length != params.length) {
			return params;
		}
		for (int i = 0; i < params.length; i++) {
			params[i] = covert(_class[i], params[i]);
		}
		return params;
	}

	/**
	 * 根据method形参转换参数,支持常用参数类型
	 * 
	 * @param _class
	 * @param param
	 * @return
	 */
	public static Object covert(Class<?> _class, Object param) {
		if(Converter.convertType2Class(_class) == param.getClass()) return param;
		if (Converter.canConvertValue(_class)) {
			return Converter.coverterClass2Value(_class, null, param.toString());
		}
		return param;
	}

	/**
	 * 使其可见 private-->public
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 持续向上查找,直到找到某个方法
	 */
	public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {// NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 根据方法名获取方法
	 */
	public static Method getDeclaredMethod(Class<?> _class, String methodName) {
		Method m[] = _class.getDeclaredMethods();
		Method me = null;
		for (int i = 0; i < m.length; i++) {
			/* 如果是公共方法,而且名称相同则返回,如果名称相同参数不同,按先后顺序只执行第一个方法 */// 是否可以判断参数个数相同的执行
			if (m[i].getModifiers() == Modifier.PUBLIC && m[i].getName().equals(methodName)) {
				me = m[i];
				break;
			}
		}
		return me;
	}

	/**
	 * 查找泛型类的泛型
	 */
	public static Class<?> getSuperClassGenricType(final Class<?> clazz, final int index) {
		Class<?> _class = Object.class;
		Type genType = clazz.getGenericSuperclass(); // 首先查找类的类型
		if (!(genType instanceof ParameterizedType)) { // 如果不是泛型类,直接返回
			return _class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments(); // 强制转换成泛型类的类型数组
		if (params.length == 0) {
			return _class;
		}
		try {
			/* 例如 class User<E,U> params(0)返回E,params(1)返回U */
			_class = (Class<?>) params[index];
		} catch (Exception e) {
		}

		return _class;
	}

	/**
	 * 将prop 注入到 obj(CoC name --> setName 注入)
	 */
	public static void populate(Object obj, Properties prop) {
		Iterator<Object> ite = prop.keySet().iterator();
		while (ite.hasNext()) {
			String key = (String) ite.next();
			String value = prop.getProperty(key);
			String methodName = toSetMethod(key);
			Method method = getDeclaredMethod(obj.getClass(), methodName);
			invokeMethodByConvert(obj, method, value);
		}
	}

	/**
	 * map 转 bean<T>
	 * 
	 * @param <T>
	 * @param map
	 * @param _class
	 * @return
	 */
	public static <T> T mapToBean(Map<String, ?> map, Class<T> _class) {
		T t;
		try {
			t = _class.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		for (String key : map.keySet()) {
			Object value = map.get(key);
			String methodName = toSetMethod(key);
			Method method = getDeclaredMethod(_class, methodName);
			invokeMethodByConvert(t, method, value);
		}
		return t;
	}

	/**
	 * bean 转 map
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object bean) {
		BeanInfo info = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			info = Introspector.getBeanInfo(bean.getClass());
		} catch (IntrospectionException e) {
			e.printStackTrace();
			return null;
		}
		PropertyDescriptor[] pros = info.getPropertyDescriptors();
		for (PropertyDescriptor pro : pros) {
			String name = pro.getName();
			if (!"class".equals(name)) {
				Method method = pro.getReadMethod();
				try {
					Object result = method.invoke(bean);
					if (result != null)
						map.put(name, result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			;
		}
		return map;
	}
	
	
	/**
     * Check whether the given class is visible in the given ClassLoader.
     * @param clazz the class to check (typically an interface)
     * @param classLoader the ClassLoader to check against (may be <code>null</code>,
     * in which case this method will always return <code>true</code>)
     */
    public static boolean isVisible(Class<?> clazz, ClassLoader classLoader) {
        if (classLoader == null) {
            return true;
        }
        try {
            Class<?> actualClass = classLoader.loadClass(clazz.getName());
            return (clazz == actualClass);
            // Else: different interface class found...
        }
        catch (ClassNotFoundException ex) {
            // No interface class found...
            return false;
        }
    }
    /**
     * Return all interfaces that the given class implements as array,
     * including ones implemented by superclasses.
     * <p>If the class itself is an interface, it gets returned as sole interface.
     * @param clazz the class to analyze for interfaces
     * @return all interfaces that the given object implements as array
     */
    public static Class<?>[] getAllInterfacesForClass(Class<?> clazz) {
        return getAllInterfacesForClass(clazz, null);
    }
    /**
     * Return all interfaces that the given class implements as array,
     * including ones implemented by superclasses.
     * <p>If the class itself is an interface, it gets returned as sole interface.
     * @param clazz the class to analyze for interfaces
     * @param classLoader the ClassLoader that the interfaces need to be visible in
     * (may be <code>null</code> when accepting all declared interfaces)
     * @return all interfaces that the given object implements as array
     */
    public static Class<?>[] getAllInterfacesForClass(Class<?> clazz, ClassLoader classLoader) {
        @SuppressWarnings("rawtypes")
		Set<Class> ifcs = getAllInterfacesForClassAsSet(clazz, classLoader);
        return ifcs.toArray(new Class[ifcs.size()]);
    }
    /**
     * Return all interfaces that the given class implements as Set,
     * including ones implemented by superclasses.
     * <p>If the class itself is an interface, it gets returned as sole interface.
     * @param clazz the class to analyze for interfaces
     * @param classLoader the ClassLoader that the interfaces need to be visible in
     * (may be <code>null</code> when accepting all declared interfaces)
     * @return all interfaces that the given object implements as Set
     */
    @SuppressWarnings("rawtypes")
	public static Set<Class> getAllInterfacesForClassAsSet(Class clazz, ClassLoader classLoader) {
        if (clazz.isInterface() && isVisible(clazz, classLoader)) {
            return java.util.Collections.singleton(clazz);
        }
        Set<Class> interfaces = new LinkedHashSet<Class>();
        while (clazz != null) {
            Class<?>[] ifcs = clazz.getInterfaces();
            for (Class<?> ifc : ifcs) {
                interfaces.addAll(getAllInterfacesForClassAsSet(ifc, classLoader));
            }
            clazz = clazz.getSuperclass();
        }
        return interfaces;
    }

	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {

		aa a = new aa();
		Method m = getDeclaredMethod(a.getClass(), "getName");
		invokeMethodByConvert(a, m, 2010L, true);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", "panmg");
		map.put("age", 22);
		map.put("sex", 0);
		long q = System.currentTimeMillis();
		for(int i=0;i<100000;i++){
			aa aaa = mapToBean(map, aa.class);
			beanToMap(aaa);
		}
//		System.out.println(aaa);
		System.out.println(System.currentTimeMillis() - q);
	}

	static class aa {
		private String name;
		private int age;
		private int sex;

		public void getName(long name, boolean flag) {
			System.out.println("this is test!" + name + "==" + flag);
		}

		@Override
		public String toString() {
			return "aa [name=" + name + ", age=" + age + ", sex=" + sex + "]";
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public int getAge() {
			return age;
		}

		public void setSex(int sex) {
			this.sex = sex;
		}

		public int getSex() {
			return sex;
		}
	}
}
