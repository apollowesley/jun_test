package com;

/**
 * Created by Administrator on 15-11-13.
 */

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

/**
 * 演示如何从MQ接受消息，和发送差不多
 *
 * 1.初始化连接工厂ConnectionFactory
 *
 * 2.创建连接Connection
 *
 * 3. 创建会话session
 *
 * 4.打开队列createQueue
 *
 * 5.获得消息消费者MessageConsumer
 *
 * 6.使用MessageConsumer接受消息
 *
 * 7. 关闭会话session和连接Connection
 *
 */
public class Receiver {

    public static void main(String[] args) {
        String user = ActiveMQConnection.DEFAULT_USER;//activeMQ默认用户 admin
        String password = ActiveMQConnection.DEFAULT_PASSWORD;//activeMQ默认密码 admin
        String url = ActiveMQConnection.DEFAULT_BROKER_URL;//activeMQ 默认连接地址
        String subject = "myActiveMQ";//这里定义的subject要与发送者的一致  这样才能正确收发
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            final Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            // MessageConsumer负责接受消息
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {

                public void onMessage(Message msg) {
                    TextMessage message = (TextMessage) msg;
                    try {
                        String hello = message.getStringProperty("hello");
                        System.out.println("收到消息：\t" + hello);
                        session.commit();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            // 为了演示接受消息，这里把关闭会话和连接注释掉了。
            // session.close();
            // connection.close();
            System.out.println("--------------");
        } catch (JMSException e) {
            e.printStackTrace();
            System.out.println("--------------e:"+e.getMessage());
        }
    }


}
