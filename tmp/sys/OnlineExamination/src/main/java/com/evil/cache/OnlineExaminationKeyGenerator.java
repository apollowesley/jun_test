package com.evil.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

import com.evil.util.StringUtil;


/**
 *自定义缓存key生成器 （格式:类名.方法名（参数1.参数2））
 */
public class OnlineExaminationKeyGenerator implements KeyGenerator {

	@SuppressWarnings("rawtypes")
	@Override
	public Object generate(Object arg0, Method arg1, Object... arg2) {
		Class clazz=arg0.getClass();
		String className=clazz.getSimpleName();
		String mname=arg1.getName();
		String prams=StringUtil.ObjectArrayToString(arg2);
		String keys=className+"."+mname+"("+prams+")";
		System.out.println(keys);
		return keys;
	}

}
