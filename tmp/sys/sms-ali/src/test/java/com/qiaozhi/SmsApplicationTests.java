package com.qiaozhi;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class SmsApplicationTests {

    @Test
    void contextLoads() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GG6hnJn8cHRFcVsYRfU", "vl55wCwsxWxQ6GMRa9feznf9pP8r1a");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", "15237990283");
        request.putQueryParameter("SignName", "笨比乔治在线");
        request.putQueryParameter("TemplateCode", "SMS_193516148");
        //构建一个短信的验证码
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",2233);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(map));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
