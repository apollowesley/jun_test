package com.loong.dilib.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.loong.dilib.annotation.DICookie;
import com.loong.dilib.annotation.DIHeader;
import com.loong.dilib.annotation.DIJson;
import com.loong.dilib.annotation.DIPath;
import com.loong.dilib.annotation.DIRequest;
import com.loong.dilib.annotation.DIResponse;
import com.loong.dilib.exception.DIAnnotationException;

/**
 * 注释校验器
 *
 * @author 张成轩
 */
public class ApiChecker {

	/**
	 * 校验Api注释
	 * 
	 * @param targetClass Api接口类
	 * @throws DIAnnotationException
	 */
	public static void check(Class<?> targetClass) throws DIAnnotationException {

		for (Method method : targetClass.getMethods())
			check(method);
	}

	/**
	 * 校验方法注释
	 * 
	 * @param method Api接口方法
	 * @throws DIAnnotationException
	 */
	public static void check(Method method) throws DIAnnotationException {

		// 校验请求
		DIRequest request = method.getAnnotation(DIRequest.class);
		if (request == null)
			throw new DIAnnotationException("Miss DIRequest. method: " + method.getName());
		// 是否为Post方式
		boolean isPost = (request.method() == DIRequest.Method.POST);
		// 请求方式是否有Json参数
		boolean hasJson = false;
		// 请求方式是否有默认参数
		boolean hasDefalut = false;
		// 遍历方法参数注释
		for (Annotation[] pAs : method.getParameterAnnotations()) {
			boolean isJson = false;
			int aCount = 0;
			// 遍历参数注释
			for (Annotation pa : pAs)
				if (pa instanceof DIJson) {
					aCount++;
					isJson = true;
				} else if (pa instanceof DIHeader)
					aCount++;
				else if (pa instanceof DICookie)
					aCount++;
				else if (pa instanceof DIPath)
					aCount++;
			if (aCount > 1)
				// 不能同时使用DIHeader、DICookie、DIJson、DIPath
				throw new DIAnnotationException(
						"In one parameter, DIHeader, DICookie, DIJson, DIPath can not be used simultaneously. method: "
								+ method.getName());
			else if (aCount == 0)
				hasDefalut = true;
			else if (isJson)
				if (hasJson)
					// 在一个方法里，DIJson只能使用一次
					throw new DIAnnotationException(
							"In one method, DIJson can only be used once. method: " + method.getName());
				else
					hasJson = true;
		}
		if (hasJson && hasDefalut)
			// 一个方法里不能同时拥有Json参数和默认参数
			throw new DIAnnotationException(
					"In one method, Can not use both DIJson and DEFALUT parameter. method: " + method.getName());
		else if (hasJson && !isPost)
			// Json参数必须使用在Post方法中
			throw new DIAnnotationException("DIJson must be used in Post method. method: " + method.getName());

		// 校验响应
		DIResponse response = method.getAnnotation(DIResponse.class);
		if (response != null && response.value() == DIResponse.Type.XML)
			throw new DIAnnotationException("XML response has not yet been developed. method: " + method.getName());
	}
}
