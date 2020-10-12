package com.iotechn.iot.device.hook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.iot.device.entity.DeviceDo;
import com.iotechn.iot.device.mapper.DeviceMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PipedReader;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-29
 * Time: 下午4:13
 */
@Controller
@RequestMapping("/emq")
public class EMQWebHook {

    private static final Logger logger = LoggerFactory.getLogger(EMQWebHook.class);

    @Autowired
    private DeviceMapper deviceMapper;

    @Value("${mqtt.monitor}")
    private String monitor;

    private static OkHttpClient okHttpClient;

    @ResponseBody
    @RequestMapping("/hook")
    public String hook(@RequestBody String request) {

        JSONObject jsonObject = JSONObject.parseObject(request);
        String action = jsonObject.getString("action");
        String clientId = jsonObject.getString("client_id");
        DeviceDo deviceDo = new DeviceDo();
        if ("client_connected".equals(action)) {
            logger.info("[device] connected clientId=" + clientId);
            deviceDo.setStatus(1);
            try {
                String string = getOkHttpClient().newCall(new Request.Builder()
                        .url(monitor + "/api/v3/connections/" + clientId).build()).execute().body().string();
                logger.info("[调试] 获取设备状态 response" + string);
                JSONArray array = JSONObject.parseArray(string);
                if (!CollectionUtils.isEmpty(array)) {
                    JSONObject deviceInfo = array.getJSONObject(0);
                    String ipaddress = deviceInfo.getString("ipaddress");
                    deviceDo.setLastIp(ipaddress);
                }
            }catch (Exception e) {
                logger.error("[emqx dashboard] 调用 restful api 异常", e);
            }
            deviceMapper.update(deviceDo,
                    new EntityWrapper<DeviceDo>()
                            .eq("name", clientId));

        } else if ("client_disconnected".equals(action)) {
            logger.info("[device] disconnected clientId=" + clientId);
            deviceDo.setStatus(0);
            deviceMapper.update(deviceDo,
                    new EntityWrapper<DeviceDo>()
                            .eq("name", clientId));

        }
        return "";
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (EMQWebHook.class) {
                if (okHttpClient == null) {
                    okHttpClient = buildBasicAuthClient("admin","public");
                }
            }
        }
        return okHttpClient;
    }

    private static OkHttpClient buildBasicAuthClient(final String name, final String password) {
        return new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(name, password);
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        }).build();
    }

}
