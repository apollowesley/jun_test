package com.laycms.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 作者 zbb:
 * @version 创建时间：2016年2月29日 上午9:09:44 类说明
 */

public class HttpClientUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * 通过post提交方式获取url指定的资源和数据
	 * 
	 * @param url
	 * @return
	 * @throws DajieHttpRequestException
	 */
	public static String postData(String url) {
		return postData(url, null);
	}

	/**
	 * 通过post提交方式获取url指定的资源和数据
	 * 
	 * @param url
	 * @param nameValuePairs
	 *            请求参数
	 * @return
	 * @throws DajieHttpRequestException
	 */
	public static String postData(String url, List<NameValuePair> nameValuePairs) {
		return postData(url, nameValuePairs, null);
	}

	/**
	 * 通过post提交方式获取url指定的资源和数据
	 * 
	 * @param url
	 * @param nameValuePairs
	 *            请求参数
	 * @param headers
	 *            请求header参数
	 * @return
	 * @throws DajieHttpRequestException
	 */
	public static String postData(String url, List<NameValuePair> nameValuePairs, Map<String, String> headers) {
		long start = System.currentTimeMillis();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse  response = null;
		try {
			if (headers != null && headers.size() > 0) {
				Set<Map.Entry<String, String>> set = headers.entrySet();
				for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
					Map.Entry<String, String> header = it.next();
					if (header != null) {
						httpPost.setHeader(header.getKey(), header.getValue());
					}
				}
			}
			if (nameValuePairs != null && nameValuePairs.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			}
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			String info = EntityUtils.toString(entity, "UTF-8");
			return info;
		} catch (Exception e) {
			logger.error("postData Exception url: {}", url, e);
			e.printStackTrace();
			return null;
		} finally {
			httpPost.releaseConnection();
			long interval = System.currentTimeMillis() - start;
			logger.debug("{} 请求耗时：{} ", url, interval);
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通过ContentType 为json的格式进行http传输
	 * 
	 * @param url
	 *            远程url
	 * @param content
	 *            传输内容
	 * @return
	 * @throws DajieHttpRequestException
	 */
	public static String postJSONData(String url, String content) {
		long start = System.currentTimeMillis();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse  response = null;
		try {
			if (content != null && content.length() > 0) {
				httpPost.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
			}
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			String info = EntityUtils.toString(entity, "UTF-8");
			return info;
		} catch (Exception e) {
			logger.error("postData Exception url: {}", url, e);
			e.printStackTrace();
			return null;
		} finally {
			httpPost.releaseConnection();
			long interval = System.currentTimeMillis() - start;
			logger.debug("{} 请求耗时：{} ", url, interval);
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通过get方法获取url资源的数据
	 * 
	 * @param url
	 *            服务器地址
	 * @return 返回响应的文本，如果请求发生异常，抛出DajieHttpRequestException
	 * @throws DajieHttpRequestException
	 */
	public static String getData(String url) {
		return getData(url, null);
	}

	/**
	 * 带header的get请求
	 * 
	 * @param url
	 *            服务器地址
	 * @param headers
	 *            添加的请求header信息
	 * @return 返回服务器响应的文本，出错抛出DajieHttpRequestException异常
	 * @throws DajieHttpRequestException
	 */
	public static String getData(String url, Map<String, String> headers) {
		long start = System.currentTimeMillis();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse  response = null;
		if (headers != null && headers.size() > 0) {
			Set<Map.Entry<String, String>> set = headers.entrySet();
			for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
				Map.Entry<String, String> header = it.next();
				if (header != null) {
					httpGet.setHeader(header.getKey(), header.getValue());
				}
			}
		}
		try {
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			String info = EntityUtils.toString(entity, "UTF-8");
			return info;
		} catch (Exception e) {
			logger.error("getData Exception url: {}", url, e);
			return null;
		} finally {
			httpGet.releaseConnection();
			long interval = System.currentTimeMillis() - start;
			logger.debug("{} 请求耗时：{} ", url, interval);
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * put 方式提交数据
	 * 
	 * @param url
	 *            ：服务器地址
	 * @param nameValuePairs
	 *            ：参数
	 * @return 返回 服务器返回的文本信息，报错会抛出异常
	 * @throws DajieHttpRequestException
	 */
	public static String putData(String url, List<NameValuePair> nameValuePairs) {
		long start = System.currentTimeMillis();
		HttpPut httpPut = new HttpPut(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse  response = null;
		try {
			if (nameValuePairs != null && nameValuePairs.size() > 0) {
				httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			}
			response = httpclient.execute(httpPut);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			String info = EntityUtils.toString(entity, "UTF-8");
			return info;
		} catch (Exception e) {
			logger.error("putData Exception url:{}", url, e);
			e.printStackTrace();
			return null;
		} finally {
			httpPut.releaseConnection();
			long interval = System.currentTimeMillis() - start;
			logger.debug("{} 请求耗时：{} ", url, interval);
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * delete 方式提交数据
	 * 
	 * @param url
	 *            服务器地址
	 * @return 返回 服务器返回的文本信息，报错会抛出异常
	 * @throws DajieHttpRequestException
	 */
	public static String deleteData(String url) {
		return deleteData(url, null);
	}

	/**
	 * delete 方式提交数据
	 * 
	 * @param url
	 *            服务器地址
	 * @return 返回 服务器返回的文本信息，报错会抛出异常
	 */
	public static String deleteData(String url, Map<String, String> headers) {
		long start = System.currentTimeMillis();
		HttpDelete httpDelete = new HttpDelete(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse  response = null;
		if (headers != null && headers.size() > 0) {
			Set<Map.Entry<String, String>> set = headers.entrySet();
			for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
				Map.Entry<String, String> header = it.next();
				if (header != null) {
					httpDelete.setHeader(header.getKey(), header.getValue());
				}
			}
		}
		try {
			response = httpclient.execute(httpDelete);
			HttpEntity entity = response.getEntity();
			String info = EntityUtils.toString(entity, "UTF-8");
			return info;
		} catch (Exception e) {
			logger.error("putData Exception url {} ", url, e);
			e.printStackTrace();
			return null;
		} finally {
			httpDelete.releaseConnection();
			long interval = System.currentTimeMillis() - start;
			logger.debug("{} 请求耗时：{} ", url, interval);
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 下载媒体资源
	 * 
	 * @param url
	 * @return
	 * @throws DajieHttpRequestException
	 */
	public static byte[] getMultipartData(String url) {
		long start = System.currentTimeMillis();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse  response = null;
		try {
			response = httpclient.execute(httpGet);
			byte[] result = EntityUtils.toByteArray(response.getEntity());
			return result;
		} catch (Exception e) {
			logger.debug("putData Exception url {} ", url, e);
			e.printStackTrace();
			return null;
		} finally {
			httpGet.releaseConnection();
			long interval = System.currentTimeMillis() - start;
			logger.debug("{} 请求耗时：{} ", url, interval);
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static CloseableHttpClient createSSLClientDefault() {

		try {

			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

				@Override
				public boolean isTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					return true;
				}

			}).build();

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

			return HttpClients.custom().setSSLSocketFactory(sslsf).build();

		} catch (KeyManagementException e) {

			e.printStackTrace();

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();

		} catch (KeyStoreException e) {

			e.printStackTrace();

		}

		return HttpClients.createDefault();

	}
	
	

	/**
	 * 通过ContentType 为json的格式进行http传输
	 * 
	 * @param url
	 *            远程url
	 * @param content
	 *            传输内容
	 * @return
	 * @throws DajieHttpRequestException
	 */
	public static String postJSONDataBySSL(String url, String content) {
		long start = System.currentTimeMillis();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpclient = createSSLClientDefault();
		CloseableHttpResponse  response = null;
		try {
			if (content != null && content.length() > 0) {
				httpPost.setEntity(new StringEntity(content, ContentType.TEXT_PLAIN));
			}
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			String info = EntityUtils.toString(entity, "UTF-8");
			return info;
		} catch (Exception e) {
			logger.error("postData Exception url: {}", url, e);
			return null;
		} finally {
			httpPost.releaseConnection();
			long interval = System.currentTimeMillis() - start;
			logger.debug("{} 请求耗时：{} ", url, interval);
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 通过ContentType 为json的格式进行http传输
	 * 
	 * @param url
	 *            远程url
	 * @param content
	 *            传输内容
	 * @return
	 * @throws DajieHttpRequestException
	 */
	public static String getDataBySSL(String url) {
		long start = System.currentTimeMillis();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpClient httpclient = createSSLClientDefault();
		CloseableHttpResponse  response = null;
		try {
			
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			String info = EntityUtils.toString(entity, "UTF-8");
			return info;
		} catch (Exception e) {
			logger.error("postData Exception url: {}", url, e);
			return null;
		} finally {
			httpGet.releaseConnection();
			long interval = System.currentTimeMillis() - start;
			logger.debug("{} 请求耗时：{} ", url, interval);
			try {
				if (response != null) {
					response.close();
				}
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
