/*
 * developer spirit_demon  @ 2015.
 */

package com.lookup.dynamic.actor.imp;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.lookup.dynamic.ext.Actor;
import com.lookup.dynamic.request.TaskRequest;
import com.lookup.dynamic.request.TaskRequestMeta;
import com.lookup.dynamic.response.TaskResponse;
import com.lookup.dynamic.util.RegexTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by Administrator on 2015/8/25 0025.
 */
@Actor
public class KuaiDaiLiActor extends UntypedActor {
    private Logger logger = LoggerFactory.getLogger("actor");
    @Inject
    @Named("httpActor")
    private ActorRef httpActor;
    @Inject
    @Named("kuaiDaiLiParseActor")
    private ActorRef kuaiDaiLiParseActor;

    @Override
    public void onReceive(Object message) throws Exception {
        //处理 task 请求消息
        if (message instanceof TaskRequest) {
            //可以对当前request 的数据进行转发
            TaskRequest request = (TaskRequest) message;
            httpActor.tell(message, self());
        }
        //处理 http返回消息  这里主要是分页处理 ，可以做些些参数变更
        else if (message instanceof TaskResponse) {
            TaskResponse response = (TaskResponse) message;
            kuaiDaiLiParseActor.tell(response, self());
            boolean paged = response.isPaged();
            if (!paged) {
                String html = response.getResponseMeta().getBody();
                Document doc = Jsoup.parse(html);
                Elements elements = doc.select("#listnav > ul a[href]");
                if (!elements.isEmpty()) {
                    List<String> list = RegexTool.getResult(elements.text(), "[\\d{1,4}]+");
                    if (!list.isEmpty()) {
                        int pageSize = Integer.parseInt(list.get(list.size() - 1));
                        for (int i = 1; i <= pageSize; i++) {
                            TaskRequest request = new TaskRequest();
                            request.setPaged(true);
                            //放置请求过快 ，配置延迟
                            //request.setDelayTime(15*1000);
                            TaskRequestMeta requestMetaPage = response.getRequestMeta().clone();
                            requestMetaPage.setUrl(response.getRequestMeta().getUrl() + String.valueOf(i));
                            request.setRequestMeta(requestMetaPage);
                            httpActor.tell(request, self());
                        }
                    } else {
                        logger.debug("获取分页数为0");
                    }
                } else {
                    logger.debug("获取分页标记失败，或当前无需分页");
                }
            }
        } else {
            unhandled(message);
        }

    }
}
