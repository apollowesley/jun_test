package com.example.bank.zhihutest;


import com.example.bank.entity.Bank;
import com.example.bank.entity.Citys;
import com.example.bank.entity.Provinces;
import com.example.bank.repository.BankRepository;
import com.example.bank.repository.CityRepository;
import com.example.bank.repository.ProvinceRepository;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CityTest {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void nameBank() {
        CloseableHttpClient httpClient = HttpClients.createDefault();


        String url = "http://www.lianhanghao.com/index.php?";
        HttpGet httpGet = new HttpGet(url);
        System.out.println(httpGet);

        HttpPost httpPost = new HttpPost(url);
        System.out.println(httpPost);
        //爬取银行名称
        List<Bank> banks = new ArrayList<>();
        List<Provinces> provinces = new ArrayList<>();
        List<Citys> citys = new ArrayList<>();

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            //获取响应码
            int status = httpResponse.getStatusLine().getStatusCode();

            if (status == 200) {
                String entity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                //采用jsoup解析抓取到的网页
                Document doc = Jsoup.parse(entity);
                //获取HTML标签中的内容
                Elements elements = doc.select("[style='background:none']").select("form");
                for (Element element : elements) {

                    //这里获取value的值（但是不知为何为空）
                    String value =element.select("option").attr("value");
                    System.out.println(value);


                    String bank = String.valueOf(String.valueOf(String.valueOf(element.select("[name='bank']").select("option").text())));
                    bank = bank.replace(" ", "#");
                    //System.out.println(bank);
                    String demo[] = bank.split("#");
                    for (int i = 1; i < demo.length; i++) {
                        //爬取到的银行名称
                        System.out.println(demo[i]);
                    }

                    String province = String.valueOf(element.select("[name='province']").select("option").text());
                    province = province.replace(" ", "#");
                    String demod[] = province.split("#");
                    for (int i = 1; i < demod.length; i++) {
                        //爬取到的省份地区
                        System.out.println(demod[i]);
                    }



                    //把银行名称封装list
//                    Bank bankm = new Bank();
//                    bankm.setBank_name(bank);
//                    banks.add(bankm);
//
//                    //把省份名称封装list
//                    Provinces provincesm = new Provinces();
//                    provincesm.setProvinces_name(province);
//                    provinces.add(provincesm);

                    //把城市名称封装list

                }

                EntityUtils.consume(httpResponse.getEntity());
            } else {
                EntityUtils.consume(httpResponse.getEntity());
            }

            //保存银行名称
//            for (Bank bank : banks) {
//                bank.setBank_name(bank.getBank_name());
//                bankRepository.save(bank);
//            }
//
//            //保存省份
//            for (Provinces province :provinces){
//                province.getProvinces_name();
//                provinceRepository.save(province);
//            }
//
//            //保存城市
//            for (Citys city :citys){
//                city.getProvince_id();
//                city.getCity_name();
//                cityRepository.save(city);
//            }

            httpResponse.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
