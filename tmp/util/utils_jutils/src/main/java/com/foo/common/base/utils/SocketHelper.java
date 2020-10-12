package com.foo.common.base.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class SocketHelper {
	static Logger logger = LoggerFactory.getLogger(SocketHelper.class);

	public static void main(String[] args) throws Exception {
		List<String> urlList = Lists.newArrayList();
		urlList.add("64.233.161.104");
		urlList.add("64.233.167.104");
		urlList.add("64.233.189.104");
		urlList.add("209.116.186.246");
		urlList.add("222.255.120.15");
		urlList.add("64.233.161.104");
		urlList.add("64.233.167.104");
		urlList.add("64.233.189.104");
		urlList.add("64.233.161.89");

		Socket socket = null;
		for (String url : urlList) {
			boolean reachable = false;
			try {
				socket = new Socket();
				// set timeout to 3 seconds
				socket.connect(new InetSocketAddress(url, 80), 3000);
				reachable = true;
			} catch (Exception e) {
				reachable = false;
			} finally {
				if (socket != null)
					try {
						socket.close();
					} catch (IOException e) {
					}
			}
			logger.info("url of:{} and reachable is:{} ", url, reachable);
		}

	}
}
