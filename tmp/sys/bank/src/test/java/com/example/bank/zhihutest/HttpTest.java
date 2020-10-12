package com.example.bank.zhihutest;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * 开户联行号获取
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpTest {

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String url = "http://www.lianhanghao.com/index.php?bank=1&province=1&city=35";
        HttpGet httpGet = new HttpGet(url);
//        System.out.println(httpGet);

        HttpPost httpPost = new HttpPost(url);
//        System.out.println(httpPost);

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
//            System.out.println(httpResponse);

            //获取响应码
            int status = httpResponse.getStatusLine().getStatusCode();
//            System.out.println(status);

            if (status == 200) {
                String entity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
//                System.out.println(entity);
                //采用jsoup解析抓取到的网页
                Document doc = Jsoup.parse(entity);
                //获取HTML标签中的内容
                Elements elements = doc.select("table[class='t2']").select("tbody");
//                String bankName = doc.select("table[class='t2']").select("tbody").select("[style]").get(0).text();

                for (Element element : elements) {
                    Elements es = element.select("td");
                    String bankNumber = String.valueOf(element.select("[align='center']").text());
                    String bankName = String.valueOf(element.select("[align='left']").text());

                    bankNumber = bankNumber.replace(" ", "#");

                    String demod[] = bankNumber.split("#");
                    for (int i = 0; i < demod.length; i++) {
                        //爬取到的联行号
                        System.out.println(demod[i]);
                    }


                    bankName = bankName.replace(" ", "#");
                    String demo[] = bankName.split("#");
                    for (int i = 0; i < demo.length; i = i + 5) {
                        //爬取到的开户支行
                        System.out.println(demo[i]);
                    }

                }
                // }
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
