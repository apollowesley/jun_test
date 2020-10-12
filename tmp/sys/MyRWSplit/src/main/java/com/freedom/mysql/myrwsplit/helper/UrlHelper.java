package com.freedom.mysql.myrwsplit.helper;

import java.util.ArrayList;
import java.util.List;

public class UrlHelper {
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(UrlHelper.class);

	public static void main(String[] args) throws Exception {
		String url = "jdbc:mysql://[106.1.1.1:3306,106.2.2.2:3306,106.3.3.3:3306]/ambari?zeroDateTimeBehavior=convertToNull";
		url = "jdbc:mysql://[106.1.1.1:3306]/ambari?zeroDateTimeBehavior=convertToNull";

		List<String> addresses = handle(url);
		for (String address : addresses) {
			System.out.println("address ---> " + address);
		}
	}

	public static ArrayList<String> handle(String url) throws Exception {
		ArrayList<String> result = new ArrayList<String>();
		//
		LOGGER.debug("url ---> " + url);
		if (false == url.startsWith("jdbc:mysql://[")) {
			throw new Exception("not legal mysql url,must start with jdbc:mysql://[");
		}
		int beginIndex = url.indexOf("[");
		if (-1 == beginIndex) {
			throw new Exception("not legal mysql url,no [ found");
		}
		int endIndex = url.indexOf("]");
		if (-1 == endIndex) {
			throw new Exception("not legal mysql url,no ] found");
		}
		String urlPrefix = url.substring(0, beginIndex);
		String ipPorts = url.substring(beginIndex + 1, endIndex);
		String urlSuffix = url.substring(endIndex + 1);
		LOGGER.debug("urlPrefix--->" + urlPrefix);
		LOGGER.debug("ipPorts--->" + ipPorts);
		LOGGER.debug("urlSuffix--->" + urlSuffix);
		//
		String[] array = ipPorts.split(",");
		// if (null == array || array.length <= 1) {
		// throw new Exception("not legal mysql url,at least 2 ip:port");
		// }
		for (String ipPort : array) {
			result.add(urlPrefix + ipPort + urlSuffix);
		}
		// 如果用户只写了1个ip:port,也就是用户只填了一个master,而没有填写slave地址
		// 修复此bug
		if (1 == result.size()) {
			String address = result.get(0);
			result.add(address);
		}
		return result;
	}
}
