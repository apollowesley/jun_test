/*
 * Copyright 2017 the original author or authors.
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

package net.oschina.durcframework.easymybatis.ext.code.util;

public class JavaType {
	private String baseType; // 基本类型
	private String boxType; // 装箱类型
	private String mybatisType; // 对应的mybatis类型

	/**
	 * @param baseType 基本类型
	 * @param boxType 装箱类型
	 * @param mybatisType 对应的mybatis类型
	 */
	public JavaType(String baseType, String boxType, String mybatisType) {
		super();
		this.baseType = baseType;
		this.boxType = boxType;
		this.mybatisType = mybatisType;
	}

	public String getBaseType() {
		return baseType;
	}

	public String getBoxType() {
		return boxType;
	}

	public String getMybatisType() {
		return mybatisType;
	}
}