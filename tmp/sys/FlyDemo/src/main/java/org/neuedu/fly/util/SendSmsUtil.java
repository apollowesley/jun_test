package org.neuedu.fly.util;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.ResourceBundle;

public class SendSmsUtil {

    private static ResourceBundle rb = ResourceBundle.getBundle("sms");

    public static CommonResponse sendSms(String tel,String code){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",rb.getString("accessKeyID"), rb.getString("accessKeySecret"));
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", tel);  //手机号
        request.putQueryParameter("SignName", "东软社区");
        request.putQueryParameter("TemplateCode", rb.getString("templateCode"));  //模板  验证码    推销    做活动
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");   //验证码
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

}