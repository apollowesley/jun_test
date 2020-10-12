package com.evil.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.evil.pojo.Questions;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface QuestionBankService extends BaseService<Questions> {
	
	/**
	 * ���ݷ�ҳ��Ϣ ������Ϣ �Լ����Ͳ�ѯ����
	 * @param page ��ҳ��Ϣ
	 * @param map ��ѯ����
	 */
	public PageResult findPageQuestions(Page page,Map<String, Object> map,String...sortfields) ;
	
	public Questions findById(String id);

	/**
	 *���ݲ���ƴhql���
	 */
	public String accordingParamHql(Map<String,Object> map,String hql,String...sortfields);
	
	/**
	 * ����ɾ������ 
	 */
	public void batchDeleteQuestion(String ids[]) ;

	public long getQuestionTypeNum(int i);
	
	public List<Questions> findQuestionsBankByType(int type);
	/**
	 * ͨ�������id������е��Ծ�id
	 * @param ids
	 */
	public String[] findPaperIdByQuestionsIds(String[] ids);
	/**
	 * �ƶ����⵽ָ����ģ��
	 */
	public void moveQuestions(String[] ids, String tid);
	/**
	 *��ȡ���е����� 
	 */
	public List<Questions> findAllQuestions();
	/**
	 *������е����������ģ�� 
	 */
	public void clearQuestionBnakAndTemplate();
	/**
	 * �������⵽���ݿ�
	 * @throws Exception 
	 */
	public void ImportQuestionsDatabase(File questionsExcel,String fileName) throws Exception;

}
