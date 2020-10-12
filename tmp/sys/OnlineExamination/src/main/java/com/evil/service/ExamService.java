package com.evil.service;

import java.util.Map;

import com.evil.pojo.Exam;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface ExamService extends BaseService<Exam> {
	/**
	 * ��ѯ�����б���ҳ
	 * @param page  ��ҳ��Ϣ
	 * @param map   ��������
	 * @param sortfields ����ʽ
	 * @return
	 */
	public PageResult findExamPage(Page page, Map<String, Object> map,
			String... sortfields);

	/**
	 * ɾ������
	 * @param model
	 */
	public void deleteExam(Exam model);


}
