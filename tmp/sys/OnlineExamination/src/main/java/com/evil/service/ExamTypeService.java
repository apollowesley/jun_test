package com.evil.service;

import com.evil.pojo.ExamType;

public interface ExamTypeService extends BaseService<ExamType> {
	
	/**
	 * É¾³ý¸Ã¿¼ÊÔÀà±ð
	 * @param model
	 */
	void deleteExamType(ExamType model);

}
