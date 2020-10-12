/*
 * developer spirit_demon  @ 2015.
 */

package com.lookup.dynamic.task.imp;

import akka.actor.ActorRef;
import com.lookup.dynamic.request.TaskRequest;
import com.lookup.dynamic.task.SingleTask;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.inject.Inject;

/**
 * Created by Administrator on 2015/6/17.
 */
@Qualifier
public class ProxyTask extends SingleTask {
    @Inject
    private ActorRef kuaiDaiLiActor;

    public ProxyTask() {

    }

    // @Scheduled(cron="0 0/1 *  * * ? ")   //spring task每一分钟执行一次 可以替换成quartz
    public void execute() {
        TaskRequest request = new TaskRequest();
        request.setRequestMeta(requestMeta);
        kuaiDaiLiActor.tell(request, ActorRef.noSender());
    }

}

