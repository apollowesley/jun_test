package com.evil.service;

import java.util.List;

import com.evil.pojo.system.TypeDictionary;

public interface TypeDictionaryService extends BaseService<TypeDictionary> {
	
	/**
	 * 查询不同类型的全部数据
	 * @param type
	 * @return
	 */
	public List<TypeDictionary> findDictionaryByType(int type);
	
	/**
	 * 删除试卷的字典数据
	 * @param id
	 */
	public void deleteClassifyByPid(String id);
	/**
	 *获得指定类型的最大类型值 
	 */
	public int getMaxValueByType(int type);

}
