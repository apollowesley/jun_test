package com.evil.dao.Impl;

import org.springframework.stereotype.Repository;

import com.evil.dao.QuestionsDao;
import com.evil.pojo.Questions;
@Repository("questionsDao")
public class QuestionDaoImpl extends BaseIpml<Questions> implements QuestionsDao {

}
