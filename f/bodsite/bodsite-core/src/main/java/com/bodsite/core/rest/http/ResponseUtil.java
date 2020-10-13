package com.bodsite.core.rest.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bodsite.common.utils.BeanUtil;
import com.bodsite.common.utils.VerificationUtil;
import com.bodsite.core.rest.entity.ResultEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;

public class ResponseUtil {
	private static ObjectMapper objectMapper;

	public static ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			synchronized (ResponseUtil.class) {
				if (objectMapper == null) {
					objectMapper = new ObjectMapper();
					objectMapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
					objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
				}
			}
		}
		return objectMapper;
	}

	/**
	 * 返回json对象
	 * 
	 * @author bod
	 */
	public static String buildSuccessJsonStr(Object obj) {
		return buildSuccessJsonStr(obj, null);
	}

	/**
	 * 返回json对象 - 只包含对应字段
	 * 
	 * @author bod
	 */
	public static String buildSuccessJsonStrInclude(Object obj, String... includeFiledNames) {
		return buildSuccessJsonStrInclude(obj, null, includeFiledNames);
	}

	/**
	 * 返回json对象 - 根据map
	 * 
	 * @author bod
	 */
	public static String buildSuccessJsonStr(Map map) {
		return buildSuccessJsonStr(null, map);
	}

	/**
	 * 返回json对象 - 根据map 与 对象
	 * 
	 * @author bod
	 */
	public static String buildSuccessJsonStr(Object obj, Map map) {
		return buildJsonStr(ResultEntity.SUCCESS_CODE, null, obj, map);
	}

	/**
	 * 返回json对象 - 根据数组映射 与 对象
	 * 
	 * @author bod
	 */
	public static String buildSuccessJsonStr(Object obj, String[] propertyNames, Object[] propertyObjs) {
		Map<String, Object> map = arrayToMap(propertyNames, propertyObjs);
		return buildSuccessJsonStr(obj, map);
	}

	/**
	 * 返回json对象 - 根据数组映射 与 只包含对应字段(对象中)
	 * 
	 * @author bod
	 */
	public static String buildSuccessJsonStrInclude(Object obj, Map map, String... includeFiledNames) {
		obj = include(obj, includeFiledNames);
		return buildJsonStr(ResultEntity.SUCCESS_CODE, null, obj, map);
	}

	/**
	 * 过滤字段
	 * 
	 * @author bod     
	 */
	public static Object include(Object data, String... includeFiledNames) {
		VerificationUtil.notNull(data);
		if (data instanceof List) {
			List<?> tempList = (ArrayList<?>) data;
			List<Object> tempListNew = Lists.newArrayList();
			for (Object temp : tempList) {
				tempListNew.add(BeanUtil.include(temp, includeFiledNames));
			}
			return tempListNew;
		} else {
			return BeanUtil.include(data, includeFiledNames);
		}
	}

	/**
	 * 返回json对象 - 返回消息
	 * 
	 * @author bod
	 */
	public static String buildErrorJsonStr(String message) {
		return buildJsonStr(ResultEntity.ERROR_CODE, message, null, null);
	}

	public static String buildJsonStr(int code, String message) {
		return buildJsonStr(new ResultEntity(code, message, null, null));
	}

	public static String buildJsonStr(int code, String message, Object obj, Map map) {
		return buildJsonStr(new ResultEntity(code, message, obj, map));
	}

	public static String buildJsonStr(ResultEntity resultEntity) {
		try {
			return getObjectMapper().writeValueAsString(resultEntity);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> arrayToMap(String[] propertyNames, Object[] propertyObjs) {
		VerificationUtil.notEmpty(propertyNames);
		VerificationUtil.notEmpty(propertyObjs);
		Map<String, Object> map = new HashMap<String, Object>();
		int length = propertyNames.length > propertyObjs.length ? propertyObjs.length : propertyNames.length;
		for (int i = 0; i < length; i++) {
			map.put(propertyNames[i], propertyObjs[i]);
		}
		return map;
	}
}
