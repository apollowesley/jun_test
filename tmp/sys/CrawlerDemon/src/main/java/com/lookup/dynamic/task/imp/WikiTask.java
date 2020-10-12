/*
 * developer spirit_demon  @ 2015.
 */

package com.lookup.dynamic.task.imp;

import akka.actor.ActorRef;
import com.lookup.dynamic.request.TaskRequest;
import com.lookup.dynamic.request.TaskRequestMeta;
import com.lookup.dynamic.task.SingleTask;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.inject.Inject;

/**
 * Created by Administrator on 2015/6/17.
 */
@Qualifier
public class WikiTask extends SingleTask {
    @Inject
    private ActorRef wikiIataActor;

    public WikiTask() {

    }

    // @Scheduled(cron="0 0/1 *  * * ? ")   //每一分钟执行一次
    public void execute() {
        for (char index = 'A'; index <= 'Z'; index++) {
            TaskRequest request = new TaskRequest();
            request.setPaged(true);
            TaskRequestMeta requestMetaPage = requestMeta.clone();
            requestMetaPage.setUrl(requestMeta.getUrl() + "_" + String.valueOf(index));
            request.setRequestMeta(requestMetaPage);
            wikiIataActor.tell(request, ActorRef.noSender());
        }
    }
}

