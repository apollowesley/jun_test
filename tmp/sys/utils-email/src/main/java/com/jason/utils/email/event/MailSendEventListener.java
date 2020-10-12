package com.jason.utils.email.event;

import java.util.EventListener;

/**
 * 消息发送成功
 * @author jason
 *
 */
public interface MailSendEventListener extends EventListener{
    public void execute(MailSendEventObject  mailSendEventObject);
}