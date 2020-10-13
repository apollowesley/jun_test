package com;

import com.hh.controller.SendMsgController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

/**
 * Created by Administrator on 15-11-17.
 * 通过配置监听器接收消息
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/spring-*.xml")
public class SpringJmsListenerTest {

    @Autowired
    @Qualifier("queueDestination3")//配置文件中只配置了接收queueDestination3的消息
    private Destination destination;

    @Autowired
    private SendMsgController sendMsgController;

    @Test
    public void sendMsg(){
       sendMsgController.sendMessage(destination,"----这里测试使用配置实现监听器测试消息接收");
    }
}
