package com;
import javax.jms.Destination;


import com.hh.controller.ConsumerController;
import com.hh.controller.SendMsgController;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * Created by Administrator on 15-11-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/spring-*.xml")
public class SpringJmsTest {
    /**
     * 队列名queue1  这里使用jms配置文件中的数据
     */
    @Autowired
    private Destination queueDestination;

    /**
     * 队列消息生产者
     */
    @Autowired
   // @Qualifier("producerService")
    private SendMsgController sendMessage;

    /**
     * 队列消息生产者
     */
    @Autowired
    //@Qualifier("consumerService")
    private ConsumerController consumer;

    /**
     * 测试生产者向queue1发送消息
     */
    @Test
    public void testProduce() {
        String msg = "-------------------------this is a queue test 'Hello world!'";
        sendMessage.sendMessage(msg);
    }

    /**
     * 测试消费者从queue1接受消息
     */
    @Test
    public void testConsume() {
        consumer.receive(queueDestination);
    }


    //---------------------------===============以下使用自定义的Destination，不使用配置文件中的===================-------------------------
    @Test
    public void sendMsgTwo(){
       Destination myDestination = new ActiveMQQueue("queue2");
       sendMessage.sendMessage(myDestination,"----send my msg to myDestination");
    }
    @Test
    public  void testGetMsgTwo(){
        Destination myDestination = new ActiveMQQueue("queue2");
        consumer.receive(myDestination);
    }

}
