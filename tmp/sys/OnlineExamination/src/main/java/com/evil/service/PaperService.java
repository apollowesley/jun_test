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
	 * 获取所有的试卷
	 */
	public List<Paper> findAllPaper();

	/**
	 * 分页查询多条件排序查询试卷
	 * 
	 * @param page
	 * @param map
	 * @param sortfields
	 */
	public PageResult findPagePaper(Page page, Map<String, Object> map,
			String... sortfields);

	/**
	 * 通过id查询试卷
	 */
	public Paper findById(String id);

	/**
	 * 获得该对象的及其子对象的列表
	 */
	Paper getPaperWithChildren(String pid, boolean deptIncluded);

	/**
	 * 计算当前试卷计算总分
	 */
	int[] calculateScore(Paper paper, List<Answer> answer, User user);

	/**
	 * 根据试卷的名称查询符合要求的试卷
	 */
	public List<Paper> findPapersByName(String paperName);

	/**
	 * 根据id删除指定的试卷
	 */
	public void deletePaPer(String pid);

	/**
	 * 批量删除多个试卷
	 */
	public void batchDeletePaper(String ids);

	/**
	 * 查询所得可用试卷
	 */
	public List<Paper> findAllEnabledPaper();

	/**
	 * 开关试卷
	 */
	public void toggleStatus(String pid);

	/**
	 * 保存/更新试卷
	 */
	public void saveOrUpdatePaper(Paper model);

	/**
	 * 插入试卷
	 */
	public void inestPaper(Paper paper);

	/**
	 * 查询试卷的类型信息
	 */
	public List<PaperType> findPaperTypers(User user);

	/**
	 * 根据问题和用户保存错题
	 */
	void saveWrongQuestion(Questions q, User user);

	/**
	 * 通过分页信息查询和参数查询错题
	 */
	public PageResult findPageWrongs(Page page, Map<String, Object> map,
			String... sortfields);

	/**
	 * 删除错题
	 */
	public void deleteWrongQuestion(String wid);

	/**
	 * 根据错题的id查找错题
	 */
	public WrongQuestions findWrongQuestionById(String wid);

	/**
	 * 都过map参数查询试卷的不同问题 然后分页
	 */
	PageResult findPaperQuestionsByType(Page page, Map<String, Object> map,
			String... sortfields);

	/**
	 * 通过 试卷的id清空试卷的指定类型的问题
	 */
	public void clearQuestion(String pid, int questionsType);

	/**
	 * 根据问题id查询问题
	 */
	Questions findQuestionById(String qid);

	/**
	 * 根据问题的id删除问题
	 */
	public void deleteQuestion(String pid, String qid);

	/**
	 * 批量删除问题
	 */
	public void batchDeleteQuestion(String pid, String[] ids);

	/**
	 * 保存或更新问题
	 */
	public void saveOrUpdateQuestion(Questions q);

	/**
	 * 修改试卷的问题信息
	 */
	void alterPaperDetailMess(String pid);

	/**
	 * 智能添加试卷
	 */
	public void savIntelligentPaper(Paper model,
			ArrayList<TemplateForm> templateForms);
	
	/**
	 *在试卷中添加试题 
	 */
	public void addPaperQuestion(String pid, Object[] strSplit);
	/**
	 * 智能在试卷中添加试题
	 * @param id 试卷id
	 * @param templateForms 添加问题的模板的参数
	 */
	public void intelligenceAddPaperQuestion(String id,
			ArrayList<TemplateForm> templateForms);
	
	/**
	 * 获取正式考试试卷
	 * @return
	 */
	public Paper findFormalTestPaper();

}
