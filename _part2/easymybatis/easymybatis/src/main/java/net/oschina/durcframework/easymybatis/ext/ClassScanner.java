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

package net.oschina.durcframework.easymybatis.ext;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

/**
 * class扫描
 * @author tanghc
 *
 */
public class ClassScanner {
	
	private static final String RESOURCE_PATTERN = "/**/*.class";

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	private List<String> packagesList = new LinkedList<String>();

	private List<TypeFilter> typeIncludes = new LinkedList<TypeFilter>();

	private Set<Class<?>> classSet = new HashSet<Class<?>>();
	
	public ClassScanner(String[] entityPackage,Class<?> scanClass) {
		for (String packagePath : entityPackage) {
			this.packagesList.add(packagePath);
		}
		// scanClass是注解类型
		if(scanClass.isAnnotation()) {
			// AnnotationTypeFilter 有scanClass注解的类将被过滤出来,不能过滤接口
			typeIncludes.add(new AnnotationTypeFilter((Class<? extends Annotation>) scanClass, false));
		}else {
			// AssignableTypeFilter 继承或实现superClass的类将被过滤出来
			// superClass可以是接口
			typeIncludes.add(new AssignableTypeFilter(scanClass));
		}
		
	}
	
	
	
	/**
	 * 将符合条件的Bean以Class集合的形式返回
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Set<Class<?>> getClassSet() throws IOException, ClassNotFoundException {
		this.classSet.clear();
		if (!this.packagesList.isEmpty()) {
			for (String pkg : this.packagesList) {
				// classpath*:com/xx/dao/**/*.class
				String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
						+ ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
				Resource[] resources = this.resourcePatternResolver.getResources(pattern);
				MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
				for (Resource resource : resources) {
					if (resource.isReadable()) {
						MetadataReader reader = readerFactory.getMetadataReader(resource);
						if (matchesEntityTypeFilter(reader, readerFactory)) {
							String className = reader.getClassMetadata().getClassName();
							this.classSet.add(Class.forName(className));
						}
					}
				}
			}
		}
		return this.classSet;
	}

	/**
	 * 检查当前扫描到的Bean含有任何一个指定的注解标记
	 * 
	 * @param reader
	 * @param readerFactory
	 * @return
	 * @throws IOException
	 */
	private boolean matchesEntityTypeFilter(MetadataReader reader, MetadataReaderFactory readerFactory)
			throws IOException {
		if (!this.typeIncludes.isEmpty()) {
			for (TypeFilter filter : this.typeIncludes) {
				if (filter.match(reader, readerFactory)) {
					return true;
				}
			}
		}
		return false;
	}
}
