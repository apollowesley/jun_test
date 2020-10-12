package com.evil.service;

import java.util.List;

import com.evil.pojo.assist.QuestionTemplate;

public interface QuestionTemplateService extends BaseService<QuestionTemplate> {
	
	/**
	 * ������е�����ģ��
	 * @param isTypeNum �Ƿ��ѯ�������͵�����
	 */
	public List<QuestionTemplate> findAllTemplates(boolean isTypeNum);
	/**
	 *ͨ�������id������еĺ���id 
	 */
	public List<String> findTemplateChildrensByid(String id);
	/**
	 *��������ģ�� 
	 */
	public void saveOrUpdateTemplate(QuestionTemplate model,String pid);
	/**
	 * ɾ������ģ��
	 */
	public void deleteQuestionTemplate(String id);
	/**
	 * ��������������������
	 * @return
	 */
	public long[] findAllQuestionTypeNum();
	
	/**
	 * ����ģ��id��ѯ��ģ���²�ͬ�������͵�����
	 */
	public long[] findQuestionTypeByTemplateId(String id);
}
