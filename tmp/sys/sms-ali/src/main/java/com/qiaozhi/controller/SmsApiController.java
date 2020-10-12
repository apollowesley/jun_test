package com.qiaozhi.controller;

import com.qiaozhi.service.SendSms;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin  //跨域支持
public class SmsApiController {
    @Autowired
    private SendSms sendSms;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/sms/{phone}")
    public String code(@PathVariable("phone") String phone){
        //调用发送方法
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isBlank(code)){
            return phone + ":" + code + "已存在，还没有过期";
        }

        //生成验证码，并存储到redis中
         code = UUID.randomUUID().toString().substring(0, 6);
        HashMap<String, Object> param = new HashMap<>();
        param.put("code",code);

        boolean isSend = sendSms.send(phone, "SMS_193516148", param);

        if (isSend){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return phone + ":" + code + "发送成功";
        }else {
            return "发送失败！";
        }

    }
}
