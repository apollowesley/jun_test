package com.evil.pojo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * �����ʵ����࣬ר�����ڼ̳�
 */
@SuppressWarnings("rawtypes")
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 7475673290409815270L;

	public abstract String getId();

	public abstract void setId(String id);

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		try {
			Class clazz = this.getClass();
			buffer.append(clazz.getSimpleName());
			buffer.append("{");
			//
			Field[] fs = clazz.getDeclaredFields(); // ��ø�������е��ֶ�
			Class ftype = null;
			String fname = null;
			Object fvalue = null;
			for (Field f : fs) {
				ftype = f.getType();
				if ((ftype.isPrimitive() || ftype == Integer.class
						|| ftype == Double.class || ftype == Float.class
						|| ftype == Character.class || ftype == Byte.class
						|| ftype == Boolean.class || ftype == Long.class
						|| ftype == Short.class || ftype == String.class)
						&& !Modifier.isStatic(f.getModifiers())) {
					fname = f.getName();// ����ֶε�����
					f.setAccessible(true);
					fvalue = f.get(this);// ����ֶε�ֵ
					buffer.append(fname);
					buffer.append(":");
					buffer.append(fvalue);
					buffer.append(" ");
				}
			}
			//
			buffer.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

}
