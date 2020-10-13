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

package net.oschina.durcframework.easymybatis.handler;

/**
 * 字段枚举类型父类.
 * 如果javaBean字段要实现枚举类型，枚举类必须实现BaseEnum。如：
 * <pre>
 * {@literal
 * public enum UserInfoType implements BaseEnum&lt;String&gt;{
	INVALID("0"),VALID("1")
	;

	private String status;

	UserInfoType(String type) {
		this.status = type;
	}
	
	
	@Override
	public String getCode() {
		return status;
	}

}
 * }
 * </pre>
 * 
 * 
 * @author tanghc
 *
 * @param <T> 枚举值类型，通常是Integer或String
 */
public interface BaseEnum<T> {
	T getCode();
}