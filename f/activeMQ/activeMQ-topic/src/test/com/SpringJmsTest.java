package com;

import com.hh.controller.TopicProviderController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

/**
 * Created by Administrator on 15-11-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/spring-*.xml")
public class SpringJmsTest {

    @Autowired
    private Destination destination;
    @Autowired
    private TopicProviderController topicProviderController;

    @Test
    public  void sendMsg(){
        topicProviderController.publish(destination,"this is topic test");
    }
}
