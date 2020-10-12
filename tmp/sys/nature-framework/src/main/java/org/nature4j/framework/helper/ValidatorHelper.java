package org.nature4j.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.nature4j.framework.util.ClassUtil;
import org.nature4j.framework.util.ReflectionUtil;
import org.nature4j.framework.validator.NatureValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(ValidatorHelper.class);
	private static Map<Class<?>, NatureValidator> validatorMap = new HashMap<Class<?>, NatureValidator>();
	public static void initValidator() {
		Set<Class<?>> classSet = ClassUtil.getClassSetByInterface(ConfigHelper.getAppBasePackage(), NatureValidator.class);
		for (Class<?> cls : classSet) {
			NatureValidator obj = (NatureValidator) ReflectionUtil.newInstance(cls);
			validatorMap.put(cls, obj);
		}
	}
	public static Map<Class<?>, NatureValidator>  getValidatorMap() {
		return validatorMap;
	}
	
	public static NatureValidator getValidator(Class<?> cls){
		NatureValidator object = validatorMap.get(cls);
		if(object==null){
			LOGGER.error(cls.getName()+" is not init or forget implements NatureValidator ");
		}
		return object;
	}
	public static void clear() {
		validatorMap.clear();
	}
}
