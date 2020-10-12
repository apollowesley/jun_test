package com.evil.service;

import java.util.List;

import com.evil.pojo.assist.QuestionTemplate;

public interface QuestionTemplateService extends BaseService<QuestionTemplate> {
	
	/**
	 * 获得所有的试题模板
	 * @param isTypeNum 是否查询试题类型的数量
	 */
	public List<QuestionTemplate> findAllTemplates(boolean isTypeNum);
	/**
	 *通过试题的id获得所有的孩子id 
	 */
	public List<String> findTemplateChildrensByid(String id);
	/**
	 *保存试题模板 
	 */
	public void saveOrUpdateTemplate(QuestionTemplate model,String pid);
	/**
	 * 删除试题模板
	 */
	public void deleteQuestionTemplate(String id);
	/**
	 * 获得所有类型试题的数量
	 * @return
	 */
	public long[] findAllQuestionTypeNum();
	
	/**
	 * 根据模板id查询该模板下不同问题类型的数量
	 */
	public long[] findQuestionTypeByTemplateId(String id);
}
