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

import com.alibaba.fastjson.JSON;

/**
 * 错误消息
 */
public class AppMsg {
	private String code;
	private String data;
	private String msg;
	
	public AppMsg() {
		
	}

	public AppMsg(AppCode appCode) {
		this.code = appCode.getCode();
		this.msg = appCode.getDesc();
	}
	
	public AppMsg(AppCode appCode, String data) {
		this(appCode);
		this.data = data;
	}

	public static String toJSONString(AppCode appCode) {
		return JSON.toJSONString(new AppMsg(appCode));
	}
	
	public static String toJSONString(AppCode appCode, String data) {
		return JSON.toJSONString(new AppMsg(appCode, data));
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
