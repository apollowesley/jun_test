package com.evil.service;

import java.util.List;

import com.evil.pojo.statistics.QuestionStatisticsModel;

public interface StatisticalService {
	/**
	 * ��ָ�����Ծ������������ģ��
	 * @return
	 */
	List<QuestionStatisticsModel> statisticalQuestionModel(String id);

}
