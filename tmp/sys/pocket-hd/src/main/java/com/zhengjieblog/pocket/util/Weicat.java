package com.zhengjieblog.pocket.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author 郑杰
 * @date 2018-7-16
 */
@Data
@Component
@ConfigurationProperties(prefix = "weixin")
public class Weicat {

    private String url;

    private String appid;

    private String secret;

    public String getOpenid(String code){
        RestTemplate restTemplate = new RestTemplate();
        String uri= url + "?appid=" + appid + "&secret=" + secret + "&js_code=" + code +"&grant_type=authorization_code";
        String result = restTemplate.getForObject( uri,String.class);
        JSONObject object = JSON.parseObject(result);
        try {
            String openid = (String) object.get("openid");
            return openid;
        }catch (Exception e){
            return null;
        }
    }
}
