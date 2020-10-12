package com.qiaozhi.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qiaozhi.service.SendSms;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class SendSmsImpl implements SendSms {

    @Override
    public boolean send(String phoneNum, String templateCode, Map<String, Object> code) {
        //连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GG6hnJn8cHRFcVsYRfU", "vl55wCwsxWxQ6GMRa9feznf9pP8r1a");
        IAcsClient client = new DefaultAcsClient(profile);

        //构建请求
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        //自定义参数
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", "笨比乔治在线");
        request.putQueryParameter("TemplateCode", templateCode);
        //构建一个短信的验证码

        request.putQueryParameter("TemplateParam", JSON.toJSONString(code));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
