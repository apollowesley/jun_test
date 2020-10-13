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

package net.oschina.durcframework.easymybatis.util;

import java.util.Collections;
import java.util.List;

import net.oschina.durcframework.easymybatis.PageInfo;
import net.oschina.durcframework.easymybatis.PageResult;
import net.oschina.durcframework.easymybatis.dao.SchDao;
import net.oschina.durcframework.easymybatis.query.Query;
import net.oschina.durcframework.easymybatis.query.param.BaseParam;

/**
 * 查询工具
 * @author tanghc
 *
 */
public class QueryUtils {
	/**
	 * 分页数算法:页数 = (总记录数  +  每页记录数  - 1) / 每页记录数
	 * @param total
	 * @param pageSize
	 * @return
	 */
	private static int calcPageCount(long total,int pageSize) {
		return (int)(pageSize == 0 ? 1 : (total  +  pageSize - 1) / pageSize);
	}
	
	/**
	 * 分页查询
	 * @param schDao 查询dao
	 * @param bean 查询bean
	 * @return 返回PageInfo
	 */
	@SuppressWarnings("unchecked")
	public static <Entity> PageInfo<Entity> query(SchDao<Entity> schDao,Object bean) {
		return query(schDao,Query.buildFromBean(bean), PageInfo.class);
	}
	
	/**
	 * 分页查询
	 * @param schDao 查询dao
	 * @param searchParam 查询pojo
	 * @return 返回PageInfo
	 */
	@SuppressWarnings("unchecked")
	public static <Entity> PageInfo<Entity> query(SchDao<Entity> schDao,BaseParam searchParam) {
		return query(schDao,Query.build(searchParam), PageInfo.class);
	}
	
	/**
	 * 分页查询
	 * @param schDao 查询dao
	 * @param query 查询条件
	 * @return 返回PageInfo
	 */
	@SuppressWarnings("unchecked")
	public static <Entity> PageInfo<Entity> query(SchDao<Entity> schDao,Query query) {
		return query(schDao,query, PageInfo.class);
	}
	

	/**
	 * 分页查询
	 * @param schDao 查询dao
	 * @param query 查询条件
	 * @param pageResultClass 结果类
	 * @return 返回结果类
	 */
	public static <Entity,T extends PageResult<Entity>> T query(SchDao<Entity> schDao, Query query,Class<T> pageResultClass) {
		T result = null;
		try {
			result = pageResultClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		try{
			long total = 0; // 总条数
			int start = query.getStart();
			int pageSize = query.getLimit(); // 每页记录数
			int pageIndex = (start / pageSize) + 1; // 当前第几页
			
			List<Entity> list = Collections.emptyList();
			
			// 如果是查询全部则直接返回结果集条数
			// 如果是分页查询则还需要带入条件执行一下sql
			if(query.getIsQueryAll()) {
				list = schDao.find(query);
				total = list.size();
			}else {
				total = schDao.countTotal(query);
				if(total > 0) {
					list = schDao.find(query);
				}
			}
			
			result.setList(list);
			result.setTotal(total);
			result.setStart(start);
			result.setPageIndex(pageIndex);
			result.setPageSize(pageSize);
			
			int pageCount = calcPageCount(total, pageSize);
			
			result.setPageCount(pageCount);
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		return result;
	}
}
