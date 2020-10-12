/*
 * developer spirit_demon  @ 2015.
 */

package com.lookup.dynamic.proxy;

import common.FilePathUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * Created by Administrator on 2015/8/14 0014.
 */
public class KuaidailiTest {
    @Test
    public void test() {

        String result = FilePathUtil.getFile("file/kuaidaili.html");

        // System.out.println(result);
        Document doc = Jsoup.parse(result);

      /* 分页处理
       Elements element = doc.select("#listnav > ul a[href]");

      List<String> list = RegexTool.getResult(element.text(), "[\\d{1,4}]+");

        System.out.println(java.util.Arrays.toString(list.toArray()));
       System.out.println(list.get(list.size()-1));*/

       /* Elements elements = doc.select("table[class=table table-bordered table-striped] tbody tr");
        String sql = "INSERT INTO t_proxy (num_row_is, vc_proxy_ip, num_proxy_port, vc_proxy_local) VALUES (%s,'%s',%s ,'%s');";
        for(int index = 0 ;index<elements.size();index++)
         {
             Elements tds =  elements.get(index).select("td");
             String ip = tds.get(0).text();
             String port = tds.get(1).text();
             String local= tds.get(4).text();


             System.out.println(String.format(sql,index+16,ip,port,local));

         }*/
        Elements elements = doc.select("table[class=table table-bordered table-striped] tbody tr");
        //String sql = "INSERT INTO t_proxy (num_row_is, vc_proxy_ip, num_proxy_port, vc_proxy_local) VALUES (%s,'%s',%s ,'%s');";
        for (int index = 0; index < elements.size(); index++) {
            Elements tds = elements.get(index).select("td");
            String ip = tds.get(0).text();
            String port = tds.get(1).text();
            String local = tds.get(4).text();

            System.out.println(ip + ": " + port + "type: " + local);
            // System.out.println(String.format(sql,index+16,ip,port,local));

        }


    }
}
