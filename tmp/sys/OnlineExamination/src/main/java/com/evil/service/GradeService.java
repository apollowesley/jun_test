package com.evil.service;

import java.util.List;
import java.util.Map;

import com.evil.pojo.Answer;
import com.evil.pojo.User;
import com.evil.pojo.UserPaper;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface GradeService  {
	
	/**
	 *保存UserPaper对象 
	 */
	public String saveUserPaper(UserPaper userPaper,List<Answer> list);
	
	/**
	 *批量保存答案 
	 */
	public void saveAnswers(List<Answer> processAnswers);
	
	/**
	 * 批量删除成绩
	 */
	public void batchDeleteGrade(String[] ids);
	
	/**
	 *根据id删除成绩
	 */
	void deleteGrade(String id);
	
	/**
	 * 清空所有的成绩
	 */
	public void clearGrade();
	
	/**
	 *根据id查询成绩
	 */
	public UserPaper findGradeById(String gid);
	
	/**
	 * 查询一个成绩的答案
	 */
	public List<Answer> findGradeAnswer(UserPaper grade);
	
	/**
	 * 根据分页信息 进行排序条件查询成绩
	 * @param page  分页信息
	 * @param map  多个参数的集 （属性：值）
	 */
	public PageResult findPageGrade(Page page, Map<String, Object> map,String...sortfields);
	/**
	 *通过试卷的id 查询改试卷的平均分 
	 * @return
	 */
	public double[] getGradeAverageByPaperId(String id);

	public UserPaper findFormalTestGrade(User user);

}
