package com.open1111.dao;

import java.util.List;
import java.util.Map;

import com.open1111.entity.Link;

/**
 * ��������Dao�ӿ�
 * @author user
 *
 */
public interface LinkDao {

	/**
	 * ����������ҳ��ѯ�������Ӽ���
	 * @param map
	 * @return
	 */
	public List<Link> list(Map<String,Object> map);
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	
	/**
	 * ������������
	 * @param link
	 * @return
	 */
	public Integer add(Link link);
	
	/**
	 * �޸���������
	 * @param link
	 * @return
	 */
	public Integer update(Link link);
	
	/**
	 * ɾ����������
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
}