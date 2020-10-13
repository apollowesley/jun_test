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
 * 分页排序查询参数
 * @author tanghc
 *
 */
public class PageSortParam extends BaseParam implements SchPageableParam,SchSortableParam {

	// 当前第几页
	private int pageIndex = 1;
	// 每页记录数
	private int pageSize = 20;

	private String sort;
	private String order;
	
	public Query toQuery() {
		return super.toQuery().addPaginationInfo(this).addSortInfo(this);
	}

	@Condition(ignore = true)
	@Override
	public String getSortname() {
		return sort;
	}

	@Condition(ignore = true)
	@Override
	public String getSortorder() {
		return order;
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

	@Condition(ignore = true)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Condition(ignore = true)
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Condition(ignore = true)
	@Override
	public String getDBSortname() {
		return this.sort;
	}

}
