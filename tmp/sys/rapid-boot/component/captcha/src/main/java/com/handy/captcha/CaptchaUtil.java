package com.handy.captcha;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author handy
 * @Description: {阿里云验证码服务}
 * @date 2019/8/25 13:47
 */
@Repository("CaptchaUtil")
public class CaptchaUtil {
    @Value("${captcha.accessKeyId}")
    private String accessKeyId;
    @Value("${captcha.accessSecret}")
    private String accessSecret;

    /**
     * 发送短信验证码
     *
     * @param mobile                    手机号
     * @param code                      验证码
     * @param verificationCodeParamEnum 类型
     * @return
     */
    public CaptchaResponse getCaptcha(String mobile, String code, CaptchaEnum verificationCodeParamEnum) {
        val param = new CaptchaParam();
        param.setPhoneNumbers(mobile);
        param.setSignName(verificationCodeParamEnum.getSignName());
        param.setTemplateCode(verificationCodeParamEnum.getTemplateCode());
        param.setTemplateParam(code);
        return sendSms(param);
    }

    /**
     * 发送验证码
     *
     * @param param
     * @return
     */
    private CaptchaResponse sendSms(CaptchaParam param) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", param.getPhoneNumbers());
        request.putQueryParameter("SignName", param.getSignName());
        request.putQueryParameter("TemplateCode", param.getTemplateCode());
        request.putQueryParameter("TemplateParam", param.getTemplateParam());
        try {
            CommonResponse response = client.getCommonResponse(request);
            return JSONUtil.toBean(response.getData(), CaptchaResponse.class);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
