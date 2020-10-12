package com.iotechn.iot.gw.ws;

import com.alibaba.fastjson.JSONObject;
import com.iotechn.iot.commons.entity.NotifyMessage;
import com.iotechn.iot.gw.notify.NotifyHolder;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 其他系统推送给网关的消息 消费者 目前由于机器数量有限问题 先用着EMQ
 */
public class WSConsumer implements MqttCallback {
    @Autowired
    private NotifyHolder notifyHolder;


    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String msg = new String(message.getPayload());
        //从消息中获取变动后的参数和secretKey
        NotifyMessage notifyMessage = JSONObject.parseObject(msg, NotifyMessage.class);
        notifyHolder.process(notifyMessage.getSecretKey(),notifyMessage.getBody());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
