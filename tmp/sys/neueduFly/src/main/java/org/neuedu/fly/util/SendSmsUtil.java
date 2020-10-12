package org.neuedu.fly.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/10/22-14:18
 **/
public class SendSmsUtil {

    private static ResourceBundle rb = ResourceBundle.getBundle("sms");

    public static String sendSms(String tel,String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", rb.getString("accessKeyId"), rb.getString("secret"));
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", tel);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        request.putQueryParameter("SignName", rb.getString("signName"));
        request.putQueryParameter("TemplateCode",  rb.getString("templateCode"));
        try {
            CommonResponse response = client.getCommonResponse(request);
           // System.out.println(response.getData());
            return response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        //String code = String.valueOf(new Double(Math.random()*1000).intValue());
        //String str = SendSmsUtil.sendSms("", code);
        //System.out.println(str);

        String jsonStr = "{\"Message\":\"模板不合法(不存在或被拉黑)\",\"RequestId\":\"10DDECF9-FFF6-4DFB-888A-1EF44FA6FC28\",\"Code\":\"isv.SMS_TEMPLATE_ILLEGAL\"}";
        //需要把JSON字符串转为Map集合
        Map<String,String> map = GsonUtil.fromJson(jsonStr, Map.class);
        System.out.println(map.get("Message"));

    }

}
