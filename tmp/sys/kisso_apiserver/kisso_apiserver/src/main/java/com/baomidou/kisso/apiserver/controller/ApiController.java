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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.kisso.ApiToken;
import com.baomidou.kisso.apiserver.TestCache;
import com.baomidou.kisso.apiserver.TestUser;
import com.baomidou.kisso.apiserver.vo.CacheToken;
import com.baomidou.kisso.common.encrypt.AESMsgCrypt;
import com.baomidou.kisso.common.parser.api.EncryptMsg;
import com.baomidou.kisso.common.parser.api.JSONParser;
import com.baomidou.kisso.exception.AESException;

/**
 * 测试 用户 restful api
 */
@Controller
@RequestMapping("/api")
public class ApiController extends BaseController {

	private static Map<Long, TestUser> userDao = new HashMap<Long, TestUser>();

	static {
		// 模拟数据库初始化数据
		userDao.put(10001L, new TestUser(10001L, "test", "123"));
	}

	/**
	 * 
	 * 查询指定 userId 的用户信息
	 * 
	 * <p>
	 * 这里只写一个演示例子其他同理，API 签名加密解析机制看 kisso 源码测试类
	 * com.baomidou.kisso.TestAESMsgCrypt.java
	 * </p>
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user_info", method = RequestMethod.GET)
	public String userInfo() {
		String accessToken = request.getParameter("accessToken");
		String data = request.getParameter("data");
		if (StringUtils.isNotBlank(accessToken) && StringUtils.isNotBlank(data)) {
			/*
			 * 解密得到明文 accessToken 从缓存中取出，申请时候放入的缓存 Token 信息 <p> 这里可以验证
			 * accessToken 有效性，根据解密结果及缓存结果 </p>
			 */
			String accessTokenStr = ApiToken.decryptAccessToken(accessToken);
			System.err.println("\n ApiController 获取缓存票据主键：accessToken=" + accessTokenStr);
			CacheToken cacheToken = TestCache.get(accessTokenStr);
			if (cacheToken != null) {
				try {
					AESMsgCrypt crypt = new AESMsgCrypt(cacheToken.getToken(), cacheToken.getAesKey(),
							cacheToken.getAppid());
					
					// 这里演示 JSON 解析
					EncryptMsg msg = JSONParser.extract(data);
					String userId = crypt.decryptMsg(msg.getMsgSignature(), msg.getTimeStamp(), msg.getNonce(),
							msg.getEncrypt());
					return JSON.toJSONString(userDao.get(Long.valueOf(userId)));
				} catch (AESException e) {
					e.printStackTrace();
				}
			}
		}
		return "none";
	}

}
