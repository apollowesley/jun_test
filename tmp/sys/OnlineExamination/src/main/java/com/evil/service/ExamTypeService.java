package com.evil.service;

import com.evil.pojo.ExamType;

public interface ExamTypeService extends BaseService<ExamType> {
	
	/**
	 * ɾ���ÿ������
	 * @param model
	 */
	void deleteExamType(ExamType model);

}
