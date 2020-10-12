package com.evil.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.evil.pojo.system.Right;
import com.evil.util.Page;
import com.evil.util.PageResult;

/**
 *权限Service
 */
public interface RightService extends BaseService<Right> {

	/**
	 * 根据model是否具有id保存/更新权限
	 */
	void saveOrUpdateRight(Right model);

	/**
	 *如果URL在rights中不存在则添加 
	 */
	void appendRightByUrl(String url);

	/**
	 *批量更新权限 
	 */
	void batchUpdateRights(List<Right> allRights);

	/**
	 *通过id数组和所有的权限集合 
	 */
	List<Right> findRightsInRange(Integer[] ownRightIds);

	/**
	 *查询不属于该角色的权限 
	 */
	List<Right> findRightsNotInRange(Set<Right> set);
	/**
	 * 查询权限列表并分页
	 * @param page  分页信息
	 * @param map   参数条件
	 * @param sortfields 排序方式
	 * @return
	 */
	public PageResult findRightsPage(Page page, Map<String, Object> map,
			String... sortfields);

	/**
	 *获得最大的权限位 
	 */
	int getMaxPos();

}
