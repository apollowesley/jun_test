package com.evil.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.evil.dao.AnswerDao;
import com.evil.dao.UserPaperDao;
import com.evil.pojo.Answer;
import com.evil.pojo.User;
import com.evil.pojo.UserPaper;
import com.evil.service.GradeService;
import com.evil.util.Page;
import com.evil.util.PageResult;
import com.evil.util.PageUtil;
import com.evil.util.QuestionsUtil;
import com.evil.util.ValidateUtil;

@Service("gradeService")
public class GradeServiceImpl implements GradeService {
	@Resource
	private UserPaperDao userPaperDao;
	@Resource
	private AnswerDao answerDao;

	@Override
	public String saveUserPaper(UserPaper userPaper, List<Answer> list) {
		String id = userPaperDao.saveEntity(userPaper);
		Date date = userPaper.getFinishTime();
		for (Answer a : list) {
			a.setAnswerTime(date);
			answerDao.saveEntity(a);
		}
		return id;
	}

	@Override
	public void saveAnswers(List<Answer> answers) {
		Date date = new Date(System.currentTimeMillis());
		for (Answer a : answers) {
			a.setAnswerTime(date);
			answerDao.saveEntity(a);
		}
	}

	@Override
	public void batchDeleteGrade(String[] ids) {
		if (!ValidateUtil.isNull(ids)) {
			for (String id : ids) {
				deleteGrade(id);
			}
		}
	}

	@Override
	public void deleteGrade(String id) {
		UserPaper up = userPaperDao.getEntity(id);
		// 先删除出问题
		String hql = "delete from Answer a where a.userid=? and a.answerTime=?";
		userPaperDao.batchEntityByHQL(hql, up.getUser().getId(),
				up.getFinishTime());
		userPaperDao.deleteEntity(up);
	}

	@Override
	public void clearGrade() {
		String hql = "delete from Answer";
		userPaperDao.batchEntityByHQL(hql);
		hql = "delete from UserPaper";
		userPaperDao.batchEntityByHQL(hql);
	}

	@Override
	public UserPaper findGradeById(String gid) {
		UserPaper userPaper = userPaperDao.getEntity(gid);
		userPaper.getPaper().getQuestions().size();
		return userPaperDao.getEntity(gid);
	}

	@Override
	public List<Answer> findGradeAnswer(UserPaper grade) {
		String hql = "from Answer a where a.paperid=? and a.userid=? and a.answerTime=?";
		return answerDao.findEntityByHQL(hql, grade.getPaper().getId(), grade
				.getUser().getId(), grade.getFinishTime());
	}

	@Override
	public PageResult findPageGrade(Page page, Map<String, Object> map,
			String... sortfields) {
		String hql = "select count(*) from UserPaper u  where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		Long count = null;
		count = (Long) userPaperDao.uniqueResult(hql);
		count = count == null ? 0 : count;
		page = PageUtil.createPage(page.getNumPerPage(), count,
				page.getCurrentPage());// 根据总记录数创建分页信息
		hql = "from UserPaper u where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		List<UserPaper> list = userPaperDao.findEntityByPage(hql,
				page.getCurrentPage(), page.getNumPerPage());// 通过分页信息取得试题
		PageResult result = new PageResult(page, list);// 封装分页信息和记录信息，返回给调用处
		return result;
	}

	/**
	 * 根据参数拼hql语句
	 */
	private String accordingParamHql(Map<String, Object> map, String hql,
			String... sortfields) {

		// 拼接限制条件
		if (map != null && !map.isEmpty()) {
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					if (entry.getKey().equals("userName")) {
						hql += " and user.userName like '%" + entry.getValue()
								+ "%'";
					} else if (entry.getKey().equals("paperTitle")) {
						hql += " and paper.title like '%" + entry.getValue()
								+ "%'";
					} else if ("paperId".equals(entry.getKey())) {
						hql += " and paper.id='" + entry.getValue() + "'";
					} else if (entry.getKey().equals("finishTime")) {
						hql += " and finishTime like '%" + entry.getValue()
								+ "%'";
					} else if (entry.getKey().equals("userId")) {
						hql += " and user.id ='" + entry.getValue() + "'";
					} else if ("paper.paperType".equals(entry.getKey())) {
						hql += " and paper.paperType=" + entry.getValue();
					} else {
						hql += " and " + entry.getKey() + "="
								+ entry.getValue();
					}
				}
			}
			// 拼接排序
			if (!ValidateUtil.isNull(sortfields)) {
				hql += " order by ";
				for (String s : sortfields) {
					if (!ValidateUtil.isNull(s))
						hql += s + ",";
				}
				hql = hql.substring(0, hql.length() - 1);
			}
		}
		return hql;
	}

	@Override
	public double[] getGradeAverageByPaperId(String id) {
		String hql = "from UserPaper where isDelete=1 and paper.id=?";
		List<UserPaper> grades = userPaperDao.findEntityByHQL(hql, id);
		double average[] = new double[QuestionsUtil.typeNum + 1];
		double a, b, c;
		for (UserPaper userPaper : grades) {
			a = Double.parseDouble(userPaper.getScoreArr()[0]);
			b = Double.parseDouble(userPaper.getScoreArr()[1]);
			c = Double.parseDouble(userPaper.getScoreArr()[2]);
			if (a != -1) {
				average[0] += a;
			}
			if (b != -1) {
				average[1] += b;
			}
			if (c != -1) {
				average[2] += c;
			}
			average[3]+=userPaper.getAllscore();
		}
		average[0] = average[0] / grades.size();
		average[1] = average[1] / grades.size();
		average[2] = average[2] / grades.size();
		average[3] = average[3] / grades.size();
		return average;
	}

	@Override
	public UserPaper findFormalTestGrade(User user) {
		String hql="from UserPaper where user.id=? and paper.formalTest=true";
		List<UserPaper> userPapers=userPaperDao.findEntityByHQL(hql,user.getId());
		UserPaper userPaper=ValidateUtil.isNull(userPapers)?null:userPapers.get(0);
		if(userPaper!=null){
			userPaper.getPaper().getQuestions().size();
		}
		return userPaper;
	}
}
