package cn.itcast.rabbitmq.spring;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 虎哥
 */
@Component
public class MyListener {

    @RabbitListener(queues = "simple_queue")
    public void listen(String msg){
        System.out.println("msg = " + msg);
    }
}
