package com.zb.task;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("mqMsgTask")
@Lazy(false)
public class MqMsgTasks {

    //@Scheduled(cron = "0/1 * * * * ?")
    public void emailMsgTasks() {
        //System.out.println("任务测试999");
    }
}