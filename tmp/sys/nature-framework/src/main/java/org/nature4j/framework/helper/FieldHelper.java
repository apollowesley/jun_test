package org.nature4j.framework.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.nature4j.framework.core.NatureMap;
import org.nature4j.framework.util.ArrayUtil;
import org.nature4j.framework.util.CastUtil;
import org.nature4j.framework.util.ClassUtil;
import org.nature4j.framework.util.CodecUtil;
import org.nature4j.framework.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(FieldHelper.class);
	private static String tmpdir= System.getProperty("java.io.tmpdir");
	private Map<String, Object> beanObject = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	public void putInField(NatureMap requestParams, Object targetObject, Class<?> targetClass) {
		Set<Entry<String, Object>> entrySet = requestParams.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value != null) {
				if (value instanceof File) {
					File file = (File) value;
					if(file!=null){
						Field  field; 
						if (name.matches("\\w+")) {
							field = ReflectionUtil.getField(targetClass, name);
							if (field!=null) {
								ReflectionUtil.setField(targetObject, field, file);
							}
						}else if (name.matches("\\w+\\[\\d+\\]")) {
							String fieldName = name.replaceFirst("\\[\\d+\\]", "");
							field = ReflectionUtil.getField(targetClass, fieldName);
							if (field!=null) {
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
				}else{
					setField(targetObject, targetClass, name, CastUtil.castString(value));
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setField(Object targetObject, Class<?> targetClass, String name, String param){
		Field field = null;
		if (name.matches("\\w+")) {
			field = ReflectionUtil.getField(targetClass, name);
			if (field!=null) {
				Object fieldObject = castCommType(param, field.getType());
				ReflectionUtil.setField(targetObject, field, fieldObject);
			}
		}else if (name.matches("\\w+\\.\\w+")) {
			String[] names = name.split("\\.");
			field = ReflectionUtil.getField(targetClass, names[0]);
			if (field!=null) {
				Class<?> fieldClass = field.getType();
				Object fieldObject = getBeanObj(names[0],fieldClass,field,targetObject);
				if(fieldObject instanceof Map){
					Map<String, Object> map = (Map<String, Object>) fieldObject;
					Object value = castCommType(param, field.getType());
					map.put(names[1], value);
				}
				ReflectionUtil.setField(targetObject, field, fieldObject);
			}
		}else if (name.matches("\\w+\\[\\d+\\]")) {
			String fieldName = name.replaceFirst("\\[\\d+\\]", "");
			field = ReflectionUtil.getField(targetClass, fieldName);
			if (field!=null) {
				Class<?> fieldClass = field.getType();
				Object fieldObject = getBeanObj(fieldName, fieldClass,field,targetObject);
				if(fieldObject instanceof Collection){
					Collection<Object> collection = (Collection<Object>) fieldObject;
					collection.add(param);
				}
				ReflectionUtil.setField(targetObject, field, fieldObject);
			}
		}else if (name.matches("\\w+\\[\\d+\\]\\.\\w+")) {
			String[] names = name.split("\\.");
			String fieldName = names[0].replaceFirst("\\[\\d+\\]", "");
			field = ReflectionUtil.getField(targetClass, fieldName);
			if (field!=null) {
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
	}

	private static String spliceParam(String[] params) {
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
	
	
	public static void putInRequestParams(HttpServletRequest request, NatureMap requestParams) {
		if (!ServletFileUpload.isMultipartContent(request)) {
			readySetField(request,requestParams);
		}else {
			readySetMultipartField(request,requestParams);
		}
		if (request.getDispatcherType()==DispatcherType.FORWARD) {
			requestParams.remove("token");
		}
		
	}

	private static void readySetMultipartField(HttpServletRequest request, NatureMap requestParams) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletContext servletContext = request.getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				LOGGER.info("upload file failure ");
				throw new RuntimeException(e);
			}
			for (FileItem item : items) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = CodecUtil.ISO2UTF8(item.getString());
					requestParams.put(name, value);
				}else {
					InputStream inputStream = null;
					FileOutputStream fos = null;
					try {
					inputStream = item.getInputStream();
					if (inputStream.available() > 0 && inputStream.available() <= ConfigHelper.getMultipartMaxSize()) {
						String name = item.getFieldName();
						String filename = item.getName();
						int index = -1;
						if ((index=filename.lastIndexOf(File.separatorChar))>-1) {
							filename = filename.substring(index+1);
						}
						if (Pattern.matches(ConfigHelper.getAllowFileRegex(), filename.toLowerCase())) {
							byte[] buff = new byte[1024];
							int len = -1;
							File file = new File(tmpdir + File.separatorChar + UUID.randomUUID().toString() + filename);
							fos = new FileOutputStream(file);
							while ((len = inputStream.read(buff)) > 0) {
								fos.write(buff, 0, len);
							}
							requestParams.put(name, file);
						} else {
							LOGGER.info("upload file failure because " + filename + " is not match "
									+ ConfigHelper.getAllowFileRegex());
						}
					}
				} catch (IOException e) {
						LOGGER.info("upload file io close error ");
						throw new RuntimeException(e);
					}finally {
						try {
							if (fos!=null) {
								fos.flush();
								fos.close();
							}
							if (inputStream!=null) {
								inputStream.close();
							}
						} catch (IOException e) {
							LOGGER.info("upload file stream close error ");
							throw new RuntimeException(e);
						}
						
					}
				}
			}
	}

	private static void readySetField(HttpServletRequest request, NatureMap requestParams) {
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement();
			String param = spliceParam(request.getParameterValues(name));
			requestParams.put(name,param);
		}
	}
	
}
