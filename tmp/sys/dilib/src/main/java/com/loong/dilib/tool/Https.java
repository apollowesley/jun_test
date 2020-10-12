package com.loong.dilib.tool;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.loong.dilib.annotation.DIRequest;
import com.loong.dilib.exception.DIConnectException;

/**
 * HTTP工具类
 *
 * @author 张成轩
 */
public class Https {

	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	/**
	 * 获取访问地址
	 * 
	 * @param apiRequest api请求注释
	 * @param request 方法请求注释
	 * @param paths REST路径参数集
	 * @return url
	 */
	public static StringBuilder getUrl(DIRequest apiRequest, DIRequest request, Map<String, Object> paths) {

		StringBuilder url = new StringBuilder();
		if (apiRequest == null)
			// 没有方法请求注释，直接返回类注释的url
			url.append(request.value());
		else {
			// 类注释访问地址
			String base = apiRequest.value();
			// 方法注释访问地址
			String servlet = request.value();

			// 拼装url
			boolean baseEnd = base.endsWith("/");
			boolean startUrl = servlet.startsWith("/");
			url.append(base);
			if (baseEnd && startUrl)
				url.append(servlet.substring(1));
			else if (!baseEnd && !startUrl)
				url.append("/").append(servlet);
			else
				url.append(servlet);
		}

		// 处理REST参数
		for (Entry<String, Object> path : paths.entrySet()) {
			// 待替换参数标识
			String sign = new StringBuilder().append("{").append(path.getKey()).append("}").toString();
			// 获取替换索引
			int start = url.indexOf(sign);
			if (start == -1)
				continue;
			int end = start + sign.length();
			// 替换参数
			url.replace(start, end, path.getValue() == null ? "" : path.getValue().toString());
		}
		return url;
	}

	/**
	 * GET方式请求
	 * 
	 * @param url 访问地址
	 * @param header 请求头
	 * @param cookie 请求Cookie
	 * @param param 请求参数
	 * @param charset 编码
	 * @return 响应体
	 * @throws DIConnectException
	 */
	public static String get(StringBuilder url, Map<String, Object> header, StringBuilder cookie, StringBuilder param,
			String charset) throws DIConnectException {

		String u = new StringBuilder(url).append("?").append(param).toString();
		HttpGet get = new HttpGet(u);
		// 添加请求头
		for (Entry<String, Object> h : header.entrySet())
			get.addHeader(h.getKey(), h.getValue().toString());
		// 添加Cookie
		if (cookie.length() != 0)
			get.addHeader("Cookie", cookie.toString());
		return getResponse(get, charset);
	}

	/**
	 * POST方式请求
	 * 
	 * @param url 访问地址
	 * @param header 请求头
	 * @param cookie 请求Cookie
	 * @param param 请求参数
	 * @param charset 编码
	 * @return 响应体
	 * @throws DIConnectException
	 */
	public static String post(StringBuilder url, Map<String, Object> header, StringBuilder cookie, StringBuilder param,
			String charset) throws DIConnectException {

		HttpPost post = new HttpPost(url.toString());
		StringEntity entity = new StringEntity(param.toString(), charset);
		// 添加参数
		post.setEntity(entity);
		// 添加请求头
		for (Entry<String, Object> h : header.entrySet())
			post.addHeader(h.getKey(), h.getValue().toString());
		// 添加Cookie
		if (cookie.length() != 0)
			post.addHeader("Cookie", cookie.toString());
		return getResponse(post, charset);
	}

	/**
	 * 请求
	 * 
	 * @param request 请求
	 * @param charset 编码
	 * @return 响应
	 * @throws IOException
	 */
	private static String getResponse(HttpUriRequest request, String charset) throws DIConnectException {

		String httpStr = null;
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(request);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				// 请求成功
				HttpEntity httpEntity = response.getEntity();
				httpStr = EntityUtils.toString(httpEntity, charset);
			} else
				throw new DIConnectException("Connection failed. error code:" + code);
		} catch (IOException e) {
			throw new DIConnectException("Connection failed", e);
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
				}
		}
		return httpStr;
	}
}
