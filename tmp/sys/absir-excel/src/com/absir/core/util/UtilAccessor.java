/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-9-22 下午1:38:58
 */
package com.absir.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.absir.core.kernel.KernelClass;
import com.absir.core.kernel.KernelReflect;

/**
 * @author absir
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UtilAccessor {

	/**
	 * @author absir
	 * 
	 */
	public static interface Accessor {

		/**
		 * @param obj
		 * @return
		 */
		public Object get(Object obj);

		/**
		 * @param obj
		 * @param value
		 * @return
		 */
		public boolean set(Object obj, Object value);
	}

	/**
	 * @author absir
	 * 
	 */
	public static abstract class AccessorWrapper implements Accessor {

		/** accessor */
		private Accessor accessor;

		/**
		 * @param accessor
		 */
		public AccessorWrapper(Accessor accessor) {
			this.accessor = accessor;
		}

		/**
		 * @param obj
		 * @return
		 */
		private Object eval(Object obj) {
			return accessor == null ? obj : accessor.get(obj);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.absir.core.util.UtilAccessor.Accessor#get(java.lang.Object)
		 */
		@Override
		public Object get(Object obj) {
			// TODO Auto-generated method stub
			obj = eval(obj);
			if (obj == null) {
				return null;
			}

			return evalGet(obj);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.absir.core.util.UtilAccessor.Accessor#set(java.lang.Object,
		 * java.lang.Object)
		 */
		@Override
		public boolean set(Object obj, Object value) {
			// TODO Auto-generated method stub
			obj = eval(obj);
			if (obj == null) {
				return false;
			}

			return evalSet(obj, value);
		}

		/**
		 * @param obj
		 * @return
		 */
		public abstract Object evalGet(Object obj);

		/**
		 * @param obj
		 * @param value
		 * @return
		 */
		public abstract boolean evalSet(Object obj, Object value);
	}

	/**
	 * @param obj
	 * @param propertyPath
	 * @return
	 */
	public static Object get(Object obj, String propertyPath) {
		return getAccessor(obj, propertyPath).get(obj);
	}

	/**
	 * @param obj
	 * @param propertyPath
	 * @param value
	 */
	public static void set(Object obj, String propertyPath, Object value) {
		getAccessor(obj, propertyPath).set(obj, value);
	}

	/** Accessor_Name_Map_Accessor */
	private static Map<String, Accessor> Accessor_Name_Map_Accessor = new HashMap<String, Accessor>();

	/**
	 * @param cls
	 * @param propertyName
	 * @return
	 */
	public static Accessor getAccessor(Class<?> cls, final Field field) {
		return getAccessor(cls, field.getName(), field);
	}

	/**
	 * @param cls
	 * @param property
	 * @param field
	 * @return
	 */
	private static Accessor getAccessor(Class<?> cls, String property, final Field field) {
		String accessName = getAccessorKey(cls.getName(), field.getName());
		Accessor accessor = Accessor_Name_Map_Accessor.get(accessName);
		if (accessor == null) {
			synchronized (cls) {
				accessor = Accessor_Name_Map_Accessor.get(accessName);
				if (accessor == null) {
					Class fieldType = field == null ? null : field.getType();
					final Method getter = KernelClass.declaredGetter(cls, property, fieldType, true);
					final Method setter = KernelClass.declaredSetter(cls, property, fieldType, true);
					accessor = new Accessor() {

						@Override
						public Object get(Object obj) {
							// TODO Auto-generated method stub
							if (obj == null) {
								return null;
							}

							return getter == null ? KernelReflect.get(obj, field) : KernelReflect.invoke(obj, getter);
						}

						@Override
						public boolean set(Object obj, Object value) {
							// TODO Auto-generated method stub
							if (obj == null) {
								return false;
							}

							return setter == null ? KernelReflect.set(obj, field, value) : KernelReflect.run(obj, setter, value);
						}
					};

					Accessor_Name_Map_Accessor.put(accessName, accessor);
				}
			}
		}

		return accessor;
	}

	/**
	 * @param obj
	 * @param propertyPath
	 * @return
	 */
	public static Accessor getAccessor(Object obj, String propertyPath) {
		return getAccessor(obj, null, propertyPath.split("\\."), 0);
	}

	/**
	 * @param accessorName
	 * @param propertyPath
	 * @return
	 */
	private static String getAccessorKey(String accessorName, String propertyPath) {
		return accessorName + ":" + propertyPath;
	}

	/**
	 * @param obj
	 * @param propertyPath
	 * @param accessorName
	 * @return
	 */
	public static Accessor getAccessor(Object obj, String propertyPath, String accessorName) {
		if (accessorName == null || obj == null) {
			return getAccessor(obj, propertyPath);

		} else {
			accessorName = getAccessorKey(accessorName, propertyPath);
			Accessor accessor = Accessor_Name_Map_Accessor.get(accessorName);
			if (accessor == null) {
				synchronized (obj.getClass()) {
					accessor = Accessor_Name_Map_Accessor.get(accessorName);
					if (accessor == null) {
						accessor = getAccessor(obj, propertyPath);
						Accessor_Name_Map_Accessor.put(accessorName, accessor);
					}
				}
			}

			return accessor;
		}
	}

	/**
	 * @param propertyPath
	 * @param accessorName
	 */
	public static void clearAccessor(String propertyPath, String accessorName) {
		Accessor_Name_Map_Accessor.remove(getAccessorKey(accessorName, propertyPath));
	}

	/**
	 * @param obj
	 * @param accessorWrapper
	 * @param properties
	 * @return
	 */
	private static Accessor getAccessor(Object obj, AccessorWrapper accessorWrapper, final String[] properties, int i) {
		for (; i < properties.length; i++) {
			if (obj == null) {
				final int index = i;
				return new AccessorWrapper(accessorWrapper) {

					/** evalAccessor */
					private Accessor evalAccessor;

					/**
					 * @param obj
					 * @return
					 */
					private Accessor getEvalAccessor(Object obj) {
						if (evalAccessor == null) {
							evalAccessor = getAccessor(obj, null, properties, index);
						}

						return evalAccessor;
					}

					@Override
					public Object evalGet(Object obj) {
						// TODO Auto-generated method stub
						return getEvalAccessor(obj).get(obj);
					}

					@Override
					public boolean evalSet(Object obj, Object value) {
						// TODO Auto-generated method stub
						return getEvalAccessor(obj).set(obj, value);
					}
				};

			} else {
				final String property = properties[i];
				if (obj instanceof Map) {
					accessorWrapper = new AccessorWrapper(accessorWrapper) {

						@Override
						public Object evalGet(Object obj) {
							// TODO Auto-generated method stub
							return ((Map) obj).get(property);
						}

						@Override
						public boolean evalSet(Object obj, Object value) {
							// TODO Auto-generated method stub
							((Map) obj).put(property, value);
							return true;
						}
					};

				} else {
					Field field = KernelReflect.declaredField(obj.getClass(), property);
					final Accessor evalAccessor = getAccessor(obj.getClass(), property, field);
					accessorWrapper = new AccessorWrapper(accessorWrapper) {

						@Override
						public Object evalGet(Object obj) {
							// TODO Auto-generated method stub
							return evalAccessor.get(obj);
						}

						@Override
						public boolean evalSet(Object obj, Object value) {
							// TODO Auto-generated method stub
							return evalAccessor.set(obj, value);
						}
					};
				}

				obj = accessorWrapper.evalGet(obj);
			}
		}

		return accessorWrapper;
	}
}
