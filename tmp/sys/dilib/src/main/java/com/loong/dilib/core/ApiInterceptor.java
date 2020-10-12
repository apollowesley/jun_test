package com.loong.dilib.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loong.dilib.annotation.DICache;
import com.loong.dilib.annotation.DICookie;
import com.loong.dilib.annotation.DIHeader;
import com.loong.dilib.annotation.DIJson;
import com.loong.dilib.annotation.DIParam;
import com.loong.dilib.annotation.DIPath;
import com.loong.dilib.annotation.DIRequest;
import com.loong.dilib.annotation.DIResponse;
import com.loong.dilib.cache.ApiCache;
import com.loong.dilib.exception.DIConnectException;
import com.loong.dilib.exception.DIFormatException;
import com.loong.dilib.tool.Beans;
import com.loong.dilib.tool.Https;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Api代理实体处理类
 */
public class ApiInterceptor implements MethodInterceptor {

	private static Log log = LogFactory.getLog(ApiInterceptor.class);

	private static Gson gson = new Gson();

	private static final String CONTENT = "Content-Type";
	private static final String CONTENT_DEFAULT = "application/x-www-form-urlencoded";
	private static final String CONTENT_JSON = "application/json";

	private static final boolean DEFAULT_SHOW_LOG = false;

	private boolean showLog = DEFAULT_SHOW_LOG;
	private ApiCache apiCache;

	@Override
	public Object intercept(Object object, Method method, Object[] params, MethodProxy methodProxy)
			throws DIConnectException, DIFormatException {

		// 起始时间
		long ds = System.currentTimeMillis();
		// 各阶段时间
		long cs = 0, ce = 0, de;

		// 缓存配置
		DICache cache = method.getAnnotation(DICache.class);
		if (cache == null)
			// 获取类响应注释
			for (Class<?> clas : object.getClass().getInterfaces())
				if ((cache = clas.getAnnotation(DICache.class)) != null)
					break;
		if (cache != null && cache.value() <= 0)
			cache = null;

		String html = null, key = null;
		// 判断缓存
		if (cache != null) {
			if (apiCache == null)
				log.warn("API cache server does not exist");
			else {
				cs = System.currentTimeMillis();

				// 获取缓存键
				key = apiCache.buildKey(object, method, params);
				// 获取缓存
				html = apiCache.get(object, method, key);

				ce = System.currentTimeMillis();
			}
		}

		Map<String, Object> pathMap = null, headerMap = null;
		StringBuilder cookieStr = null, paramStr = null, url = null;

		if (html == null) {
			// REST参数
			pathMap = new HashMap<String, Object>();
			// 请求头
			headerMap = new LinkedHashMap<String, Object>();
			// Cookie
			cookieStr = new StringBuilder();
			// 请求参数
			paramStr = new StringBuilder();

			// 遍历参数
			Annotation[][] psAs = method.getParameterAnnotations();
			// 参数索引
			int pi = 0;
			for (Annotation[] pAs : psAs) {
				// 请求头注释
				DIHeader headerAnn = null;
				// Cookie注释
				DICookie cookieAnn = null;
				// DIParam
				DIParam paramAnn = null;
				// REST参数注释
				DIPath pathAnn = null;
				// Json注释
				DIJson jsonAnn = null;
				// 遍历注释判断
				for (Annotation pa : pAs)
					if (pa instanceof DIHeader)
						headerAnn = (DIHeader) pa;
					else if (pa instanceof DICookie)
						cookieAnn = (DICookie) pa;
					else if (pa instanceof DIParam)
						paramAnn = (DIParam) pa;
					else if (pa instanceof DIPath)
						pathAnn = (DIPath) pa;
					else if (pa instanceof DIJson)
						jsonAnn = (DIJson) pa;
				// 处理方法参数
				if (headerAnn != null)
					// 请求头
					if (headerAnn.value().isEmpty())
						pushMap(params[pi], headerMap);
					else
						pushMap(headerAnn.value(), params[pi], headerMap);
				else if (cookieAnn != null) {
					// Cookie
					if (cookieAnn.value().isEmpty())
						pushStr(params[pi], ";", cookieStr);
					else
						pushStr(cookieAnn.value(), params[pi], ";", cookieStr);
				} else if (pathAnn != null) {
					// 请求头
					if (pathAnn.value().isEmpty())
						pushMap(params[pi], pathMap);
					else
						pushMap(pathAnn.value(), params[pi], pathMap);
				} else if (jsonAnn != null) {
					// 请求Json参数处理
					jsonParam(params[pi], paramStr);
					// 添加头部信息
					pushMap(CONTENT, CONTENT_JSON, headerMap);
				} else {
					// 请求字符串参数处理
					if (paramAnn == null || paramAnn.value().isEmpty())
						pushStr(params[pi], "&", paramStr);
					else
						pushStr(paramAnn.value(), params[pi], "&", paramStr);
					// 添加头部信息
					pushMap(CONTENT, CONTENT_DEFAULT, headerMap);
				}
				pi++;
			}

			// 获取方法请求注释
			DIRequest request = method.getAnnotation(DIRequest.class);
			// 获取类请求注释
			DIRequest apiRequest = null;
			for (Class<?> clas : object.getClass().getInterfaces())
				if ((apiRequest = clas.getAnnotation(DIRequest.class)) != null)
					break;

			// 获取请求基础信息
			url = Https.getUrl(apiRequest, request, pathMap);

			cs = System.currentTimeMillis();
			// 发送请求
			if (request.method() == DIRequest.Method.POST)
				html = Https.post(url, headerMap, cookieStr, paramStr, request.charset());
			else
				html = Https.get(url, headerMap, cookieStr, paramStr, request.charset());
			ce = System.currentTimeMillis();

			if (cache != null && apiCache != null && html != null)
				apiCache.put(object, method, key, html, cache.value());
		}

		// 返回对象
		Object result = null;
		// 处理响应信息
		DIResponse response = method.getAnnotation(DIResponse.class);
		if (response == null)
			// 获取类响应注释
			for (Class<?> clas : object.getClass().getInterfaces())
				if ((response = clas.getAnnotation(DIResponse.class)) != null)
					break;
		if (html != null)
			if (method.getReturnType() == String.class)
				// 按字符串处理
				result = html;
			else if (response == null || response.value() == DIResponse.Type.JSON)
				// Jsonp处理
				result = fromJson(html, method.getGenericReturnType());
			else if (response.value() == DIResponse.Type.JSONP)
				// Json处理
				result = fromJsonp(html, method.getGenericReturnType());

		if (showLog) {
			de = System.currentTimeMillis();
			StringBuilder logStr = new StringBuilder();
			logStr.append("[dilib] api: ").append(method.getDeclaringClass().getName()).append(".")
					.append(method.getName()).append("\r\n");
			if (url != null)
				logStr.append("request: ").append(url).append("\r\n");
			if (paramStr != null)
				logStr.append("parameter: ").append(paramStr).append("\r\n");
			logStr.append("response: ").append(html).append("\r\n").append("consuming: ").append(ce - cs).append("/")
					.append(de - ds).append(" ms");
			log.info(logStr);
		}
		return result;
	}

