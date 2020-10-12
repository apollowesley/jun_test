package com.iotechn.iot.commons.entity;

/**
 * 内部往GW推送的消息模型
 */
public class NotifyMessage {

    public static final String NOTIFY_MESSAGE_TOPIC = "/notify_message_topic";

    private String secretKey;

    private String body;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
