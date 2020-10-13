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

package net.oschina.durcframework.easymybatis.support;

import java.util.List;

import net.oschina.durcframework.easymybatis.PageSupport;

/**
 * 支持easyui表格的json结果<br>
 * <code>{"total":28,"rows":[{...},{...}]}</code>
 * @author tanghc
 *
 * @param <Entity>
 */
public class PageEasyui<Entity> extends PageSupport<Entity> {

	public long getTotal() {
		return this.fatchTotal();
	}
	
	public List<Entity> getRows() {
		return this.fatchList();
	}
}
