import com.alibaba.fastjson.JSONObject;
import com.iotechn.iot.commons.entity.NotifyMessage;
import com.iotechn.iot.device.DeviceApplication;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceApplication.class)
public class IotDeviceApplicationTests {


    @Autowired
    private MqttClient mqttClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void contextLoads() {
    }


    @Test
    public void paramNotify() throws Exception {
        //推送给网关 ，网关 会通过WS 推送给客户端
        String sk = "tgx9ivhmhdhlw3eho5hi5d48a5sf0epfbc64179e8d2044bab52c6a410b43ae3e";
        if (stringRedisTemplate.opsForSet().isMember("GW_PARAM_NOTIFY",sk)) {
            //查询redis里面通知列表
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(2);
            NotifyMessage notifyMessage = new NotifyMessage();
            notifyMessage.setSecretKey(sk);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("param","test msg");
            notifyMessage.setBody(jsonObject.toJSONString());
            mqttMessage.setPayload(JSONObject.toJSONString(notifyMessage).getBytes());
            mqttClient.publish(NotifyMessage.NOTIFY_MESSAGE_TOPIC, mqttMessage);
        }
    }
}
