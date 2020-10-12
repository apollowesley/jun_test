package com.evil.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.evil.pojo.system.Right;
import com.evil.util.Page;
import com.evil.util.PageResult;

/**
 *Ȩ��Service
 */
public interface RightService extends BaseService<Right> {

	/**
	 * ����model�Ƿ����id����/����Ȩ��
	 */
	void saveOrUpdateRight(Right model);

	/**
	 *���URL��rights�в���������� 
	 */
	void appendRightByUrl(String url);

	/**
	 *��������Ȩ�� 
	 */
	void batchUpdateRights(List<Right> allRights);

	/**
	 *ͨ��id��������е�Ȩ�޼��� 
	 */
	List<Right> findRightsInRange(Integer[] ownRightIds);

	/**
	 *��ѯ�����ڸý�ɫ��Ȩ�� 
	 */
	List<Right> findRightsNotInRange(Set<Right> set);
	/**
	 * ��ѯȨ���б���ҳ
	 * @param page  ��ҳ��Ϣ
	 * @param map   ��������
	 * @param sortfields ����ʽ
	 * @return
	 */
	public PageResult findRightsPage(Page page, Map<String, Object> map,
			String... sortfields);

	/**
	 *�������Ȩ��λ 
	 */
	int getMaxPos();

}
