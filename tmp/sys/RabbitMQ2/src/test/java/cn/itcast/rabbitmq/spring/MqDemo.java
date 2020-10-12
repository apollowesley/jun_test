package cn.itcast.rabbitmq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: HuYi.Zhang
 * @create: 2018-05-23 18:08
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MqDemo {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSend() throws InterruptedException {
        // |String msg = "hello, Spring boot amqp";
        Map<String,String> msg = new HashMap<>();
        msg.put("name", "Jack");
        amqpTemplate.convertAndSend("spring.test.exchange","a.b.c.d", msg);

        Thread.sleep(1000L);
    }
}
