/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.kisso.apiclient;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.ApiToken;
import com.baomidou.kisso.apiserver.TestUser;
import com.baomidou.kisso.apiserver.vo.AppCode;
import com.baomidou.kisso.apiserver.vo.AppMsg;
import com.baomidou.kisso.common.encrypt.AESMsgCrypt;
import com.baomidou.kisso.common.util.RandomUtil;

/**
 * 模拟 APP 客户端请求
 */
public class AppClient {

	private static final String SERVER_URL = "http://localhost:8080";
	private static final String APP_ID = "10_1002003";// APP_ID 的定义参考
														// access_token 获取接口注释信息

	/**
	 * 
	 * 执行前 8080 端口启动 apiserver 服务
	 * 
	 * 这里 kisso 配置只用到了密钥 accessToken 生成时使用
	 * 
	 * <p>
	 * 拦截器，时间戳校验等，需要您自己去实现，这里只给你提供基础验证方式。
	 * </p>
	 * 
	 * <p>
	 * 这里采用【微信公众 API 验证机制】出于 2 点考虑：1、该方式很安全 2、它已经实现了大部分语言客户端代码
	 * </p>
	 * 
	 */
	public static void main(String[] args) {
		ApiToken apiToken = getApiToken();
		if (apiToken != null) {
			System.out.println("\n access_token: " + apiToken.getAccessToken());
			TestUser user = getTestUserById(apiToken);
			if (user != null) {
				System.err.println("\n 测试API userName: " + user.getUserName());
			}
		}
	}

	/**
	 * 
	 * 获取访问权限 ApiToken
	 * 
	 */
	public static ApiToken getApiToken() {
		String accessTokenUrl = SERVER_URL + "/token/access_token?grant_type=app_credential";
		try {
			Map<String, String> data = new HashMap<String, String>();
			data.put("appid", APP_ID);
			data.put("username", "test");
			data.put("password", "123");
			String body = HttpUtil.post(accessTokenUrl, data).body();
			System.err.println("\n getApiToken json:" + body);
			AppMsg appMsg = JSON.parseObject(body, AppMsg.class);
			if (appMsg != null && AppCode.OK.getCode().equals(appMsg.getCode())) {
				return JSON.parseObject(appMsg.getData(), ApiToken.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 查询指定 userId 的用户信息
	 * 
	 */
	public static TestUser getTestUserById(ApiToken apiToken) {
		try {
			String getuserUrl = SERVER_URL + "/api/user_info?accessToken=" + apiToken.getAccessToken();
			AESMsgCrypt msgCrypt = new AESMsgCrypt(apiToken.getToken(), apiToken.getAesKey(), APP_ID);
			String timeStamp = Long.toString(System.currentTimeMillis());
			String nonce = RandomUtil.getCharacterAndNumber(8);
			/*
			 * 
			 * 这里是一个需要签名加密传输的数据（用户ID）
			 * 
			 * <p> 这里演示 json 校验，加密内容可以是任何字符，JSON 格式等。 </p> <p> 后台 xml 形式校验
			 * msgCrypt.encryptXMLMsg(replyMsg, timeStamp, nonce) </p>
			 */
			String encryptData = msgCrypt.encryptJSONMsg("10001", timeStamp, nonce);

			// 传输参数
			Map<String, String> data = new HashMap<String, String>();
			data.put("data", encryptData);
			String body = HttpUtil.get(getuserUrl, data).body();
			System.err.println("\n getTestUserById json:" + body);
			return JSON.parseObject(body, TestUser.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
