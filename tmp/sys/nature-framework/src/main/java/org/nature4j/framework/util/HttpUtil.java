package org.nature4j.framework.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import java.util.concurrent.TimeUnit;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
	
	/**
	 * 发送普通post提交
	 * @param url 地址
	 * @param natureMap 数据
	 * @return 返回结果
	 */
	public static String post(String url,Map<String, Object> natureMap){
		String result = "";
		try {
			OkHttpClient okHttpClient = new OkHttpClient();
			Builder bodyBuilder = new FormBody.Builder();
			if (natureMap!=null) {
				Set<String> keySet = natureMap.keySet();
				for (String key : keySet) {
					bodyBuilder.add(key,CastUtil.castString(natureMap.get(key)));
				}
			}
			Request request = new Request.Builder()
				      .url(url)
				      .post(bodyBuilder.build())
				      .build();
			Response response = okHttpClient.newCall(request).execute();
			result = response.body().string();
		} catch (IOException e) {
			result="{\"status\":-1,\"msg\":\"connect error\"}";
		}
		return result;
	}
	
	/**
	 * 发送复杂表单提交（包含文件）
	 * @param url 请求地址
	 * @param natureMap 请求数据
	 * @return 返回结果
	 */
	public static String mutipost(String url,Map<String, Object> natureMap){
		String result = "";
		try {
			OkHttpClient okHttpClient = new OkHttpClient();
			MultipartBody.Builder multipartBody = new MultipartBody.Builder();
			multipartBody.setType(MultipartBody.FORM);
			Set<String> keySet = natureMap.keySet();
			for (String key : keySet) {
				Object value = natureMap.get(key);
				if (value instanceof File) {
					File file =(File)value;
					RequestBody	fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
					multipartBody.addFormDataPart(key,file.getName(),fileBody);
				}else{
					multipartBody.addFormDataPart(key, CastUtil.castString(value));
				}
			}
			Request request = new Request.Builder()
				      .url(url)
				      .post(multipartBody.build())
				      .build();
			Response response = okHttpClient.newCall(request).execute();
			result = response.body().string();
		} catch (IOException e) {
			result="{\"status\":-1,\"msg\":\"connect error\"}";
		}
		return result;
	}
	
	/**
	 * 发送get请求
	 * @param url 请求地址
	 * @return 返回结果
	 */
	public static String get(String url) throws Exception{
		String result = "";
			OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
					.connectTimeout(10, TimeUnit.SECONDS)
					.readTimeout(10, TimeUnit.SECONDS)
					.build();
		Request.Builder builder = new Request.Builder();
		Request request = builder
				      .url(url)
				      .build();
			Response response = okHttpClient.newCall(request).execute();
			result = response.body().string();
		return result;
	}
	
}
