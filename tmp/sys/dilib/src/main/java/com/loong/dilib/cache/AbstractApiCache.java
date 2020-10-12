package com.loong.dilib.cache;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * 抽象缓存处理器（实现了buildKey方法）
 *
 * @author 张成轩
 */
public abstract class AbstractApiCache implements ApiCache {

	private static final char DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };
	private static final String DEGIST_AL = "MD5";
	private static final String CHARSET = "UTF-8";

	private static Gson gson = new Gson();

	@Override
	public String buildKey(Object api, Method method, Object[] params) {

		List<Object> ps = new LinkedList<Object>();
		// 加入Api方法（已包含Api类信息）
		ps.add(method.toGenericString());
		for (Object param : params)
			if (param instanceof Map)
				// 处理掉Map顺序问题
				ps.add(new LinkedHashMap<>((Map<?, ?>) param));
			else
				// 对于对象里面的无需Map及Set，暂时没有处理
				ps.add(param);
		// 处理成Json字符串
		String json = gson.toJson(ps);
		// 摘要处理
		return digest(json);
	}

	/**
	 * 信息摘要处理
	 * 
	 * @param str 字符串
	 * @return 摘要串
	 */
	public static String digest(String str) {

		// 信息摘要处理
		byte[] bytes;
		try {
			MessageDigest md = MessageDigest.getInstance(DEGIST_AL);
			bytes = md.digest(str.getBytes(CHARSET));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		// Hex编码处理
		int l = bytes.length;
		StringBuilder result = new StringBuilder(l << 1);
		for (int i = 0; i < l; i++) {
			result.append(DIGITS[(0xF0 & bytes[i]) >>> 4]);
			result.append(DIGITS[0x0F & bytes[i]]);
		}
		return result.toString();
	}

	@Override
	public abstract String get(Object api, Method method, String key);

	@Override
	public abstract void put(Object api, Method method, String key, String html, int expire);
}
