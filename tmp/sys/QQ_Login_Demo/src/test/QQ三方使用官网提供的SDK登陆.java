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

import com.qq.connect.QQConnectException;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.http.HttpClient;
import com.qq.connect.utils.http.PostParameter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * qq使用原生http发送登陆请求
 *
 * @author kiwipeach [1099501218@qq.com]
 * @create 2018/09/08
 */
public class QQ三方使用官网提供的SDK登陆 {
    private Logger logger = LoggerFactory.getLogger(QQ三方使用官网提供的SDK登陆.class);
    private String authorizeURL;
    private String app_ID;
    private String app_KEY;
    private String redirect_uri;
    private String response_type = "code";
    //private String state =RandomStatusGenerator.getUniqueState();
    private String state = "--kiwipeach--";
    private String scope = "get_user_info";
    private String qqCallBackInfo;


    /**
     * Step0: 初始化QQ登陆时候需要使用的配置信息
     */
    @Before
    public void 初始化QQ基础配置信息() {
        authorizeURL = QQConnectConfig.getValue("authorizeURL").trim();
        app_ID = QQConnectConfig.getValue("app_ID").trim();
        app_KEY = QQConnectConfig.getValue("app_KEY").trim();
        redirect_uri = QQConnectConfig.getValue("redirect_URI");
        StringBuffer resultUrl = new StringBuffer(authorizeURL);
        resultUrl.append("?client_id=").append(app_ID);
        resultUrl.append("&redirect_uri=").append(redirect_uri);
        resultUrl.append("&response_type=").append(response_type);
        resultUrl.append("&state=").append(state);
        resultUrl.append("&scope=").append(scope);
        logger.info("QQ登陆地址:{}", resultUrl.toString());
        /**
         * 最终应该得到:
         * https://graph.qq.com/oauth2.0/authorize?client_id=101465199&redirect_uri=http://localhost:8080/afterlogin.do&response_type=code&state=cc86ad8159bf700ef45570777dc038ab&scope=get_user_info
         */

    }

    /**
     * Step1: 获取认证码，腾讯授权服务器在回调地址中会携带有一个授权码和随机state来做安全验证
     * http://localhost:8080/afterlogin.do?code=9A5F************************06AF&state=test
     */
    @Test
    public void 获取AuthorizationCode() {
        /**
         * 此步骤需要在Debug环境中获取
         */
        qqCallBackInfo = "http://localhost:8080/afterlogin.do?code=9A5F************************06AF&state=test";
    }

    /**
     * https://graph.qq.com/oauth2.0/token/oauth2.0/token POST
     * Content-Type: application/x-www-form-urlencoded
     * client_id=xxxx&client_secret=yyyyyyy&grant_type=authorization_code&code=5A568FBE8CCCFB694A4D7E993C10136D&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fafterlogin.do"
     */
    @Test
    public void 获取AccessToken操作() throws QQConnectException {
        HttpClient client = new HttpClient();
        String returnAuthCode = "23CB1CAC8591391FD89FFEE751E15E14";//授权码
        PostParameter[] postParameters1 = {
                new PostParameter("client_id", app_ID),
                new PostParameter("client_secret", app_KEY),
                new PostParameter("grant_type", "authorization_code"),
                new PostParameter("code", returnAuthCode),
                new PostParameter("redirect_uri",redirect_uri)};
        String url = "https://graph.qq.com/oauth2.0/token";
        AccessToken accessToken = new AccessToken(client.post(url, postParameters1, false));
        String accessTokenCode = accessToken.getAccessToken();
        logger.info("访问授权码：{}", accessTokenCode);
        //"access_token=D76354B5C94686470DFBC02DE9CB6913&expires_in=7776000&refresh_token=501954626EB250E3242B7C5DC2ED3F36"
    }

    @Test
    public void 权限自动续期_获取Access_Token() throws QQConnectException {
        HttpClient client = new HttpClient();
        String url = "https://graph.qq.com/oauth2.0/token";
        /**
         * grant_type:refresh_token
         * client_id:101495727
         * client_secret:6d4d93e59731ef57deae952ccec2774a
         * refresh_token:501954626EB250E3242B7C5DC2ED3F36
         */
        PostParameter[] postParameters1 = {
                new PostParameter("grant_type", "refresh_token"),
                new PostParameter("client_id", app_ID),
                new PostParameter("client_secret", app_KEY),
                new PostParameter("refresh_token","501954626EB250E3242B7C5DC2ED3F36")};
        client.get(url, postParameters1);
        // "access_token=21D18D98EF633C34C2EE9CD7A082206E&expires_in=7776000&refresh_token=501954626EB250E3242B7C5DC2ED3F36"
    }

    @Test
    public void 获取OpenID() throws QQConnectException {
        HttpClient client = new HttpClient();
        String url = "https://graph.qq.com/oauth2.0/me";
        PostParameter[] postParameters1 = {
                new PostParameter("access_token","21D18D98EF633C34C2EE9CD7A082206E")};
        client.get(url, postParameters1);
        // "callback( {"client_id":"101495727","openid":"6FF96B97CF726B2D1DD31798135782FA"} );[\n]"
    }

    @Test
    public void 测试OpenAPI接口调用() throws QQConnectException {
        /**
         * access_token:D76354B5C94686470DFBC02DE9CB6913
         * oauth_consumer_key:101495727
         * openid:6FF96B97CF726B2D1DD31798135782FA
         */
        HttpClient client = new HttpClient();
        String url = "https://graph.qq.com/user/get_user_info";
        PostParameter[] postParameters1 = {
                new PostParameter("access_token","21D18D98EF633C34C2EE9CD7A082206E"),
                new PostParameter("oauth_consumer_key",app_ID),
                new PostParameter("openid","6FF96B97CF726B2D1DD31798135782FA")
        };
        client.get(url, postParameters1);
        //返回用户信息
    }
}
