package com.hh.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Administrator on 15-11-17.
 */
public class TopicLinstenerTwo implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage =(TextMessage)message;
        try {
            System.out.println("-----TopicLinstenerTwo接收到消息：\t"+textMessage.getText());
        }catch (JMSException e){
            System.out.println("-----err：\t"+e.getMessage());
        }
    }
}
