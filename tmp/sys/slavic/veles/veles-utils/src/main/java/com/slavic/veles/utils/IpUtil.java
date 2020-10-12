package com.slavic.veles.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;

@Slf4j
public class IpUtil {
	private static CloseableHttpClient client = HttpClientBuilder.create().build();

	public static String getIpAddr() {
		HttpServletRequest request = SpringContextHolder.getRequest();
		String ipAddr = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ipAddr) || "unknown".equalsIgnoreCase(ipAddr)) ipAddr = request.getHeader("Proxy-Client-IP");
		if (StringUtils.isBlank(ipAddr) || "unknown".equals(ipAddr)) ipAddr = request.getRemoteAddr();
		if ("127.0.0.1".equals(ipAddr) || "0:0:0:0:0:0:0:1".equals(ipAddr)) {
			// 根据网卡获取本机配置的IP
			InetAddress inetAddr;
			try {
				inetAddr = InetAddress.getLocalHost();
				ipAddr = inetAddr.getHostAddress();
			} catch (final UnknownHostException e) {
				log.error("{}-{}","未知主机", e);
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照,分隔
		if (ipAddr != null && ipAddr.length() > 15 && ipAddr.indexOf(",") > 0) ipAddr = ipAddr.substring(0, ipAddr.indexOf(","));
		return ipAddr;
	}

	public static String getIpLocation() {
		return getIpLocation(getIpAddr());
	}

	public static String getIpLocation(String ip) {
		if (StringUtils.isBlank(ip)) return null;
		HttpGet httpGet = new HttpGet();
		String ipLocationApiUrlBase = "http://opendata.baidu.com/api.php?query=%s&co=&resource_id=6006&oe=utf8";
		httpGet.setURI(URI.create(String.format(ipLocationApiUrlBase, ip)));
		httpGet.setConfig(RequestConfig.custom().setConnectTimeout(1000).setConnectionRequestTimeout(1000).build());
		try (CloseableHttpResponse response = client.execute(httpGet)) {
			try (InputStream is = response.getEntity().getContent()) {
				JSONObject resp = JSON.parseObject(is, JSONObject.class);
				List<JSONObject> data = resp.getJSONArray("data").toJavaList(JSONObject.class);
				if (data.size() > 0) {
					return data.get(0).getString("location");
				}
			}
		} catch (IOException ignored) {
		}
		return null;
	}
}
