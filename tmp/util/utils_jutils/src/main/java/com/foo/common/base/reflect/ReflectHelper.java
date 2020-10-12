package com.foo.common.base.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.foo.common.base.utils.CommonCrudHelper;

public class ReflectHelper {

	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(ReflectHelper.class);

	public static void main(String[] args) {
		System.out.println("\"\"");
		for (Field field : FieldUtils.getAllFields(CommonCrudHelper.class)) {
			logger.info("type is:{}", field.getType().getSimpleName());
		}
	}

	public static void main2(String[] args) throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		// ReflectPojoParent reflectPojo = new ReflectPojoChildOne();
		// for (Field field : reflectPojo.getClass().getSuperclass()
		// .getDeclaredFields()) {
		// logger.info(field.getName());
		// }

		ReflectPojoChildTwo object = new ReflectPojoChildTwo();
		object.setMyStrChildTwo("fuck");

		for (Field field : FieldUtils.getAllFields(ReflectPojoChildTwo.class)) {
			logger.info("field name is:{}", field.getName());
		}

		// logger.info("value is:{}",
		// FieldUtils.readDeclaredField(object, "myStrChildTwo"));
		BeanUtils.setProperty(object, "myStrChildTwo", "hehe");
		logger.info("value is:{}",
				BeanUtils.getProperty(object, "myStrChildTwo"));
		logger.info("value is:{}",
				BeanUtils.getSimpleProperty(object, "myStrChildTwo"));

	}
}
