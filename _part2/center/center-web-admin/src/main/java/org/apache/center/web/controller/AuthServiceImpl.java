/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
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
package org.apache.center.web.controller;

import java.util.List;

import org.apache.center.model.Resources;
import org.apache.center.service.ResourcesService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.baomidou.framework.spring.SpringHelper;
import com.baomidou.kisso.SSOAuthorization;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.Token;

/**
 * 
 * 系统权限授权实现类
 *
 */
public class AuthServiceImpl implements SSOAuthorization {

	private static ResourcesService resourcesService; //资源服务层

	static {
		resourcesService = SpringHelper.getBean(ResourcesService.class);
	}

	/**
	 * 
	 * 开启配置 sso.permission.uri=true 支持、先验证 url 地址，后验证注解。
	 * 
	 */
	public boolean isPermitted( Token token, String permission ) {
		/**
		 * 循环判断权限编码是否合法，token 获取登录用户ID信息、判断相应权限也可作为缓存主键使用。
		 */
		SSOToken ssoToken = (SSOToken)token;
		String userName = ssoToken.getData();
		List<Resources> resourcesList = resourcesService.selectByUserId(userName, NumberUtils.toLong(String.valueOf(token.getId())));
		if (CollectionUtils.isNotEmpty(resourcesList)) {
			for (Resources resources : resourcesList) {
				if (StringUtils.equals(resources.getAuthCode(), permission)) {
					return true;
				}
			}
		}
		return false;
	}

}
