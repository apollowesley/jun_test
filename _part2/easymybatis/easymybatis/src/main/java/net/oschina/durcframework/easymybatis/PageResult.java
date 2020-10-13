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

import java.io.Serializable;
import java.util.List;

public interface PageResult<Entity> extends Serializable {
	/** 设置结果集 */
	void setList(List<Entity> list);
	/** 设置记录总数 */
	void setTotal(long total);
	/** 设置第一条记录起始位置 */
	void setStart(int start);
	/** 设置当前页索引 */
	void setPageIndex(int pageIndex);
	/** 设置每页记录数 */
	void setPageSize(int pageSize);
	/** 设置总页数 */
	void setPageCount(int pageCount);
}
