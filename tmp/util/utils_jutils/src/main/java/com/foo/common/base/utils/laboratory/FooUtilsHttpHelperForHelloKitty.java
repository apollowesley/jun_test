package com.foo.common.base.utils.laboratory;

import java.net.URLEncoder;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.foo.common.base.utils.FooUtils;

/**
 * This class simulate the process of get helloKitty film sources
 * 
 * ("httpclient3.0,废弃使用")
 */
@Deprecated
public class FooUtilsHttpHelperForHelloKitty {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		String filmName = "H31796";

		DefaultHttpClient httpclient = new DefaultHttpClient();

		String myURL = "http://www.torrentkitty.com/search/"
				+ URLEncoder.encode(filmName, "utf-8") + "/";

		HttpGet httpGet = new HttpGet(myURL);

		System.out.println(myURL);

		// Request timeout
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);

		// Read timeout
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				60000);

		httpGet.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Accept-Charset", "UTF-8,*;q=0.5");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate");
		httpGet.setHeader("Accept-Language", "Accept-Language");
		httpGet.setHeader("Connection", "keep-alive");
		// httpGet.setHeader("Cookie", cookie.getName() + "=" +
		// cookie.getValue());
		httpGet.setHeader("Host", "www.torrentkitty.com");
		httpGet.setHeader("Referer", "http://www.torrentkitty.com/search/"
				+ filmName + "/");
		httpGet.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.14 (KHTML, like Gecko) Chrome/24.0.1293.0 Safari/537.14");

		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = httpclient.execute(httpGet, responseHandler);

		if (responseBody != null) {

			responseBody = new String(responseBody.getBytes("ISO-8859-1"),
					"UTF-8");

			Document doc = Jsoup.parse(responseBody, "UTF-8");

			Elements pngs = doc.select("table#archiveResult tr:gt(0)");

			for (Element element : pngs) {
				System.out.println(element.select("td.name").text());

				System.out.println(FooUtils.parseMagnetStr(element.select(
						"a:eq(1)").attr("href")));
				System.out.println("------------------");
			}
		}
		httpGet.releaseConnection();

	}
}
