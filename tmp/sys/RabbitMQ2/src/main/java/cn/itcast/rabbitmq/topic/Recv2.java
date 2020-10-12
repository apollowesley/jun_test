package cn.itcast.rabbitmq.topic;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import cn.itcast.rabbitmq.util.ConnectionUtil;

/**
 * 消费者2
 */
public class Recv2 {
    /**
     * 指定第一个消息队列的名称
     */
    private final static String QUEUE_NAME = "topic_exchange_queue_2";
    /**
     * 指定交换机的名称
     */
    private final static String EXCHANGE_NAME = "topic_exchange_test";

    public static void main(String[] argv) throws Exception {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //绑定队列到交换机，同时指定需要订阅的routing key。订阅 insert、update、delete
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.*");
        //定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
                                       byte[] body) throws IOException {
                //body是消息体
                String message = new String(body);
                System.out.println("[消费者2] received："+message+"!");
            }
        };
        //监听队列，自动ACK(确认)
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}