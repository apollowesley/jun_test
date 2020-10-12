package com.evil.service;

import java.util.List;

import com.evil.pojo.system.TypeDictionary;

public interface TypeDictionaryService extends BaseService<TypeDictionary> {
	
	/**
	 * ��ѯ��ͬ���͵�ȫ������
	 * @param type
	 * @return
	 */
	public List<TypeDictionary> findDictionaryByType(int type);
	
	/**
	 * ɾ���Ծ���ֵ�����
	 * @param id
	 */
	public void deleteClassifyByPid(String id);
	/**
	 *���ָ�����͵��������ֵ 
	 */
	public int getMaxValueByType(int type);

}
