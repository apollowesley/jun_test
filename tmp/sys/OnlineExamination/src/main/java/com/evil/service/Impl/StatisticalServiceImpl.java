package com.evil.service.Impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.evil.dao.AnswerDao;
import com.evil.dao.PaperDao;
import com.evil.dao.QuestionsDao;
import com.evil.pojo.Questions;
import com.evil.pojo.statistics.QuestionStatisticsModel;
import com.evil.service.StatisticalService;
import com.evil.util.QuestionsUtil.QuestionType;
import com.evil.util.StringUtil;

@Service(value="statisticalService")
public class StatisticalServiceImpl implements StatisticalService {

	@Resource(name = "questionsDao")
	private QuestionsDao questionsDao;
	@Resource(name = "paperDao")
	private PaperDao paperdao;
	@Resource
	private AnswerDao answerDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<QuestionStatisticsModel> statisticalQuestionModel(String id) {
		List<QuestionStatisticsModel> questionModels = new ArrayList<QuestionStatisticsModel>();
		String sql = "select questionsID from tb_paper_questions where paperID=?";
		String qids = StringUtil.arr2SqlStr(paperdao.executeSQLQuery(null, sql,
				id).toArray());
		String hql = "from Questions where id in (" + qids
				+ ") ORDER BY questionsType ASC";
		List<Questions> questions = questionsDao.findEntityByHQL(hql);
		String countSql = "select rights,count(*) from tb_answers where paperid=? and questionid=? GROUP BY rights ORDER BY rights ASC"; // 查询回答该问题的人数
		QuestionStatisticsModel qModel = null;
		List count = null;
		Long num = 0l;
		for (Questions q : questions) {
			num = 0l;
			qModel = new QuestionStatisticsModel();
			qModel.setQuestions(q);
			count = answerDao.executeSQLQuery(null, countSql, id, q.getId());
			for (int i = 0; i < count.size(); i++) {
				Object[] o = (Object[]) count.get(i);
				if ((Boolean) o[0]) {
					qModel.setRightCount(((BigInteger) o[1]).longValue());
				}
				num += ((BigInteger) o[1]).longValue();
			}
			qModel.setCount(num);
			if (q.getQuestionsType() == QuestionType.single.ordinal()) {
				qModel.setOptionsCount(findSingleOptionsCount(id, q.getId()));
			}
			questionModels.add(qModel);
		}
		return questionModels;
	}

	@SuppressWarnings("rawtypes")
	private long[] findSingleOptionsCount(String pid, String qid) {
		long[] ocount = new long[4];
		// 查询每个答案的回答的数量
		String ohql = "select answers,count(*) from tb_answers where paperid=? and questionid=? GROUP BY answers ORDER BY answers ASC";
		List optionsCount = answerDao.executeSQLQuery(null, ohql, pid,qid);
		for (int i = 0; i < optionsCount.size(); i++) {
			Object[] o = (Object[]) optionsCount.get(i);
			if ("A".equals(o[0].toString())) {
				ocount[0] = ((BigInteger) o[1]).longValue();
			} else if ("B".equals(o[0].toString())) {
				ocount[1] = ((BigInteger) o[1]).longValue();
			} else if ("C".equals(o[0].toString())) {
				ocount[2] = ((BigInteger) o[1]).longValue();
			} else if ("D".equals(o[0].toString())) {
				ocount[3] = ((BigInteger) o[1]).longValue();
			}
		}
		return ocount;
	}

}
