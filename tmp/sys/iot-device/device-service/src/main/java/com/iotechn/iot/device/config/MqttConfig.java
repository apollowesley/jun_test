package com.iotechn.iot.device.config;

import com.iotechn.iot.device.mapper.DeviceParamMapper;
import com.iotechn.iot.device.mqtt.PushCallback;
import com.iotechn.iot.device.service.biz.DeviceBizServiceImpl;
import com.iotechn.iot.executor.api.ExecutionOpenService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-12
 * Time: 下午8:30
 */
@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfig {
    private String host;
    private String username;
    private String password;
    private String clientId;

    @Autowired
    private DeviceBizServiceImpl deviceBizService;
    @Autowired
    private DeviceParamMapper deviceParamMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ExecutionOpenService executionOpenService;

    @Bean
    public MqttClient mqttClient() throws Exception{
        MqttClient mqttClient = new MqttClient(host, clientId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        mqttClient.setCallback(new PushCallback(deviceBizService,deviceParamMapper,stringRedisTemplate,mqttClient,executionOpenService));
        mqttClient.connect(options);
        mqttClient.subscribe("server",0);
        return mqttClient;
    }









    public void setHost(String host) {
        this.host = host;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}
