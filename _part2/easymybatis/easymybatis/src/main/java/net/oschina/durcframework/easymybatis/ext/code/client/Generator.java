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
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import net.oschina.durcframework.easymybatis.ext.code.generator.SQLContext;
import net.oschina.durcframework.easymybatis.ext.code.generator.TableDefinition;
import net.oschina.durcframework.easymybatis.ext.code.generator.TableSelector;
import net.oschina.durcframework.easymybatis.ext.code.util.VelocityUtil;

/**
 * 代码生成器，根据定义好的velocity模板生成代码
 * @author tanghc
 *
 */
public class Generator {
	
	private static final Charset UTF8 = Charsets.toCharset("UTF-8");
	
	public String generateCode(ClientParam clientParam) throws FileNotFoundException {
		InputStream templateInputStream = this.buildTemplateInputStream(clientParam);
		SQLContext sqlContext = this.buildClientSQLContextList(clientParam);
		VelocityContext context = new VelocityContext();

		TableDefinition tableDefinition = sqlContext.getTableDefinition();
		
		// vm模板中的变量
		context.put("context", sqlContext);
		context.put("table", tableDefinition);
		context.put("pk", tableDefinition.getPkColumn());
		context.put("columns", tableDefinition.getTableColumns());
		context.put("allColumns", tableDefinition.getAllColumns());
		context.put("countExpression", clientParam.getCountExpression());

		return VelocityUtil.generate(context, templateInputStream);
	}
	
	// 返回模板文件内容
	private InputStream buildTemplateInputStream(ClientParam clientParam) throws FileNotFoundException {
		DefaultResourceLoader templateLoader = new DefaultResourceLoader(); // 模板文件
		Resource vmResource = templateLoader.getResource(clientParam.getTemplateLocation());
		
		try {
			if(StringUtils.isNotBlank(clientParam.getGlobalVmLocation())) {
				DefaultResourceLoader globalVmResourceLoader = new DefaultResourceLoader(); // 全局模板文件
				Resource globalVmResource = globalVmResourceLoader.getResource(clientParam.getGlobalVmLocation());
				return this.mergeGlobalVm(vmResource, globalVmResource,clientParam.getGlobalVmPlaceholder());
			}else {
				return vmResource.getInputStream();
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	// 合并全局模板,globalVmResource的内容合并到vmResource中
	private InputStream mergeGlobalVm(Resource vmResource,Resource globalVmResource,String placeholder) throws IOException {
		String vmContent = IOUtils.toString(vmResource.getInputStream(), UTF8);
		String globalVmContent = IOUtils.toString(globalVmResource.getInputStream(), UTF8);
		
		String finalContent = vmContent.replace(placeholder, globalVmContent);
		
		return IOUtils.toInputStream(finalContent, UTF8);
	}
	
	/**
	 * 返回SQL上下文列表
	 * 
	 * @param tableNames
	 * @return
	 */
	private SQLContext buildClientSQLContextList(ClientParam clientParam) {
		Class<?> entityClass = clientParam.getEntityClass();
		TableSelector tableSelector = new TableSelector(entityClass,clientParam.getConfig());

		TableDefinition tableDefinition = tableSelector.getTableDefinition();

		SQLContext context = new SQLContext(tableDefinition);
		
		String namespace = this.buildNamespace(clientParam.getMapperClass());
		context.setClassName(entityClass.getName());
		context.setClassSimpleName(entityClass.getSimpleName());
		context.setPackageName(entityClass.getPackage().getName());
		context.setNamespace(namespace);
		
		return context;
	}
	
	private String buildNamespace(Class<?> mapperClass) {
		return mapperClass.getName();
	}

}
