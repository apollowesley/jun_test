/**
 * @author:稀饭
 * @time:上午12:00:19
 * @filename:Work.java
 */
package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import util.StringRiceUtil;

import data.Data;

public class Work_rice {
	// 預線程數
	private static int corePoolSize = 5;
	// 開啟的最大線程數
	private static int maximumPoolSize = 10;
	// 空閒線程存活時間
	private static long keepAliveTime = 3;
	private static ThreadPoolExecutor threadPool = null;
	private static BlockingQueue<Runnable> linkedBlockingQueue = null;
	private static Map<String, String> versionMap;

	public static void main(String[] args) {
		setThreadPool();
		versionMap = new HashMap<String, String>();
		ArrayList<String> list = work();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(Data.url + list.get(i));
//			DownThread downThread = new DownThread(Data.url + list.get(i));
//			threadPool.execute(downThread);
//		}
//		threadPool.shutdown();
	}

	// 開啟線程池
	public static void setThreadPool() {
		linkedBlockingQueue = new ArrayBlockingQueue<Runnable>(4);
		threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
				keepAliveTime, TimeUnit.DAYS, linkedBlockingQueue,
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	@Test
	public static void getVersionMessage(String content) {
		String name = content.substring(1, content.indexOf("."));
		String versionString = content.substring(content.indexOf(">") + 1,
				content.indexOf("</"));
		versionMap.put(name, versionString);
	}

	/**
	 * @描述：从pom文件获取数据
	 * @时间：下午10:59:51
	 * @开发者：稀饭
	 * @测试：
	 */
	public static ArrayList<String> work() {
		ArrayList<String> list = new ArrayList<String>();
		// TODO Auto-generated method stub
		Document document = null;
		try {
			document = Jsoup.parse(new File("pom.xml"), "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements elements = document.select("properties");

		if (elements.size() > 0) {
			Element property = document.select("properties").get(0);
			String propertyString = StringRiceUtil.replaceBlank(property
					.toString());
			Pattern p = Pattern
					.compile("<(\\w)+?.version>((\\d.)+?RELEASE|(\\d.)+\\d)</(\\w)+?.version>");
			Matcher m = p.matcher(propertyString);
			while (m.find()) {
				String matcherString = m.group();
				getVersionMessage(matcherString);
			}
		}
		elements = document.select("dependency");
		for (int i = 0; i < elements.size(); i++) {
			String first = "";
			String[] firsts = elements.get(i).select("groupid").text()
					.split("\\.");
			for (int j = 0; j < firsts.length; j++) {
				first += firsts[j] + "/";
			}
			String second = elements.get(i).select("artifactid").text() + "/";
			String third = elements.get(i).select("version").text() + "/";
			if (third.contains("$")) {
				third = third.substring(2, third.length() - 10);
				String version = versionMap.get(third);
				if (version != null) {
					third = version + "/";
				}
			}
			list.add(first + second + third);
		}
		return list;
	}
}
