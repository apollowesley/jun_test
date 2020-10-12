/*
 * developer spirit_demon  @ 2015.
 */

package com.lookup.dynamic.actor.imp;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import com.lookup.dynamic.actor.base.BaseActor;
import com.lookup.dynamic.ext.Actor;
import com.lookup.dynamic.request.TaskRequest;
import com.lookup.dynamic.response.TaskResponse;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2015/8/25 0025.
 */
@Actor
public class WikiIataListActor extends BaseActor {

    @Inject
    @Named("httpActor")
    private ActorRef httpActor;
    @Inject
    @Named("wikiIataListParser")
    private ActorRef wikiIataListParser;

    @Override
    public void parallelProcess(ActorRef sender, Object message, ActorRef recipient, ActorContext context) {
        //处理 task 请求消息
        if (message instanceof TaskRequest) {
            //可以对当前request 的数据进行处理
            httpActor.tell(message, self());
        }
        //处理 http返回消息
        else if (message instanceof TaskResponse) {
            TaskResponse response = (TaskResponse) message;
            wikiIataListParser.tell(response, self());
        } else {
            unhandled(message);
        }
    }
}
