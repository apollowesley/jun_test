/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-3-8 下午12:43:09
 */
package com.absir.core.dyna;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.absir.core.kernel.KernelArray;
import com.absir.core.kernel.KernelArray.ArrayAccessor;
import com.absir.core.kernel.KernelClass;
import com.absir.core.kernel.KernelDyna;
import com.absir.core.kernel.KernelLang;
import com.absir.core.kernel.KernelLang.BreakException;
import com.absir.core.kernel.KernelObject;
import com.absir.core.kernel.KernelReflect;

/**
 * @author absir
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DynaBinder {

	/** INSTANCE */
	public static final DynaBinder INSTANCE = new DynaBinder();

	/**
	 * @param obj
	 * @param toClass
	 * @return
	 */
	public static <T> T to(Object obj, Class<T> toClass) {
		return to(obj, null, toClass);
	}

	/**
	 * @param obj
	 * @param name
	 * @param toClass
	 * @return
	 */
	public static <T> T to(Object obj, String name, Class<T> toClass) {
		return INSTANCE.binder(obj, name, toClass);
	}

	/**
	 * @param map
	 * @param toClass
	 * @return
	 */
	public static <T> T mapTo(Map map, Class<T> toClass) {
		return mapTo(map, null, toClass);
	}

	/**
	 * @param map
	 * @param name
	 * @param toClass
	 * @return
	 */
	public static <T> T mapTo(Map map, String name, Class<T> toClass) {
		return INSTANCE.mapBinder(map, name, toClass);
	}

	/**
	 * @param map
	 * @param toObject
	 */
	public static void mapTo(Map map, Object toObject) {
		INSTANCE.mapBinder(map, toObject);
	}

	/** converts */
	protected final List<DynaConvert> converts = new ArrayList<DynaConvert>();

	/**
	 * 
	 */
	public DynaBinder() {
	}

	/**
	 * @param convert
	 */
	public DynaBinder(DynaConvert convert) {
		if (convert != null) {
			this.converts.add(convert);
		}
	}

	/**
	 * @return
	 */
	public List<DynaConvert> getConverts() {
		return converts;
	}

	/**
	 * @param obj
	 * @param name
	 * @param toType
	 * @return
	 */
	public <T> T binder(Object obj, String name, Type toType) {
		return binder(obj, name, toType, null);
	}

	/**
	 * @param obj
	 * @param name
	 * @param toType
	 * @param toObject
	 * @return
	 */
	public <T> T binder(Object obj, String name, Type toType, T toObject) {
		if (toType == null) {
			return (T) obj;
		}

		if (toType instanceof Class) {
			return (T) binder(obj, name, (Class) toType);
		}

		if (toType instanceof ParameterizedType) {
			ParameterizedType type = (ParameterizedType) toType;
			toType = type.getRawType();
			if (toType instanceof Class) {
				Class toClass = (Class) toType;
				if (Collection.class.isAssignableFrom(toClass)) {
					return (T) binderCollection(obj, name, toClass, KernelArray.get(type.getActualTypeArguments(), 0), (Collection) toObject);

				} else if (Map.class.isAssignableFrom(toClass)) {
					if (obj instanceof Map) {
						return (T) binderMap(obj, name, null, toClass, KernelArray.get(type.getActualTypeArguments(), 1), KernelArray.get(type.getActualTypeArguments(), 0), (Map) toObject);
					}
				}
			}

		} else if (toType instanceof GenericArrayType) {
			GenericArrayType type = (GenericArrayType) toType;
			type.getGenericComponentType();
		}

		return null;
	}

	/**
	 * @param obj
	 * @param name
	 * @param toClass
	 * @return
	 */
	public <T> T binder(Object obj, String name, Class<T> toClass) {
		if (obj == null) {
			return KernelDyna.nullTo(toClass);
		}

		if (toClass.isAssignableFrom(obj.getClass())) {
			return (T) obj;
		}

		T toObject = null;
		if (obj instanceof Number) {
			toObject = KernelDyna.numberTo((Number) obj, toClass);

		} else if (obj instanceof Date) {
			toObject = KernelDyna.dateTo((Date) obj, toClass);

		} else if (obj instanceof String) {
			boolean[] dynas = new boolean[] { true };
			toObject = KernelDyna.stringTo((String) obj, toClass, dynas);
			if (dynas[0]) {
				return toObject;
			}

		} else if (obj instanceof Map) {
			return mapBinder((Map) obj, name, toClass);

		} else if (toClass.isArray()) {
			return (T) binderArray(obj, name, toClass, toClass.getComponentType());
		}

		if (toObject == null) {
			BreakException breakException = null;
			for (DynaConvert convert : converts) {
				try {
					toObject = (T) convert.to(obj, name, toClass, breakException);
					if (toObject != null) {
						return toObject;
					}

				} catch (BreakException e) {
					breakException = e;

				} catch (Exception e) {
					// TODO: handle exception
					break;
				}
			}

			toObject = (T) KernelClass.instanceOf(toClass, obj);
			if (toObject == null) {
				return KernelDyna.nullTo(toClass);
			}
		}

		return toObject;
	}

	/**
	 * @param map
	 * @param toObject
	 */
	public void mapBinder(Map map, Object toObject) {
		if (toObject != null) {
			Class toClass = toObject.getClass();
			while (toClass != null) {
				List<Field> fields = KernelReflect.declaredFields(toClass);
				for (Field field : fields) {
					Object value = mapField(map, field);
					if (value != KernelLang.NULL_OBJECT) {
						if (KernelReflect.memberAccessor(field) != null) {
							Method method = KernelClass.setter(toObject.getClass(), field);
							if (method != null || Modifier.isPublic(field.getModifiers())) {
								value = binder(value, field, KernelObject.publicGetter(toObject, field), toObject);
								if (method == null) {
									KernelReflect.set(toObject, field, value);

								} else {
									KernelReflect.invoke(toObject, method, value);
								}
							}
						}
					}
				}

				toClass = toClass.getSuperclass();
			}
		}
	}

	/**
	 * @param map
	 * @param field
	 * @return
	 */
	protected Object mapField(Map map, Field field) {
		if (map.containsKey(field.getName())) {
			return map.get(field.getName());
		}

		return KernelLang.NULL_OBJECT;
	}

	/**
	 * @param map
	 * @param name
	 * @param toClass
	 * @return
	 */
	public <T> T mapBinder(Map map, String name, Class<T> toClass) {
		T toObject = null;
		BreakException breakException = null;
		for (DynaConvert convert : converts) {
			try {
				toObject = (T) convert.mapTo(map, name, toClass, breakException);
				if (toObject != null) {
					break;
				}

			} catch (BreakException e) {
				breakException = e;
			}
		}

		if (toObject == null) {
			toObject = KernelClass.newInstance(toClass);
		}

		mapBinder(map, toObject);
		return toObject;
	}

	/**
	 * @param obj
	 * @param field
	 * @param toObject
	 * @param object
	 * @return
	 */
	protected Object binder(Object obj, Field field, Object toObject, Object object) {
		String name = null;
		return binder(obj, name, field.getGenericType(), toObject);
	}

	/**
	 * @param obj
	 * @param name
	 * @param toClass
	 * @param toType
	 * @param toObject
	 * @return
	 */
	protected <T extends Collection> T binderCollection(Object obj, String name, Class<T> toClass, Type toType, Collection toObject) {
		if (toObject == null) {
			if (toClass.isAssignableFrom(ArrayList.class)) {
				toClass = (Class<T>) ArrayList.class;

			} else if (toClass.isAssignableFrom(HashSet.class)) {
				toClass = (Class<T>) HashSet.class;
			}

			try {
				toObject = toClass.newInstance();
			} catch (Exception e) {
			}
		}

		if (toObject != null) {
			toObject.clear();
			if (obj instanceof Collection) {
				for (Object ob : (Collection) obj) {
					toObject.add(binder(ob, name, toType));
				}

			} else if (obj.getClass().isArray()) {
				ArrayAccessor array = KernelArray.forClass(obj.getClass());
				int length = Array.getLength(obj);
				for (int i = 0; i < length; i++) {
					toObject.add(binder(array.get(obj, i), name, toType));
				}

			} else {
				toObject.add(binder(obj, name, toType));
			}
		}

		return (T) toObject;
	}

	/**
	 * @param obj
	 * @param name
	 * @param toClass
	 * @param toType
	 * @return
	 */
	protected <T> T binderArray(Object obj, String name, Class<T> toClass, Type toType) {
		ArrayAccessor array = KernelArray.forClass(toClass);
		if (obj instanceof Collection) {
			Object toObject = array.newInstance(((Collection) obj).size());
			if (toObject != null) {
				int index = 0;
				for (Object o : (Collection) obj) {
					array.set(toObject, index++, binder(o, name, toType));
				}
			}

			return (T) toObject;

		} else if (obj.getClass().isArray()) {
			int length = Array.getLength(obj);
			Object toObject = array.newInstance(length);
			if (toObject != null) {
				ArrayAccessor ary = KernelArray.forClass(obj.getClass());
				for (int i = 0; i < length; i++) {
					array.set(toObject, i, binder(ary.get(obj, i), name, toType));
				}
			}

			return (T) toObject;

		} else {
			Object toObject = array.newInstance(1);
			if (toObject != null) {
				array.set(toObject, 0, binder(obj, name, toType));
			}

			return (T) toObject;
		}
	}

	/**
	 * @param obj
	 * @param name
	 * @param keyName
	 * @param toClass
	 * @param toType
	 * @param keyType
	 * @param toObject
	 * @return
	 */
	protected <T extends Map> T binderMap(Object obj, String name, String keyName, Class<T> toClass, Type toType, Type keyType, Map toObject) {
		if (obj instanceof Map) {
			return binderMap((Map) obj, name, keyName, toClass, toType, keyType, toObject);
		}

		return null;
	}

	/**
	 * @param obj
	 * @param name
	 * @param keyName
	 * @param toClass
	 * @param toType
	 * @param keyType
	 * @param toObject
	 * @return
	 */
	protected <T extends Map> T binderMap(Map obj, String name, String keyName, Class<T> toClass, Type toType, Type keyType, Map toObject) {
		if (toObject == null) {
			if (toClass.isAssignableFrom(HashMap.class)) {
				toClass = (Class<T>) HashMap.class;
			}

			toObject = KernelClass.newInstance(toClass);
		}

		if (toObject != null) {
			toObject.clear();
			for (Iterator<Entry> it = obj.entrySet().iterator(); it.hasNext();) {
				Entry entry = it.next();
				Object key = binder(entry.getKey(), keyName, keyType);
				if (key != null) {
					toObject.put(key, binder(entry.getValue(), name, toType));
				}
			}
		}

		return (T) toObject;
	}
}
