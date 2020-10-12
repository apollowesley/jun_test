package com.team.blogs.common.config;

import org.springframework.stereotype.Component;
import struqt.util.UniqueIdGenerator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.UUID;

/**
 * 雪花算法生成
 */
@Component
public class Generator {

    public static String getOutNo(String merchantNo, String channl){
        return new StringBuilder(merchantNo).insert(0,channl).toString();
    }

    public static String snowflakeWith3Zero(){
        UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator(new Random().nextLong());
        return new StringBuilder(String.valueOf(uniqueIdGenerator.next())).insert(11,"000").toString();
    }

    public static String snowflakeWith1ZeroAnd1Char(char name){
        UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator(new Random().nextLong());
        return new StringBuilder(String.valueOf(uniqueIdGenerator.next())).insert(11,"0").insert(0,name).toString();
    }

    public static String snowflakeWith2Zero(){
        UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator(new Random().nextLong());
        return new StringBuilder(String.valueOf(uniqueIdGenerator.next())).insert(11,"00").toString();
    }

    public static String snowflakeWith1ZeroAndQ(){
        return snowflakeWith1ZeroAnd1Char('Q');
    }

    public static String snowflakeWith1RandomChar(){
        UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator(new Random().nextLong());
        return randomCharInsertion(new StringBuilder(String.valueOf(uniqueIdGenerator.next())));
    }

    public static String uuidReplaceLowerCase(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }

    public static String uuidReplaceUpperCase(){
        return uuidReplaceLowerCase().toLowerCase();
    }

    public static String randomCharInsertion(StringBuilder s){
        char[] chars = getRandomChar();
        s.insert(new Random().nextInt(s.length()),chars[new Random().nextInt(chars.length)]);
        return s.toString().toUpperCase();
    }

    public static char[] getRandomChar(){
        return new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    }

    private static String getUrl(String pafAppId, String platformMerchantId, String returnUrl) {

        String wxOpenIdOauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize";

        String pafReturnUrl = "https://mzone.yqb.com/mzone-http/api/get_wx_user_id";

        try {
            pafReturnUrl = String.format("%s?appId=%s&platformMerchantId=%s&returnUrl=%s", pafReturnUrl, pafAppId, platformMerchantId, URLEncoder.encode(returnUrl, "utf-8"));
            String url = String.format("%s?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect", wxOpenIdOauthUrl, pafAppId, URLEncoder.encode(pafReturnUrl, "utf-8"));
            return url;
        } catch (UnsupportedEncodingException e) {
            System.err.println(e);
        }
        return "";
    }

    public static String getOpenIdUrl(String info){
        String pafAppId = "wxfdfb765ffcb257fc";
        String platformMerchantId = "900000963754";
        String returnUrl ="https://subscribe.heyoufu.com/callback/c01/wx_openid_results/"+info+"/";
        return getUrl(pafAppId, platformMerchantId, returnUrl);
    }

    public static String getBuyerIdUrl(String info){
        String returnUrl = "https://subscribe.heyoufu.com/callback/a01/ali_openid/"+info;
        return "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2019021663207969&scope=auth_base&redirect_uri=" + returnUrl;
    }
}
