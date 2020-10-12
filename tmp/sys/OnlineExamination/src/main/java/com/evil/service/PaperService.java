package com.evil.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.evil.pojo.Answer;
import com.evil.pojo.Paper;
import com.evil.pojo.Questions;
import com.evil.pojo.User;
import com.evil.pojo.WrongQuestions;
import com.evil.pojo.assist.PaperType;
import com.evil.struts2.form.TemplateForm;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface PaperService {

	/**
	 * ��ȡ���е��Ծ�
	 */
	public List<Paper> findAllPaper();

	/**
	 * ��ҳ��ѯ�����������ѯ�Ծ�
	 * 
	 * @param page
	 * @param map
	 * @param sortfields
	 */
	public PageResult findPagePaper(Page page, Map<String, Object> map,
			String... sortfields);

	/**
	 * ͨ��id��ѯ�Ծ�
	 */
	public Paper findById(String id);

	/**
	 * ��øö���ļ����Ӷ�����б�
	 */
	Paper getPaperWithChildren(String pid, boolean deptIncluded);

	/**
	 * ���㵱ǰ�Ծ�����ܷ�
	 */
	int[] calculateScore(Paper paper, List<Answer> answer, User user);

	/**
	 * �����Ծ�����Ʋ�ѯ����Ҫ����Ծ�
	 */
	public List<Paper> findPapersByName(String paperName);

	/**
	 * ����idɾ��ָ�����Ծ�
	 */
	public void deletePaPer(String pid);

	/**
	 * ����ɾ������Ծ�
	 */
	public void batchDeletePaper(String ids);

	/**
	 * ��ѯ���ÿ����Ծ�
	 */
	public List<Paper> findAllEnabledPaper();

	/**
	 * �����Ծ�
	 */
	public void toggleStatus(String pid);

	/**
	 * ����/�����Ծ�
	 */
	public void saveOrUpdatePaper(Paper model);

	/**
	 * �����Ծ�
	 */
	public void inestPaper(Paper paper);

	/**
	 * ��ѯ�Ծ��������Ϣ
	 */
	public List<PaperType> findPaperTypers(User user);

	/**
	 * ����������û��������
	 */
	void saveWrongQuestion(Questions q, User user);

	/**
	 * ͨ����ҳ��Ϣ��ѯ�Ͳ�����ѯ����
	 */
	public PageResult findPageWrongs(Page page, Map<String, Object> map,
			String... sortfields);

	/**
	 * ɾ������
	 */
	public void deleteWrongQuestion(String wid);

	/**
	 * ���ݴ����id���Ҵ���
	 */
	public WrongQuestions findWrongQuestionById(String wid);

	/**
	 * ����map������ѯ�Ծ�Ĳ�ͬ���� Ȼ���ҳ
	 */
	PageResult findPaperQuestionsByType(Page page, Map<String, Object> map,
			String... sortfields);

	/**
	 * ͨ�� �Ծ��id����Ծ��ָ�����͵�����
	 */
	public void clearQuestion(String pid, int questionsType);

	/**
	 * ��������id��ѯ����
	 */
	Questions findQuestionById(String qid);

	/**
	 * ���������idɾ������
	 */
	public void deleteQuestion(String pid, String qid);

	/**
	 * ����ɾ������
	 */
	public void batchDeleteQuestion(String pid, String[] ids);

	/**
	 * ������������
	 */
	public void saveOrUpdateQuestion(Questions q);

	/**
	 * �޸��Ծ��������Ϣ
	 */
	void alterPaperDetailMess(String pid);

	/**
	 * ��������Ծ�
	 */
	public void savIntelligentPaper(Paper model,
			ArrayList<TemplateForm> templateForms);
	
	/**
	 *���Ծ���������� 
	 */
	public void addPaperQuestion(String pid, Object[] strSplit);
	/**
	 * �������Ծ����������
	 * @param id �Ծ�id
	 * @param templateForms ��������ģ��Ĳ���
	 */
	public void intelligenceAddPaperQuestion(String id,
			ArrayList<TemplateForm> templateForms);
	
	/**
	 * ��ȡ��ʽ�����Ծ�
	 * @return
	 */
	public Paper findFormalTestPaper();

}
