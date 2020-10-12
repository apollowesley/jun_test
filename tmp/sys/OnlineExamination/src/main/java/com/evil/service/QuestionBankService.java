package com.evil.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.evil.pojo.Questions;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface QuestionBankService extends BaseService<Questions> {
	
	/**
	 * 根据分页信息 排序信息 以及题型查询问题
	 * @param page 分页信息
	 * @param map 查询条件
	 */
	public PageResult findPageQuestions(Page page,Map<String, Object> map,String...sortfields) ;
	
	public Questions findById(String id);

	/**
	 *根据参数拼hql语句
	 */
	public String accordingParamHql(Map<String,Object> map,String hql,String...sortfields);
	
	/**
	 * 批量删除问题 
	 */
	public void batchDeleteQuestion(String ids[]) ;

	public long getQuestionTypeNum(int i);
	
	public List<Questions> findQuestionsBankByType(int type);
	/**
	 * 通过问题的id获得所有的试卷id
	 * @param ids
	 */
	public String[] findPaperIdByQuestionsIds(String[] ids);
	/**
	 * 移动问题到指定的模板
	 */
	public void moveQuestions(String[] ids, String tid);
	/**
	 *获取所有的问题 
	 */
	public List<Questions> findAllQuestions();
	/**
	 *清空所有的试题和试题模板 
	 */
	public void clearQuestionBnakAndTemplate();
	/**
	 * 导入试题到数据库
	 * @throws Exception 
	 */
	public void ImportQuestionsDatabase(File questionsExcel,String fileName) throws Exception;

}
