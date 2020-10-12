package com.foo.common.base.utils.laboratory;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * This class simulate the process of get helloKitty film sources
 */
@Deprecated
public class FooUtilsHttpHelperForLolBox {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		// String filmName = "H31796";

		DefaultHttpClient httpclient = new DefaultHttpClient();

		String myURL = "http://lolbox.duowan.com/zdlRankData.php?serverName=%E7%94%B5%E4%BF%A1%E4%B8%80";

		HttpGet httpGet = new HttpGet(myURL);

		System.out.println(myURL);

		// Request timeout
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);

		// Read timeout
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				60000);

		// ResponseHandler<String> responseHandler = new BasicResponseHandler();

		httpclient.addResponseInterceptor(new HttpResponseInterceptor() {

			public void process(final HttpResponse response,
					final HttpContext context) throws HttpException,
					IOException {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					Header ceheader = entity.getContentEncoding();
					if (ceheader != null) {
						HeaderElement[] codecs = ceheader.getElements();
						for (int i = 0; i < codecs.length; i++) {
							if (codecs[i].getName().equalsIgnoreCase("gzip")) {
								response.setEntity(new GzipDecompressingEntity(
										response.getEntity()));
								return;
							}
						}
					}
				}
			}

		});

		HttpResponse response = httpclient.execute(httpGet);

		HttpEntity entity = response.getEntity();

		if (entity != null) {
			String content = EntityUtils.toString(entity);
			System.out.println(content);
			System.out.println("----------------------------------------");
			System.out.println("Uncompressed size: " + content.length());
		}

		// String responseBody = httpclient.execute(httpGet, responseHandler);

		// if (responseBody != null) {
		//
		// System.out.println(new String(responseBody.getBytes("ISO-8859-1"))
		// .substring(0, 200));
		// //
		// // Document doc = Jsoup.parse(responseBody, "UTF-8");
		// //
		// // Elements pngs = doc.select("table#archiveResult tr:gt(0)");
		// //
		// // for (Element element : pngs) {
		// // System.out.println(element.select("td.name").text());
		// //
		// // System.out.println(FooUtils.parseMagnetStr(element.select(
		// // "a:eq(1)").attr("href")));
		// // System.out.println("------------------");
		// // }
		// }
		httpGet.releaseConnection();

	}
}