	/**
	 * @param bean Bean对象
	 * @param map Map集合
	 */
	private static void pushMap(Object bean, Map<String, Object> map) {

		Map<String, Object> hs = Beans.convert(bean);
		map.putAll(hs);
	}

	/**
	 * @param name 名称
	 * @param value 值
	 * @param map Map集合
	 */
	private static void pushMap(String name, Object value, Map<String, Object> map) {

		if (!map.containsKey(name))
			map.put(name, value);
	}

	/**
	 * @param bean Bean对象
	 * @param split 分隔符
	 * @param str 字符串
	 */
	private static void pushStr(Object bean, String split, StringBuilder str) {

		Map<String, Object> cs = Beans.convert(bean);
		for (Entry<String, Object> c : cs.entrySet())
			pushStr(c.getKey(), c.getValue(), split, str);
	}

	/**
	 * @param name 名称
	 * @param value 值
	 * @param split 分隔符
	 * @param str 字符串
	 */
	private static void pushStr(String name, Object value, String split, StringBuilder str) {

		if (str.length() != 0)
			str.append(split);
		str.append(name).append("=");
		if (value != null)
			str.append(value);
	}

	/**
	 * 添加Json参数
	 * 
	 * @param bean bean对象
	 * @param param 参数串
	 */
	private static void jsonParam(Object bean, StringBuilder param) {

		if (param.length() == 0)
			param.append(gson.toJson(bean));
	}

	/**
	 * 获取对象
	 * 
	 * @param json Json字符串
	 * @param type 类型
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	private static <T> T fromJson(String json, Type type) {

		try {
			return (T) gson.fromJson(json, type);
		} catch (JsonSyntaxException e) {
			throw new DIFormatException("Response body format error: " + json, e);
		}
	}

	/**
	 * 获取对象
	 * 
	 * @param jsonp Jsonp字符串
	 * @param type 类型
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	private static <T> T fromJsonp(String jsonp, Type type) {

		int start = jsonp.indexOf("{");
		int end = jsonp.lastIndexOf("}") + 1;
		if (start < 0 || start >= end)
			throw new DIFormatException("Response body format error: " + jsonp);
		try {
			return (T) gson.fromJson(jsonp.substring(start, end), type);
		} catch (JsonSyntaxException e) {
			throw new DIFormatException("Response body format error: " + jsonp, e);
		}
	}

	/**
	 * @param apiCache 缓存处理器
	 */
	public void setApiCache(ApiCache apiCache) {

		this.apiCache = apiCache;
	}

	/**
	 * @param showLog 是否显示日志
	 */
	public void setShowLog(boolean showLog) {

		this.showLog = showLog;
	}
}
