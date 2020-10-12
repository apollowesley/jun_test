package com.iotechn.iot.gw.config;

import com.iotechn.iot.commons.entity.NotifyMessage;
import com.iotechn.iot.gw.ws.ApiWebsocket;
import com.iotechn.iot.gw.ws.WSConsumer;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yeauty.standard.ServerEndpointExporter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-12-24
 * Time: 上午11:53
 */
@Configuration
public class WebsocketConfig {

    @Value("${com.iotechn.iot.gw.mqtt.host}")
    private String mqttHost;
    @Value("${com.iotechn.iot.gw.mqtt.username}")
    private String mqttUsername;
    @Value("${com.iotechn.iot.gw.mqtt.password}")
    private String mqttPwd;

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public ApiWebsocket apiWebsocket() {
        return new ApiWebsocket();
    }

    @Bean
    public WSConsumer wsConsumer() {
        return new WSConsumer();
    }

    @Bean
    public MqttClient mqttClient() throws Exception {
        MqttClient mqttClient = new MqttClient(mqttHost, mqttUsername, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(mqttUsername);
        options.setPassword(mqttPwd.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        mqttClient.setCallback(wsConsumer());
        mqttClient.connect(options);
        mqttClient.subscribe(NotifyMessage.NOTIFY_MESSAGE_TOPIC,0);
        return mqttClient;
    }
}
