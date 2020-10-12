package com.sise.school.teach.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author idea
 * @data 2018/11/29
 */
@Slf4j
public class SmsUtil {

    /**
     * 智能匹配模板发送接口的http地址
     */
    private static String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";

    /**
     * 编码格式。发送编码格式统一用UTF-8
     */
    private static String ENCODING = "UTF-8";

    public static void main(String[] args) throws IOException,
            URISyntaxException {

        //修改为您的apikey.apikey可在官网（http://www.yunpian.com)登录后获取
        String apikey = "9ee094d0d451ed6e3a1ea5770757f184";

        //修改为您要发送的手机号
        String mobile = "15889793032";

        //模板发送的调用示例
        System.out.println(SmsUtil.sendSms(apikey, "【仓管云】您的验证码是1222", mobile));
    }


    /**
     * 智能匹配模板接口发短信
     *
     * @param apikey apikey
     * @param text   　短信内容
     * @param mobile 　接受的手机号
     * @return json格式字符串
     */

    public static String sendSms(String apikey, String text, String mobile) {
        Map<String, String> params = new HashMap<>(16);
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        String result=post(URI_SEND_SMS, params);
        log.info(result);
        return result;
    }



    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */

    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(),
                            param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList,
                        ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity, ENCODING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }

}
