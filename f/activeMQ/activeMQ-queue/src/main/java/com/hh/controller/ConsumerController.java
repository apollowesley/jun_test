package com.hh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
/**
 * Created by Administrator on 15-11-16.
 * 消费者 接收消息
 */
@Controller
@RequestMapping("/")
public class ConsumerController {
    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 接受消息
     */
    public void receive(Destination destination) {
        TextMessage tm = (TextMessage) jmsTemplate.receive(destination);
        try {
            System.out.println("从队列" + destination.toString() + "收到了消息：\t"
                    + tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}
