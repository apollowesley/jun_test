package com.evil.service;

import com.evil.pojo.WrongQuestions;

public interface WrongQuestionsService {

	public void SaveWrongQuestions(WrongQuestions entity);
	public WrongQuestions findWrongQuestions(String uid ,String qid);
}
