package com.evil.service;

import java.util.List;

import com.evil.pojo.statistics.QuestionStatisticsModel;

public interface StatisticalService {
	/**
	 * 对指定的试卷分析生成问题模型
	 * @return
	 */
	List<QuestionStatisticsModel> statisticalQuestionModel(String id);

}
