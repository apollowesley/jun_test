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
package com.baomidou.kisso.apiserver.vo;

/**
 * 应用编码
 */
public enum AppCode {
	OK("200", "ok"), // 正确请求
	INVALID_REQUEST("40012", "invalid request"), // 非法请求
	INVALID_APPID("40013", "invalid appid"); // AppID无效错误

	/** 主键 */
	private final String code;

	/** 描述 */
	private final String desc;

	AppCode(final String code, final String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return this.code;
	}

	public String getDesc() {
		return this.desc;
	}

}
