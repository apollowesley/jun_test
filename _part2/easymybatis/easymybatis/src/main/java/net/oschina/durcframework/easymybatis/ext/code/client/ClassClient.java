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

import java.io.FileNotFoundException;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import net.oschina.durcframework.easymybatis.EasymybatisConfig;

public class ClassClient {

	private static Log logger = LogFactory.getLog(ClassClient.class);

	private Generator generator = new Generator();
	
	private EasymybatisConfig config;

	public ClassClient(EasymybatisConfig config) {
		super();
		if(config == null) {
			throw new IllegalArgumentException("config不能为null");
		}
		this.config = config;
	}

	/**
	 * 生成mybatis文件
	 * 
	 * @param mapperClass
	 * @param dbType
	 * @return
	 */
	public String genMybatisXml(Class<?> mapperClass, String templateLocation,String globalVmLocation) {
		if (logger.isDebugEnabled()) {
			logger.debug("开始生成" + mapperClass.getName() + "对应的Mapper");
		}

		ClientParam param = new ClientParam();
		param.setTemplateLocation(templateLocation);
		param.setGlobalVmLocation(globalVmLocation);
		param.setMapperClass(mapperClass);
		param.setConfig(config);

		try {
			return generator.generateCode(param);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
