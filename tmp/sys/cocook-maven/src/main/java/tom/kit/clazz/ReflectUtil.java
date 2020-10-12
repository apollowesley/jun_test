 package tom.kit.clazz;

 import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import tom.cocook.core.CocookException;

 /**
  * 反射工具类
  *
  * @author Service Platform Architecture Team (spat@58.com)
  */
 public abstract class ReflectUtil {
	 


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
			if(param == null) return null;
			Class<?> clazz = param.getClass();
			if(Converter.convertType2Class(_class) == clazz || _class.isAssignableFrom(clazz)){  // 是本类 或 父类判断[isAssignableFrom]
				return param;
			}
			if (Converter.canConvertValue(_class)) {
				return Converter.coverterClass2Value(_class, null, param.toString());
			}
			return param.toString();
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
				if(method == null) continue;
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
		 * 将prop 注入到 obj(CoC name --> setName 注入)
		 * @throws SecurityException 
		 * @throws NoSuchMethodException 
		 */
		public static void populate(Object obj, Properties prop) {
			Iterator<Object> ite = prop.keySet().iterator();
			while (ite.hasNext()) {
				String key = (String) ite.next();
				String value = prop.getProperty(key);
				String methodName = toSetMethod(key);
				Method method = findMethod(obj.getClass(),methodName);
				if(method == null) {
					System.out.println("method is null = "+methodName);
					continue;
				}
				invokeMethodByConvert(obj, method, value);
			}
		}

     /**
      * Attempt to find a {@link java.lang.reflect.Field field} on the supplied {@link Class} with the
      * supplied <code>name</code>. Searches all superclasses up to {@link Object}.
      * @param clazz the class to introspect
      * @param name the name of the field
      * @return the corresponding Field object, or <code>null</code> if not found
      */
     public static Field findField(Class<?> clazz, String name) {
         return findField(clazz, name, null);
     }

     /**
      * Attempt to find a {@link java.lang.reflect.Field field} on the supplied {@link Class} with the
      * supplied <code>name</code> and/or {@link Class type}. Searches all superclasses
      * up to {@link Object}.
      * @param clazz the class to introspect
      * @param name the name of the field (may be <code>null</code> if type is specified)
      * @param type the type of the field (may be <code>null</code> if name is specified)
      * @return the corresponding Field object, or <code>null</code> if not found
      */
     public static Field findField(Class<?> clazz, String name, Class<?> type) {
         Class<?> searchType = clazz;
         while (!Object.class.equals(searchType) && searchType != null) {
             Field[] fields = searchType.getDeclaredFields();
             for (Field field : fields) {
                 if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
                     return field;
                 }
             }
             searchType = searchType.getSuperclass();
         }
         return null;
     }

     /**
      * Set the field represented by the supplied {@link java.lang.reflect.Field field object} on the
      * specified {@link Object target object} to the specified <code>value</code>.
      * In accordance with {@link java.lang.reflect.Field#set(Object, Object)} semantics, the new value
      * is automatically unwrapped if the underlying field has a primitive type.
      * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException(Exception)}.
      * @param field the field to set
      * @param target the target object on which to set the field
      * @param value the value to set; may be <code>null</code>
      */
     public static void setField(Field field, Object target, Object value) {
         try {
             field.set(target, value);
         }
         catch (IllegalAccessException ex) {
             handleReflectionException(ex);
             throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": "
                     + ex.getMessage());
         }
     }

     /**
      * Get the field represented by the supplied {@link java.lang.reflect.Field field object} on the
      * specified {@link Object target object}. In accordance with {@link java.lang.reflect.Field#get(Object)}
      * semantics, the returned value is automatically wrapped if the underlying field
      * has a primitive type.
      * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException(Exception)}.
      * @param field the field to get
      * @param target the target object from which to get the field
      * @return the field's current value
      */
     public static Object getField(Field field, Object target) {
         try {
             return field.get(target);
         }
         catch (IllegalAccessException ex) {
             handleReflectionException(ex);
             throw new IllegalStateException(
                     "Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
         }
     }

     /**
      * Attempt to find a {@link java.lang.reflect.Method} on the supplied class with the supplied name
      * and no parameters. Searches all superclasses up to <code>Object</code>.
      * <p>Returns <code>null</code> if no {@link java.lang.reflect.Method} can be found.
      * @param clazz the class to introspect
      * @param name the name of the method
      * @return the Method object, or <code>null</code> if none found
      */
     public static Method findMethod(Class<?> clazz, String name) {
         return findMethod(clazz, name, new Class<?>[0]);   //只判断名字相同
     }

     /**
      * Attempt to find a {@link java.lang.reflect.Method} on the supplied class with the supplied name
      * and parameter types. Searches all superclasses up to <code>Object</code>.
      * <p>Returns <code>null</code> if no {@link java.lang.reflect.Method} can be found.
      * @param clazz the class to introspect
      * @param name the name of the method
      * @param paramTypes the parameter types of the method
      * (may be <code>null</code> to indicate any signature)
      * @return the Method object, or <code>null</code> if none found
      */
     public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
         Class<?> searchType = clazz;
         while (searchType != null) {
             Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
             for (Method method : methods) {
                 if (name.equals(method.getName())
                         && (paramTypes.length==0 || Arrays.equals(paramTypes, method.getParameterTypes()))) {
                     return method;
                 }
             }
             searchType = searchType.getSuperclass();
         }
         return null;
     }

     /**
      * Invoke the specified {@link java.lang.reflect.Method} against the supplied target object with no arguments.
      * The target object can be <code>null</code> when invoking a static {@link java.lang.reflect.Method}.
      * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException}.
      * @param method the method to invoke
      * @param target the target object to invoke the method on
      * @return the invocation result, if any
      * @see #invokeMethod(java.lang.reflect.Method, Object, Object[])
      */
     public static Object invokeMethod(Method method, Object target) {
         return invokeMethod(method, target, new Object[0]);
     }

     /**
      * Invoke the specified {@link java.lang.reflect.Method} against the supplied target object with the
      * supplied arguments. The target object can be <code>null</code> when invoking a
      * static {@link java.lang.reflect.Method}.
      * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException}.
      * @param method the method to invoke
      * @param target the target object to invoke the method on
      * @param args the invocation arguments (may be <code>null</code>)
      * @return the invocation result, if any
      */
     public static Object invokeMethod(Method method, Object target, Object... args) {
         try {
             return method.invoke(target, args);
         }
         catch (Exception ex) {
             handleReflectionException(ex);
         }
         throw new IllegalStateException("Should never get here");
     }

     /**
      * Invoke the specified JDBC API {@link java.lang.reflect.Method} against the supplied target
      * object with no arguments.
      * @param method the method to invoke
      * @param target the target object to invoke the method on
      * @return the invocation result, if any
      * @throws java.sql.SQLException the JDBC API SQLException to rethrow (if any)
      * @see #invokeJdbcMethod(java.lang.reflect.Method, Object, Object[])
      */
     public static Object invokeJdbcMethod(Method method, Object target) throws SQLException {
         return invokeJdbcMethod(method, target, new Object[0]);
     }

     /**
      * Invoke the specified JDBC API {@link java.lang.reflect.Method} against the supplied target
      * object with the supplied arguments.
      * @param method the method to invoke
      * @param target the target object to invoke the method on
      * @param args the invocation arguments (may be <code>null</code>)
      * @return the invocation result, if any
      * @throws java.sql.SQLException the JDBC API SQLException to rethrow (if any)
      * @see #invokeMethod(java.lang.reflect.Method, Object, Object[])
      */
     public static Object invokeJdbcMethod(Method method, Object target, Object... args){
         try {
             return method.invoke(target, args);
         }catch(Exception e){
        	 e.printStackTrace();
         }
         return null;
     }

     /**
      * Handle the given reflection exception. Should only be called if no
      * checked exception is expected to be thrown by the target method.
      * <p>Throws the underlying RuntimeException or Error in case of an
      * InvocationTargetException with such a root cause. Throws an
      * IllegalStateException with an appropriate message else.
      * @param ex the reflection exception to handle
      */
     public static void handleReflectionException(Exception ex) {
         if (ex instanceof NoSuchMethodException) {
             throw new IllegalStateException("Method not found: " + ex.getMessage());
         }
         if (ex instanceof IllegalAccessException) {
             throw new IllegalStateException("Could not access method: " + ex.getMessage());
         }
         if (ex instanceof InvocationTargetException) {
             handleInvocationTargetException((InvocationTargetException) ex);
         }
         if (ex instanceof RuntimeException) {
             throw (RuntimeException) ex;
         }
         handleUnexpectedException(ex);
     }

     /**
      * Handle the given invocation target exception. Should only be called if no
      * checked exception is expected to be thrown by the target method.
      * <p>Throws the underlying RuntimeException or Error in case of such a root
      * cause. Throws an IllegalStateException else.
      * @param ex the invocation target exception to handle
      */
     public static void handleInvocationTargetException(InvocationTargetException ex) {
         rethrowRuntimeException(ex.getTargetException());
     }

     /**
      * Rethrow the given {@link Throwable exception}, which is presumably the
      * <em>target exception</em> of an {@link java.lang.reflect.InvocationTargetException}. Should
      * only be called if no checked exception is expected to be thrown by the
      * target method.
      * <p>Rethrows the underlying exception cast to an {@link RuntimeException} or
      * {@link Error} if appropriate; otherwise, throws an
      * {@link IllegalStateException}.
      * @param ex the exception to rethrow
      * @throws RuntimeException the rethrown exception
      */
     public static void rethrowRuntimeException(Throwable ex) {
         if (ex instanceof RuntimeException) {
             throw (RuntimeException) ex;
         }
         if (ex instanceof Error) {
             throw (Error) ex;
         }
         handleUnexpectedException(ex);
     }

     /**
      * Rethrow the given {@link Throwable exception}, which is presumably the
      * <em>target exception</em> of an {@link java.lang.reflect.InvocationTargetException}. Should
      * only be called if no checked exception is expected to be thrown by the
      * target method.
      * <p>Rethrows the underlying exception cast to an {@link Exception} or
      * {@link Error} if appropriate; otherwise, throws an
      * {@link IllegalStateException}.
      * @param ex the exception to rethrow
      * @throws Exception the rethrown exception (in case of a checked exception)
      */
     public static void rethrowException(Throwable ex) throws Exception {
         if (ex instanceof Exception) {
             throw (Exception) ex;
         }
         if (ex instanceof Error) {
             throw (Error) ex;
         }
         handleUnexpectedException(ex);
     }

     /**
      * Throws an IllegalStateException with the given exception as root cause.
      * @param ex the unexpected exception
      */
     private static void handleUnexpectedException(Throwable ex) {
         throw new IllegalStateException("Unexpected exception thrown", ex);
     }

     /**
      * Determine whether the given method explicitly declares the given
      * exception or one of its superclasses, which means that an exception of
      * that type can be propagated as-is within a reflective invocation.
      * @param method the declaring method
      * @param exceptionType the exception to throw
      * @return <code>true</code> if the exception can be thrown as-is;
      * <code>false</code> if it needs to be wrapped
      */
     public static boolean declaresException(Method method, Class<?> exceptionType) {
         Class<?>[] declaredExceptions = method.getExceptionTypes();
         for (Class<?> declaredException : declaredExceptions) {
             if (declaredException.isAssignableFrom(exceptionType)) {
                 return true;
             }
         }
         return false;
     }

     /**
      * Determine whether the given field is a "public static final" constant.
      * @param field the field to check
      */
     public static boolean isPublicStaticFinal(Field field) {
         int modifiers = field.getModifiers();
         return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
     }

     /**
      * Determine whether the given method is an "equals" method.
      * @see Object#equals(Object)
      */
     public static boolean isEqualsMethod(Method method) {
         if (method == null || !method.getName().equals("equals")) {
             return false;
         }
         Class<?>[] paramTypes = method.getParameterTypes();
         return (paramTypes.length == 1 && paramTypes[0] == Object.class);
     }

     /**
      * Determine whether the given method is a "hashCode" method.
      * @see Object#hashCode()
      */
     public static boolean isHashCodeMethod(Method method) {
         return (method != null && method.getName().equals("hashCode") && method.getParameterTypes().length == 0);
     }

     /**
      * Determine whether the given method is a "toString" method.
      * @see Object#toString()
      */
     public static boolean isToStringMethod(Method method) {
         return (method != null && method.getName().equals("toString") && method.getParameterTypes().length == 0);
     }

     /**
      * Make the given field accessible, explicitly setting it accessible if
      * necessary. The <code>setAccessible(true)</code> method is only called
      * when actually necessary, to avoid unnecessary conflicts with a JVM
      * SecurityManager (if active).
      * @param field the field to make accessible
      * @see java.lang.reflect.Field#setAccessible
      */
     public static void makeAccessible(Field field) {
         if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                 Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
             field.setAccessible(true);
         }
     }

     /**
      * Make the given method accessible, explicitly setting it accessible if
      * necessary. The <code>setAccessible(true)</code> method is only called
      * when actually necessary, to avoid unnecessary conflicts with a JVM
      * SecurityManager (if active).
      * @param method the method to make accessible
      * @see java.lang.reflect.Method#setAccessible
      */
     public static void makeAccessible(Method method) {
         if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                 && !method.isAccessible()) {
             method.setAccessible(true);
         }
     }

     /**
      * Make the given constructor accessible, explicitly setting it accessible
      * if necessary. The <code>setAccessible(true)</code> method is only called
      * when actually necessary, to avoid unnecessary conflicts with a JVM
      * SecurityManager (if active).
      * @param ctor the constructor to make accessible
      * @see java.lang.reflect.Constructor#setAccessible
      */
     public static void makeAccessible(Constructor<?> ctor) {
         if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers()))
                 && !ctor.isAccessible()) {
             ctor.setAccessible(true);
         }
     }

     /**
      * Perform the given callback operation on all matching methods of the given
      * class and superclasses.
      * <p>The same named method occurring on subclass and superclass will appear
      * twice, unless excluded by a {@link MethodFilter}.
      * @param clazz class to start looking at
      * @param mc the callback to invoke for each method
      * @see #doWithMethods(Class, MethodCallback, MethodFilter)
      */
     public static void doWithMethods(Class<?> clazz, MethodCallback mc) throws IllegalArgumentException {
         doWithMethods(clazz, mc, null);
     }

     /**
      * Perform the given callback operation on all matching methods of the given
      * class and superclasses (or given interface and super-interfaces).
      * <p>The same named method occurring on subclass and superclass will appear
      * twice, unless excluded by the specified {@link MethodFilter}.
      * @param clazz class to start looking at
      * @param mc the callback to invoke for each method
      * @param mf the filter that determines the methods to apply the callback to
      */
     public static void doWithMethods(Class<?> clazz, MethodCallback mc, MethodFilter mf)
             throws IllegalArgumentException {

         // Keep backing up the inheritance hierarchy.
         Method[] methods = clazz.getDeclaredMethods();
         for (Method method : methods) {
             if (mf != null && !mf.matches(method)) {
                 continue;
             }
             try {
                 mc.doWith(method);
             }
             catch (IllegalAccessException ex) {
                 throw new IllegalStateException("Shouldn't be illegal to access method '" + method.getName()
                         + "': " + ex);
             }
         }
         if (clazz.getSuperclass() != null) {
             doWithMethods(clazz.getSuperclass(), mc, mf);
         }
         else if (clazz.isInterface()) {
             for (Class<?> superIfc : clazz.getInterfaces()) {
                 doWithMethods(superIfc, mc, mf);
             }
         }
     }

     
     /**
      * Perform the given callback operation on all matching methods of the given
      * class and superclasses (or given interface and super-interfaces).
      * <p>The same named method occurring on subclass and superclass will appear
      * twice, unless excluded by the specified {@link MethodFilter}.
      * @param clazz class to start looking at
      * @param mc the callback to invoke for each method
      * @param mf the filter that determines the methods to apply the callback to
      */
     public static void doWithMethodsWithNoRecursion(Class<?> clazz, MethodCallback mc, MethodFilter mf)
             throws IllegalArgumentException {

         // Keep backing up the inheritance hierarchy.
         Method[] methods = clazz.getDeclaredMethods();
         for (Method method : methods) {
             if (mf != null && !mf.matches(method)) {
                 continue;
             }
             try {
                 mc.doWith(method);
             }
             catch (IllegalAccessException ex) {
                 throw new IllegalStateException("Shouldn't be illegal to access method '" + method.getName()
                         + "': " + ex);
             }
         }
//         if (clazz.getSuperclass() != null) {
//             doWithMethods(clazz.getSuperclass(), mc, mf);
//         }
//         else if (clazz.isInterface()) {
//             for (Class<?> superIfc : clazz.getInterfaces()) {
//                 doWithMethods(superIfc, mc, mf);
//             }
//         }
     }
     /**
      * Get all declared methods on the leaf class and all superclasses. Leaf
      * class methods are included first.
      */
     public static Method[] getAllDeclaredMethods(Class<?> leafClass) throws IllegalArgumentException {
         final List<Method> methods = new ArrayList<Method>(32);
         doWithMethods(leafClass, new MethodCallback() {
             @Override
			public void doWith(Method method) {
                 methods.add(method);
             }
         });
         return methods.toArray(new Method[methods.size()]);
     }

     /**
      * Invoke the given callback on all fields in the target class, going up the
      * class hierarchy to get all declared fields.
      * @param clazz the target class to analyze
      * @param fc the callback to invoke for each field
      */
     public static void doWithFields(Class<?> clazz, FieldCallback fc) throws IllegalArgumentException {
         doWithFields(clazz, fc, null);
     }

     /**
      * Invoke the given callback on all fields in the target class, going up the
      * class hierarchy to get all declared fields.
      * @param clazz the target class to analyze
      * @param fc the callback to invoke for each field
      * @param ff the filter that determines the fields to apply the callback to
      */
     public static void doWithFields(Class<?> clazz, FieldCallback fc, FieldFilter ff)
             throws IllegalArgumentException {

         // Keep backing up the inheritance hierarchy.
         Class<?> targetClass = clazz;
         do {
             Field[] fields = targetClass.getDeclaredFields();
             for (Field field : fields) {
                 // Skip static and final fields.
                 if (ff != null && !ff.matches(field)) {
                     continue;
                 }
                 try {
                     fc.doWith(field);
                 }
                 catch (IllegalAccessException ex) {
                     throw new IllegalStateException(
                             "Shouldn't be illegal to access field '" + field.getName() + "': " + ex);
                 }
             }
             targetClass = targetClass.getSuperclass();
         }
         while (targetClass != null && targetClass != Object.class);
     }

     /**
      * Given the source object and the destination, which must be the same class
      * or a subclass, copy all fields, including inherited fields. Designed to
      * work on objects with public no-arg constructors.
      * @throws IllegalArgumentException if the arguments are incompatible
      */
     public static void shallowCopyFieldState(final Object src, final Object dest) throws IllegalArgumentException {
         if (src == null) {
             throw new IllegalArgumentException("Source for field copy cannot be null");
         }
         if (dest == null) {
             throw new IllegalArgumentException("Destination for field copy cannot be null");
         }
         if (!src.getClass().isAssignableFrom(dest.getClass())) {
             throw new IllegalArgumentException("Destination class [" + dest.getClass().getName()
                     + "] must be same or subclass as source class [" + src.getClass().getName() + "]");
         }
         doWithFields(src.getClass(), new FieldCallback() {
             @Override
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                 makeAccessible(field);
                 Object srcValue = field.get(src);
                 field.set(dest, srcValue);
             }
         }, COPYABLE_FIELDS);
     }


     /**
      * Action to take on each method.
      */
     public interface MethodCallback {

         /**
          * Perform an operation using the given method.
          * @param method the method to operate on
          */
         void doWith(Method method) throws IllegalArgumentException, IllegalAccessException;
     }


     /**
      * Callback optionally used to method fields to be operated on by a method callback.
      */
     public interface MethodFilter {

         /**
          * Determine whether the given method matches.
          * @param method the method to check
          */
         boolean matches(Method method);
     }


     /**
      * Callback interface invoked on each field in the hierarchy.
      */
     public interface FieldCallback {

         /**
          * Perform an operation using the given field.
          * @param field the field to operate on
          */
         void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
     }


     /**
      * Callback optionally used to filter fields to be operated on by a field callback.
      */
     public interface FieldFilter {

         /**
          * Determine whether the given field matches.
          * @param field the field to check
          */
         boolean matches(Field field);
     }


     /**
      * Pre-built FieldFilter that matches all non-static, non-final fields.
      */
     public static FieldFilter COPYABLE_FIELDS = new FieldFilter() {

         @Override
		public boolean matches(Field field) {
             return !(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()));
         }
     };


     /**
      * Pre-built MethodFilter that matches all non-bridge methods.
      */
     public static MethodFilter NON_BRIDGED_METHODS = new MethodFilter() {

         @Override
		public boolean matches(Method method) {
             return !method.isBridge();
         }
     };


     /**
      * Pre-built MethodFilter that matches all non-bridge methods
      * which are not declared on <code>java.lang.Object</code>.
      */
     public static MethodFilter USER_DECLARED_METHODS = new MethodFilter() {

         @Override
		public boolean matches(Method method) {
             return (!method.isBridge() && method.getDeclaringClass() != Object.class);
         }
     };

 }
