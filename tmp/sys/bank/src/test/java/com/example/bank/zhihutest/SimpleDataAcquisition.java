package com.example.bank.zhihutest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author xgl
 */
public class SimpleDataAcquisition {

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String url = "http://www.lianhanghao.com/index.php/Index/index/p/1.html";
        HttpGet httpGet = new HttpGet(url);

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            //获取响应码
            int status = httpResponse.getStatusLine().getStatusCode();

            if (status == 200) {
                String entity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                //采用jsoup解析抓取到的网页
                Document doc = Jsoup.parse(entity);
                //获取HTML标签中的内容
                Elements elements = doc.getElementsByClass("t2");
                Elements tbody = elements.first().getElementsByTag("tbody").first().getElementsByTag("tr");

                for (Element tr : tbody) {
                    Elements td = tr.getElementsByTag("td");
                    String text1 = td.first().text();
                    String text = td.get(1).text();

                    System.out.println(text1 + "==" + text);
                }


                EntityUtils.consume(httpResponse.getEntity());
            } else {
                EntityUtils.consume(httpResponse.getEntity());
            }
            httpResponse.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
