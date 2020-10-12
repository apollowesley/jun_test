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
	 *����UserPaper���� 
	 */
	public String saveUserPaper(UserPaper userPaper,List<Answer> list);
	
	/**
	 *��������� 
	 */
	public void saveAnswers(List<Answer> processAnswers);
	
	/**
	 * ����ɾ���ɼ�
	 */
	public void batchDeleteGrade(String[] ids);
	
	/**
	 *����idɾ���ɼ�
	 */
	void deleteGrade(String id);
	
	/**
	 * ������еĳɼ�
	 */
	public void clearGrade();
	
	/**
	 *����id��ѯ�ɼ�
	 */
	public UserPaper findGradeById(String gid);
	
	/**
	 * ��ѯһ���ɼ��Ĵ�
	 */
	public List<Answer> findGradeAnswer(UserPaper grade);
	
	/**
	 * ���ݷ�ҳ��Ϣ ��������������ѯ�ɼ�
	 * @param page  ��ҳ��Ϣ
	 * @param map  ��������ļ� �����ԣ�ֵ��
	 */
	public PageResult findPageGrade(Page page, Map<String, Object> map,String...sortfields);
	/**
	 *ͨ���Ծ��id ��ѯ���Ծ��ƽ���� 
	 * @return
	 */
	public double[] getGradeAverageByPaperId(String id);

	public UserPaper findFormalTestGrade(User user);

}
