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

package net.oschina.durcframework.easymybatis.query.param;

import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.annotation.Condition;

/**
 * 分页查询参数
 * 
 * @author tanghc
 *
 */
public class PageParam extends BaseParam implements SchPageableParam {

	// 当前第几页
	private int pageIndex = 1;
	// 每页记录数
	private int pageSize = 20;

	@Override
	public Query toQuery() {
		return super.toQuery().addPaginationInfo(this);
	}

	@Condition(ignore = true)
	@Override
	public int getStart() {
		return (int) ((this.getPageIndex() - 1) * this.getPageSize());
	}

	@Condition(ignore = true)
	@Override
	public int getLimit() {
		return this.getPageSize();
	}

	@Condition(ignore = true)
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	@Condition(ignore = true)
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
