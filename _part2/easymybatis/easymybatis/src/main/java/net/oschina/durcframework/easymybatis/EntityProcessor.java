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

package net.oschina.durcframework.easymybatis;

import java.util.Map;

/**
 * 记录处理
 * 
 * @param <Entity>
 */
public interface EntityProcessor<Entity> {
	/**
	 * 处理记录
	 * @param entity 记录对象
	 * @param entityMap 记录对象中的属性名/值所对应的map
	 */
	void process(Entity entity, Map<String,Object> entityMap);
}
