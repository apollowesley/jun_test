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

package net.oschina.durcframework.easymybatis.ext.code.client;

import net.oschina.durcframework.easymybatis.EasymybatisConfig;
import net.oschina.durcframework.easymybatis.util.ClassUtil;

public class ClientParam {
	private Class<?> mapperClass;
	private String templateLocation; // 模板文件路径
	private String globalVmLocation;
	private EasymybatisConfig config;

	public Class<?> getEntityClass() {
		if (mapperClass.isInterface()) {
			return ClassUtil.getSuperInterfaceGenricType(mapperClass, 0);
		} else {
			return ClassUtil.getSuperClassGenricType(mapperClass, 0);
		}
	}
	
	public String getGlobalVmPlaceholder() {
		return this.config.getGlobalVmPlaceholder();
	}

	public String getGlobalVmLocation() {
		return globalVmLocation;
	}

	public void setGlobalVmLocation(String globalVmLocation) {
		this.globalVmLocation = globalVmLocation;
	}

	public Class<?> getMapperClass() {
		return mapperClass;
	}

	public void setMapperClass(Class<?> mapperClass) {
		this.mapperClass = mapperClass;
	}

	public String getTemplateLocation() {
		return templateLocation;
	}

	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}

	public EasymybatisConfig getConfig() {
		return config;
	}

	public void setConfig(EasymybatisConfig config) {
		this.config = config;
	}

	public String getCountExpression() {
		return this.config.getCountExpression();
	}

}
