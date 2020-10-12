package com.foo.common.base.utils.laboratory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import com.foo.common.base.utils.FooUtils;
import com.foo.common.base.utils.laboratory.httpClient.MavenSearchResultJson;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This class simulate the process of zznode IMTS login
 */
public class HttpClientHelper {

	static final Logger logger = FooUtils.getRootLogger();

	public static void main33(String[] args) throws ClientProtocolException,
			IOException {
		String groupId = "org.springframework";
		String artifactId = "spring-web";

		logger.info("groupId:【{}】 and artifactId:【{}】 analyze start.", groupId,
				artifactId);

		Stopwatch stopwatch = Stopwatch.createStarted();

		CloseableHttpClient httpclient = HttpClients.createDefault();

		String url = "http://search.maven.org/solrsearch/select?q=g:%22com.jhlabs%22+AND+a:%22filters %22&rows=1&wt=json";

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet httpGet = new HttpGet(url);
		String responseBody = httpclient.execute(httpGet, responseHandler);
		// responseBody = new String(responseBody.getBytes("ISO-8859-1"),
		// "UTF-8");

		Gson gson = new GsonBuilder().serializeNulls().create();
		MavenSearchResultJson object = new MavenSearchResultJson();
		object = gson.fromJson(responseBody, MavenSearchResultJson.class);

		stopwatch.stop();
		logger.info(
				"groupId:【{}】 and artifactId:【{}】 analyze end.Newest version:【{}】",
				groupId, artifactId, object.getVersion());
		logger.info("all cost time:{}", stopwatch);
	}

	public static void main5(String[] args) throws IOException {

		String groupId = "commons-net";
		String artifactId = "commons-net";

		logger.info("groupId:【{}】 and artifactId:【{}】 analyze start.", groupId,
				artifactId);

		Stopwatch stopwatch = Stopwatch.createStarted();

		CloseableHttpClient httpclient = HttpClients.createDefault();

		String url = "http://search.maven.org/solrsearch/select?q=g:%22"
				+ groupId + "%22+AND+a:%22" + artifactId + "%22&rows=1&wt=json";

		logger.info("request url is:{}", url);

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		HttpGet httpGet = new HttpGet(url);
		String responseBody = httpclient.execute(httpGet, responseHandler);
		// responseBody = new String(responseBody.getBytes("ISO-8859-1"),
		// "UTF-8");

		Gson gson = new GsonBuilder().serializeNulls().create();
		MavenSearchResultJson object = new MavenSearchResultJson();
		object = gson.fromJson(responseBody, MavenSearchResultJson.class);

		stopwatch.stop();
		logger.info(
				"groupId:【{}】 and artifactId:【{}】 analyze end.Newest version:【{}】",
				groupId, artifactId, object.getVersion());
		logger.info("all cost time:{}", stopwatch);

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

	public static void main(String[] args) {
		HttpClient httpClient = new HttpClient();

		httpClient.getState().setCredentials(
				new AuthScope("127.0.0.1", 8080, null),
				new UsernamePasswordCredentials("tomcat", "tomcat"));
		httpClient.getParams().setAuthenticationPreemptive(true);

		// GetMethod getMethod = new GetMethod(
		// "http://127.0.0.1:8080/feiYnn-manager/text/start?path=/");
		GetMethod getMethod = new GetMethod(
				"http://127.0.0.1:8080/feiYnn-manager/text/vminfo");
		getMethod.setDoAuthentication(true);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			byte[] responseBody = getMethod.getResponseBody();
			System.out.println(new String(responseBody));
		} catch (HttpException e) {
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
	}
}
