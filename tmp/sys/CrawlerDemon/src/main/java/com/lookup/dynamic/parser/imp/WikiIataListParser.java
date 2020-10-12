/*
 * developer spirit_demon  @ 2015.
 */

package com.lookup.dynamic.parser.imp;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import com.lookup.dynamic.actor.base.BaseActor;
import com.lookup.dynamic.model.ProxyInfo;
import com.lookup.dynamic.parser.Parser;
import com.lookup.dynamic.response.TaskResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/15 0015.
 */

public class WikiIataListParser extends BaseActor implements Parser<ProxyInfo> {
    private Logger logger = LoggerFactory.getLogger("parse");

    @Override
    public List<ProxyInfo> parse(String targetString) {


        List<ProxyInfo> proxyList = new ArrayList<ProxyInfo>();
      /*   Document doc = Jsoup.parse(targetString);

         Elements elements = doc.select("table[class=table table-bordered table-striped] tbody tr");
         for (int index = 0; index < elements.size(); index++) {

             Elements tds = elements.get(index).select("td");
             String ip = tds.get(0).text();
             String port = tds.get(1).text();
             String type = tds.get(3).text();
             String position = tds.get(4).text();
             ProxyInfo proxyInfo = new ProxyInfo();
             proxyInfo.setProxyIp(ip);
             proxyInfo.setProxyPort(Integer.valueOf(port));
             proxyInfo.setProxyType(type);
             proxyInfo.setProxyPosition(position);
             proxyList.add(proxyInfo);
         }*/
        return proxyList;
    }
   /* @Inject
    private MysqlServiceMapper mysqlServiceMapper;*/

    @Override
    public void parallelProcess(ActorRef sender, Object message, ActorRef recipient, ActorContext context) {
        if (message instanceof TaskResponse) {
            TaskResponse response = (TaskResponse) message;

            String html = response.getResponseMeta().getBody();

            List<ProxyInfo> proxyList = this.parse(html);

            for (ProxyInfo info : proxyList) {

                //logger.info(info.toString());

                //mysqlServiceMapper.insertProxy(info);
            }

        }
    }

}
