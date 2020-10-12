/**
 * @author:稀饭
 * @time:下午1:03:43
 * @filename:DownThread.java
 */
package service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.ConnectionUtil;
import data.Data;

public class DownThread extends Thread {
	private String url = null;
	private static ConnectionUtil connectionUtil = null;
	private static String[] requestPropertieStrings = {
			"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
			"Accept-Encoding: gzip, deflate, sdch",
			"Accept-Language: zh-CN,zh;q=0.8",
			"Cache-Control: max-age=0",
			"Connection: keep-alive",
			"Host: repo1.maven.org",
			"Referer: http://repo1.maven.org/maven2/abbot/abbot/",
			"Upgrade-Insecure-Requests: 1",
			"User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36" };

	public DownThread(String url) {
		connectionUtil = new ConnectionUtil();
		this.url = url;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		getDir(url);
	}
	
	/**
	 * @Title: getDir
	 * @Description: TODO
	 * @param @param result
	 * @return void
	 */
	public static void getDir(String url) {

		String result = null;
		try {
			result = getContent(url, requestPropertieStrings);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result != null) {
			Document document = Jsoup.parse(result);
			Elements elements = document.select("a");
			// System.out.println(elements);
			for (Element element : elements) {
				if (ifExist(element.text()) == 2) {
					String fileUrl = url + element.text();
					String[] paths = fileUrl.replace(Data.url, "").split("/");
					String path = Data.filePath;
					for (int i = 0; i < paths.length - 1; i++) {
						path += paths[i] + "/";
					}
					// System.out.println(url + element.text());
					connectionUtil.downloadFile(url + element.text(), path,
							paths[paths.length - 1]);
				} else if (ifExist(element.text()) == 0) {
					if (!element.text().equals("../")) {
						getDir(url + element.text());
					}
				}
			}
		}
	}

	/**
	 * @Title: ifExist
	 * @Description: TODO
	 * @param @param path
	 * @param @return
	 * @return boolean
	 */
	public static int ifExist(String path) {
		int check = 0;
		if (path.contains(".md5")) {
			check = 1;
		}
		if (path.contains(".xml")) {
			check = 1;
		}
		if (path.contains(".ear")) {
			check = 1;
		}
		if (path.contains(".sha1")) {
			check = 2;
		}
		if (path.contains(".jar")) {
			check = 2;
		}
		if (path.contains(".poem")) {
			check = 2;
		}
		if (path.contains(".distribution-tgz")) {
			check = 1;
		}
		if (path.contains(".distribution-zip")) {
			check = 1;
		}
		if (path.contains(".gz")) {
			check = 1;
		}
		if (path.contains(".zip")) {
			check = 1;
		}
		return check;
	}

	/**
	 * @Title: getContent
	 * @Description: TODO
	 * @param @param htmlUrl
	 * @param @param requestProperties
	 * @param @return
	 * @param @throws IOException
	 * @return String
	 */
	public static String getContent(String htmlUrl, String[] requestProperties)
			throws IOException {
		String result = connectionUtil.sendGet(htmlUrl, requestProperties);
		return result;
	}

}
