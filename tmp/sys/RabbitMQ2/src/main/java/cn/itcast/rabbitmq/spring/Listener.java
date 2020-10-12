package cn.itcast.rabbitmq.spring;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: HuYi.Zhang
 * @create: 2018-05-23 18:04
 **/
@Component
public class Listener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue", durable = "true"),
            exchange = @Exchange(value = "spring.test.exchange", type = ExchangeTypes.TOPIC),
            key = {"#.#"}))
    public void listen(Map<String, String> msg){
        System.out.println("接收到消息：" + msg);
    }
}
