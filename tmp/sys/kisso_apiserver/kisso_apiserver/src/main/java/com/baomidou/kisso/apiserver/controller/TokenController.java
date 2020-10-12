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
package com.baomidou.kisso.apiserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.ApiToken;
import com.baomidou.kisso.apiserver.TestCache;
import com.baomidou.kisso.apiserver.vo.AppCode;
import com.baomidou.kisso.apiserver.vo.AppMsg;
import com.baomidou.kisso.apiserver.vo.CacheToken;
import com.baomidou.kisso.common.util.RandomUtil;

/**
 * 票据颁发
 */
@Controller
@RequestMapping("/token")
public class TokenController extends BaseController {

	/**
	 * 
	 * 访问票据
	 * 
	 * <p>
	 * 返回数据： {"code":"200","data":
	 * "{\"accessToken\":\"10001#25925h87E7\",\"aesKey\":\"66cc3c21bfd64a3ca7473aed8c62cea4\",\"token\":\"25925h87E7\"}"
	 * ,"msg":"ok"}
	 * </p>
	 * 
	 * 注意！！ 线上使用 post 请求。。
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/access_token", method = RequestMethod.POST)
	public String access_token() {
		AppCode appCode = AppCode.INVALID_REQUEST;// 非法请求
		// 认证类型 app_credential 移动端 ，client_credential 客户端（PC）
		String grant_type = request.getParameter("grant_type");
		if ("app_credential".equals(grant_type)) {
			String appid = request.getParameter("appid");
			/*
			 * 应用AppId
			 * 
			 * <p> AppId 这里测试固定值 test ， 生产环境是一个唯一应用ID ，可作为统计使用 AppId 定义【
			 * 客户端标识_机器唯一编号 】移动端自动生成
			 * 
			 * 例如： 10_1002003 【10 表示 ANDROD 手机端 1002003 表示机器唯一编码】 例如： 20_1002003
			 * 【20 表示 IOS 手机端 1002003 表示机器唯一编码】 </p>
			 * 
			 */
			if ("10_1002003".equals(appid)) {
				String username = request.getParameter("username");// 用户名
				String password = request.getParameter("password");// 用户名
				if ("test".equals(username) && "123".equals(password)) {
					// 用户ID 根据登录从数据库中获取
					String uid = "10001";
					/*
					 * 此处 token 采用随机 10位字符 访问票据 accessToken = ui#token 需要缓存，例如
					 * 10 分钟即该访问票据有效时间 10 分钟，失效需要重新申请。
					 */
					String token = RandomUtil.getCharacterAndNumber(10);
					String accessToken = uid + "#" + token;
					System.err.println("缓存票据主键：accessToken=" + accessToken);
					
					// 生成 ApiToken
					ApiToken apiToken = new ApiToken(token, accessToken);

					// 缓存对象 CacheToken 下一次从缓存中取出该对象直接使用
					CacheToken cacheToken = new CacheToken(appid, uid, token, apiToken.getAesKey());
					System.err.println("缓存票据内容：cacheToken=" + JSON.toJSONString(cacheToken) + "\n");
					
					//设置缓存
					TestCache.set(accessToken, cacheToken, 0);

					return AppMsg.toJSONString(AppCode.OK, JSON.toJSONString(apiToken));
				}
			} else {
				// 非法 AppId
				appCode = AppCode.INVALID_APPID;
			}
		}
		return AppMsg.toJSONString(appCode);
	}

}
