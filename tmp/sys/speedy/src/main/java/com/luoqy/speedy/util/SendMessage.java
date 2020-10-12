package com.luoqy.speedy.util;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.http.HTTPException;

import org.json.JSONException;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
/**
 *  短信发送
 * */
public class SendMessage {
    /**
     *  腾讯发送短信
     * */
    public static SmsSingleSenderResult sendTx(String phone, HttpServletRequest req){
        //腾讯短信应用的 SDK AppID
        int appid =1400238491;
        String appkey = "e13f1eec44b7203fff26dcffd1c3163f";//腾讯云短信中的 App Key
        int templateId =383941;// NOTE: 这里是短信模板ID需要在短信控制台中申请
        String smsSign = "";//设置信息标头，如【腾讯云】
        //验证码
        String str = "";
        try {
            //随机生成6位的验证码
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
                str += random.nextInt(10);
            }
            String[] params = {str};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信，这里的13800138000是为用户输入的手机号码
            SmsSingleSenderResult result = ssender.sendWithParam("86", phone, templateId, params, smsSign, "", "");
            System.out.println(result.errMsg);
            if("OK".equals(result.errMsg)){
                req.getSession(true).setAttribute("code",str);
            }
            return result;
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }catch (Exception e) {
			// TODO: handle exception
		}
        return null;

    }
    /**
     * 批量发送短信
     */
    public static void SendBatchSms() {
    	 DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "<accessKeyId>", "<accessSecret>");
         IAcsClient client = new DefaultAcsClient(profile);

         CommonRequest request = new CommonRequest();
         request.setMethod(MethodType.POST);
         request.setDomain("dysmsapi.aliyuncs.com");
         request.setVersion("2017-05-25");
         request.setAction("SendBatchSms");
         request.putQueryParameter("RegionId", "cn-hangzhou");
         request.putQueryParameter("PhoneNumberJson", "1");
         request.putQueryParameter("SignNameJson", "1");
         request.putQueryParameter("TemplateCode", "1");
         try {
             CommonResponse response = client.getCommonResponse(request);
             System.out.println(response.getData());
         } catch (ServerException e) {
             e.printStackTrace();
         } catch (ClientException e) {
             e.printStackTrace();
         }
    }
    /**
     * 阿里云单挑条发送短信
     * */
    public static void sendAli(String phone, HttpServletRequest req){
        //随机生成6位的验证码
        int appid =1400238491;
        String appkey = "e13f1eec44b7203fff26dcffd1c3163f";//腾讯云短信中的 App Key
        int templateId =383941;// NOTE: 这里是短信模板ID需要在短信控制台中申请
        String smsSign = "";//设置信息标头，如【腾讯云】
        //验证码
        String str = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str += random.nextInt(10);
        }
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "<accessKeyId>", "<accessSecret>");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "1");
        request.putQueryParameter("TemplateCode", "1");
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
