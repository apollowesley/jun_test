package org.nature.framework.helper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.nature.framework.util.ArrayUtil;
import org.nature.framework.util.CastUtil;
import org.nature.framework.util.ClassUtil;
import org.nature.framework.util.CodecUtil;
import org.nature.framework.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class FieldHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(FieldHelper.class);
	private Map<String, Object> beanObject = new HashMap<String, Object>();

	public void putInField(HttpServletRequest request, Object targetObject, Class<?> targetClass) {
		
		if (request.getContentType()==null||!request.getContentType().startsWith("multipart/form-data")) {
			readySetField(request, targetObject, targetClass);
			return ;
		}else {
			readySetMultipartField(request, targetObject, targetClass);
		}
	}
	@SuppressWarnings("unchecked")
	private void readySetMultipartField(HttpServletRequest request, Object targetObject, Class<?> targetClass) {
		final String tmpdir = System.getProperty("java.io.tmpdir");
			MultipartRequest multipartRequest = null;
			try {
				multipartRequest = new MultipartRequest(request, tmpdir, ConfigHelper.getMultipartMaxSize(), new FileRenamePolicy() {
					public File rename(File temp) {
						String fileName = CodecUtil.ISO2UTF8(temp.getName());
						File file = new File(tmpdir+UUID.randomUUID().toString()+fileName);
							temp.renameTo(file);
							return file;
					}
				});
			} catch (IOException e) {
				LOGGER.error("upload file failure ");
				throw new RuntimeException(e);
			}
			
			Enumeration<String> fileNames = multipartRequest.getFileNames();
			while (fileNames.hasMoreElements()) {
				String fileName = CastUtil.castString(fileNames.nextElement());
				File file = multipartRequest.getFile(fileName);
				if(file!=null){
					Field  field; 
					if (fileName.matches("\\w+")) {
						field = ReflectionUtil.getField(targetClass, fileName);
						ReflectionUtil.setField(targetObject, field, file);
					}else if (fileName.matches("\\w+\\[\\d+\\]")) {
						String fieldName = fileName.replaceFirst("\\[\\d+\\]", "");
						field = ReflectionUtil.getField(targetClass, fieldName);
						Class<?> fieldClass = field.getType();
						Object fieldObject = getBeanObj(fieldName, fieldClass,field,targetObject);
						if(fieldObject instanceof Collection){
							Collection<Object> collection = (Collection<Object>) fieldObject;
							collection.add(file);
						}
						ReflectionUtil.setField(targetObject, field, fieldObject);
					}
				}
			}
			
			Enumeration<String> parameterNames = multipartRequest.getParameterNames();
			while (parameterNames.hasMoreElements()) {
					String name = parameterNames.nextElement();
					String param = spliceParam(multipartRequest.getParameterValues(name));
					param = CodecUtil.ISO2UTF8(param);
					setField(targetObject, targetClass, name, param);
			}
	}
	
	private void readySetField(HttpServletRequest request, Object targetObject, Class<?> targetClass) {
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			try {
				String name = parameterNames.nextElement();
				String param = spliceParam(request.getParameterValues(name));
				setField(targetObject, targetClass, name, param);
			} catch (Exception e) {
				LOGGER.error(e.toString());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setField(Object targetObject, Class<?> targetClass, String name, String param){
		Field field = null;
		if (name.matches("\\w+")) {
			field = ReflectionUtil.getField(targetClass, name);
			Object fieldObject = castCommType(param, field.getType());
			ReflectionUtil.setField(targetObject, field, fieldObject);
		}else if (name.matches("\\w+\\.\\w+")) {
			String[] names = name.split("\\.");
			field = ReflectionUtil.getField(targetClass, names[0]);
			Class<?> fieldClass = field.getType();
			Object fieldObject = getBeanObj(names[0],fieldClass,field,targetObject);
			if(fieldObject instanceof Map){
				Map<String, Object> map = (Map<String, Object>) fieldObject;
				Object value = castCommType(param, field.getType());
				map.put(names[1], value);
			}
			ReflectionUtil.setField(targetObject, field, fieldObject);
		}else if (name.matches("\\w+\\[\\d+\\]")) {
			String fieldName = name.replaceFirst("\\[\\d+\\]", "");
			field = ReflectionUtil.getField(targetClass, fieldName);
			Class<?> fieldClass = field.getType();
			Object fieldObject = getBeanObj(fieldName, fieldClass,field,targetObject);
			if(fieldObject instanceof Collection){
				Collection<Object> collection = (Collection<Object>) fieldObject;
				collection.add(param);
			}
			ReflectionUtil.setField(targetObject, field, fieldObject);
		}else if (name.matches("\\w+\\[\\d+\\]\\.\\w+")) {
			String[] names = name.split("\\.");
			String fieldName = names[0].replaceFirst("\\[\\d+\\]", "");
			field = ReflectionUtil.getField(targetClass, fieldName);
			Class<?> fieldClass = field.getType();
			Object fieldObject = getBeanObj(fieldName,fieldClass,field,targetObject);
			Type genericType = field.getGenericType();
			Pattern compile = Pattern.compile("<([\\w\\.]+)>");
			Matcher matcher = compile.matcher(genericType.getTypeName());
			while (matcher.find()) {
				String classStr = matcher.group(1);
				Class<?> loadClass = ClassUtil.loadClass(classStr);
				Object beanObj = getBeanObj(names[0], loadClass,field,targetObject);
				if(beanObj instanceof Map){
					Map<String, Object> map = (Map<String, Object>) beanObj;
					map.put(names[1], param);
					if(fieldObject instanceof Collection){
						Collection<Object> collection = (Collection<Object>) fieldObject;
						collection.add(map);
						ReflectionUtil.setField(targetObject, field, collection);
					}
				}
			}
			
		}
	}

	private String spliceParam(String[] params) {
		String param=null;
		if (ArrayUtil.isNotEmpty(params)) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < params.length; i++) {
				sb.append(params[i]);
				if (i!=params.length-1) {
					sb.append(",");
				}
			}
			param = sb.toString();
		}
		return param;
	}

	
	@SuppressWarnings("unchecked")
	private <T> T getBeanObj(String name,Class<T> cls,Field field,Object targetObject) {
		Object object = beanObject.get(name+cls);
		if (object == null) {
			object = ReflectionUtil.getFieldValue(targetObject, field);
			if (object==null) {
				object = ReflectionUtil.newInstance(cls);
			}
			beanObject.put(name+cls, object);
		}
		return (T)object;
	}

	public static Object castCommType(String param, Class<?> typeName) {
		if (int.class.equals(typeName) || Integer.class.equals(typeName)) {
			return CastUtil.castInt(param);
		}else
		if (double.class.equals(typeName) ||Double.class.equals(typeName)) {
			return CastUtil.castDouble(param);
		}else
		if (float.class.equals(typeName) || Float.class.equals(typeName)) {
			return CastUtil.castFloat(param);
		}else
		if (long.class.equals(typeName) || Long.class.equals(typeName)) {
			return CastUtil.castLong(param);
		}else
		if (boolean.class.equals(typeName) || Boolean.class.equals(typeName)) {
			return CastUtil.castBoolean(param);
		}
		return param;
	}

}
