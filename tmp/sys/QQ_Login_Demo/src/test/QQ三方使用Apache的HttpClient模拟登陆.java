/*
 * Copyright 2018 kiwipeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 前置条件：假设用户正确的输入过了用户密码，此时腾讯认证授权服务器会返回code和state
 *
 * @author kiwipeach [1099501218@qq.com]
 * @create 2018/09/24
 */
public class QQ三方使用Apache的HttpClient模拟登陆 {

    private Logger logger = LoggerFactory.getLogger(QQ三方使用Apache的HttpClient模拟登陆.class);
    //1)qq配置信息
    private String app_ID = "101495727";
    private String app_KEY = "6d4d93e59731ef57deae952ccec2774a";
    private String redirect_URI = "http://www.kiwipeach.cn/qqlogin/callback";
    private String scope = "get_user_info";
    //2)https请求需要访问的url
    private String baseURL = "https://graph.qq.com/";
    private String authorizeURL = "https://graph.qq.com/oauth2.0/authorize";
    private String accessTokenURL = "https://graph.qq.com/oauth2.0/token";
    private String getOpenIDURL = "https://graph.qq.com/oauth2.0/me";
    private String getUserInfoURL = "https://graph.qq.com/user/get_user_info";
    //3)http请求对象
    private CloseableHttpClient httpclient;
    private HttpEntity entity;
    private CloseableHttpResponse response;

    @Before
    public void init() {
        httpclient = HttpClientBuilder.create().build();
    }

    @After
    public void destroy() throws IOException {
        if (response != null) {
            entity = response.getEntity();
            logger.info("响应内容:{}", EntityUtils.toString(entity));
        }
        if (httpclient != null) {
            httpclient.close();
        }
    }

    /**
     * 1）通过Authorization Code获取Access Token
     */
    @Test
    public void 通过Authorization_Code获取Access_Token() throws URISyntaxException, IOException {
        //code:授权码，需要认证成功后才能够获取到该值
        //http://www.kiwipeach.cn/qqlogin/callback?code=02F2BCC543134404B33E2665DCFD1A0C&state=d4213204f14d585c48998f8ba3304253
        String code = "02F2BCC543134404B33E2665DCFD1A0C";
        URI uri = new URIBuilder(accessTokenURL)
                .addParameter("grant_type", "authorization_code")
                .addParameter("client_id", app_ID)
                .addParameter("client_secret", app_KEY)
                .addParameter("code", code)
                .addParameter("redirect_uri", redirect_URI).build();
        HttpGet httpGet = new HttpGet(uri);
        response = httpclient.execute(httpGet);
        /**
         * 返回报文：
         * access_token=21D18D98EF633C34C2EE9CD7A082206E&expires_in=7776000&refresh_token=501954626EB250E3242B7C5DC2ED3F36
         */
    }

    @Test
    public void 权限自动续期() throws URISyntaxException, IOException {
        //refreshToken,上一步骤返回的refresh_token值
        String refreshToken = "501954626EB250E3242B7C5DC2ED3F36";
        URI uri = new URIBuilder(accessTokenURL)
                .addParameter("grant_type", "refresh_token")
                .addParameter("client_id", app_ID)
                .addParameter("client_secret", app_KEY)
                .addParameter("refresh_token", refreshToken)
                .addParameter("redirect_uri", redirect_URI).build();
        HttpGet httpGet = new HttpGet(uri);
        response = httpclient.execute(httpGet);
        /**
         *返回报文：
         *access_token=9B5020394BE3DB13003CD0ACAF3F3DE8&expires_in=7776000&refresh_token=501954626EB250E3242B7C5DC2ED3F36
         */
    }

    @Test
    public void 获取用户OpenID_OAuth2() throws URISyntaxException, IOException {
        //上一步骤的access_token.
        String accessToken = "9B5020394BE3DB13003CD0ACAF3F3DE8";
        URI uri = new URIBuilder(getOpenIDURL)
                .addParameter("access_token", accessToken).build();
        HttpGet httpGet = new HttpGet(uri);
        response = httpclient.execute(httpGet);
        /**
         * 响应报文：callback( {"client_id":"101495727","openid":"6FF96B97CF726B2D1DD31798135782FA"} );
         */
    }

    @Test
    public void OpenAPI调用说明_OAuth2() throws URISyntaxException, IOException {
        String accessToken = "9B5020394BE3DB13003CD0ACAF3F3DE8";
        String openId = "6FF96B97CF726B2D1DD31798135782FA";
        URI uri = new URIBuilder(getUserInfoURL)
                .addParameter("access_token", accessToken)
                .addParameter("oauth_consumer_key", app_ID)
                .addParameter("openid", openId).build();
        HttpGet httpGet = new HttpGet(uri);
        response = httpclient.execute(httpGet);
    }

}
