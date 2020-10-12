package com.foo.common.base.utils.laboratory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * This class simulate the process of zznode IMTS login
 */
public class FooUtilsHttpHelperForItms {

	public static void main(String[] args) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// boolean isSuccess = false;

		String url = "http://search.maven.org/solrsearch/select?q=g:%22net.sf.ehcache%22+AND+a:%22ehcache%22&rows=1&wt=json";

		try {
			HttpGet httpget = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(5000).setConnectTimeout(5000)
					.setConnectionRequestTimeout(5000).build();
			httpget.setConfig(requestConfig);
			CloseableHttpResponse response1 = httpclient.execute(httpget);
			try {
				HttpEntity entity = response1.getEntity();
				// System.out.println("Login form get: "
				// + response1.getStatusLine());
				EntityUtils.consume(entity);
				// isSuccess = true;
			} finally {
				response1.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpclient.close();
		}
	}

	public static void main2(String[] args) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpResponse response1 = null;

		HttpPost httpPost = new HttpPost(
				"http://192.168.2.112:9980/itms/pages/security/loginAction.action");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("user.password", "asb#1234"));
		nvps.add(new BasicNameValuePair("user.userName", "administrator"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

		response1 = httpclient.execute(httpPost);

		EntityUtils.consume(response1.getEntity());

		System.out.println(response1.getStatusLine());
		httpPost.releaseConnection();

		System.out.println("---------------------");
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpPost httpPost2 = new HttpPost(
				"http://192.168.2.112:9980/itms/pages/generic/refreshUserTables.action");
		String responseBody = httpclient.execute(httpPost2, responseHandler);
		responseBody = new String(responseBody.getBytes("ISO-8859-1"), "UTF-8");
		System.out.println(responseBody);
		httpPost2.releaseConnection();
	}
}
