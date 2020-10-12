package com.evil.service.Impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.evil.dao.AnswerDao;
import com.evil.dao.BaseDao;
import com.evil.dao.PaperDao;
import com.evil.dao.QuestionsDao;
import com.evil.dao.UserPaperDao;
import com.evil.dao.WrongQuestionsDao;
import com.evil.pojo.Answer;
import com.evil.pojo.Paper;
import com.evil.pojo.Questions;
import com.evil.pojo.User;
import com.evil.pojo.WrongQuestions;
import com.evil.pojo.assist.PaperType;
import com.evil.pojo.system.TypeDictionary;
import com.evil.service.PaperService;
import com.evil.struts2.form.TemplateForm;
import com.evil.util.Page;
import com.evil.util.PageResult;
import com.evil.util.PageUtil;
import com.evil.util.QuestionsUtil.QuestionType;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Service("PaperService")
public class PaperServiceImpl implements PaperService {
	@Resource(name = "paperDao")
	private PaperDao paperdao;

	@Resource
	private AnswerDao answerDao;
	@Resource
	private UserPaperDao userPaperDao;
	@Resource
	private QuestionsDao questionsDao;
	@Resource
	private WrongQuestionsDao wrongQuestionsDao;
	@Resource
	private BaseDao<TypeDictionary> typeDictionaryDao;

	@Override
	public List<Paper> findAllPaper() {
		String hql = "from Paper where isDelete=?";
		return paperdao.findEntityByHQL(hql, "1");
	}

	@Override
	public PageResult findPagePaper(Page page, Map<String, Object> map,
			String... sortfields) {
		String hql = "select count(*) from Paper p  where isDelete=1 and 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		Long count = null;
		count = (Long) paperdao.uniqueResult(hql);
		count = count == null ? 0 : count;
		page = PageUtil.createPage(page.getNumPerPage(), count,
				page.getCurrentPage());// �����ܼ�¼��������ҳ��Ϣ
		hql = "from Paper p where isDelete=1 and 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		List<Paper> list = paperdao.findEntityByPage(hql,
				page.getCurrentPage(), page.getNumPerPage());// ͨ����ҳ��Ϣȡ������
		PageResult result = new PageResult(page, list);// ��װ��ҳ��Ϣ�ͼ�¼��Ϣ�����ظ����ô�
		return result;
	}

	@Override
	public Paper findById(String sid) {
		return paperdao.getEntity(sid);
	}

	@Override
	public Paper getPaperWithChildren(String pid, boolean nochildren) {
		Paper paper = this.findById(pid);
		if (!nochildren) {
			for (Questions q : paper.getQuestions()) {
				q.getId();
			}
		}
		return paper;
	}

	/**
	 * �����ܷ�
	 */
	@Override
	public int[] calculateScore(Paper paper, List<Answer> answer, User user) {
		Map<String, Questions> questionMap = new HashMap<String, Questions>(); // ������ͨ��id�����������
																				// Map
		Questions questions = null;
		String itemTypes[] = paper.getItemType().split(","); // ��������ͼ���
		String itemNum[] = paper.getItemNumber().split(","); // ��ͬ���͵�����
		String itemScore[] = paper.getItemScore().split(","); // ��ͬ���͵ķ�ֵ
		int item[][] = new int[4][2];
		for (int i = 0; i < itemTypes.length; i++) {
			int a = Integer.parseInt(itemTypes[i]);
			item[a][0] = Integer.parseInt(itemNum[a]);
			item[a][1] = Integer.parseInt(itemScore[a]);
		}
		int allscore[] = new int[item.length + 1]; // ��¼�������͵ĵ÷�
		List<Questions> list = new ArrayList<Questions>(paper.getQuestions()); // ����Ծ������
		for (Questions q : list) { // ������ͨ��id����������� Map
			questionMap.put(q.getId(), q);
		}
		if (!ValidateUtil.isNull(answer)) { // ����÷�
			for (Answer a : answer) {
				questions = questionMap.get(a.getQuestionId());
				if (a.getAnswers().equals(questions.getAnswer())) {
					a.setRights(true);
					int type = questions.getQuestionsType();
					allscore[type] += item[type][1];
				} else {
					a.setRights(false);
					saveWrongQuestion(questions, user);
				}
			}
		} else {
			for (Questions q : questionMap.values()) {
				saveWrongQuestion(q, user);
			}
		}
		for (int j = 0; j < 4; j++) { // ���û�и������� �򽫵÷�����Ϊ-1
			if (item[j][0] == 0) {
				allscore[j] = -1;
			}
		}
		for (int b = 0; b < allscore.length - 1; b++) { // ͳ�Ƶ÷�
			if (allscore[b] < 0)
				continue;
			allscore[allscore.length - 1] += allscore[b];
		}
		return allscore;
	}

	@Override
	public void saveWrongQuestion(Questions q, User user) {
		String hql = "from WrongQuestions w where w.userId=? and w.questions=?";
		List<WrongQuestions> list = wrongQuestionsDao.findEntityByHQL(hql,
				user.getId(), q);
		WrongQuestions w = null;
		if (ValidateUtil.isNull(list)) {
			w = new WrongQuestions();
			w.setQuestions(q);
			w.setUserId(user.getId());
			w.setCorrectNumber(1);
			wrongQuestionsDao.saveEntity(w);
		} else {
			w = list.get(0);
			w.setCorrectNumber(w.getCorrectNumber() + 1);
		}
	}

	@Override
	public List<Paper> findPapersByName(String paperName) {
		String hql = "from Paper p where p.title like ? and p.isDelete=1";
		return paperdao.findEntityByHQL(hql, "%" + paperName + "%");
	}

	@Override
	public void deletePaPer(String pid) {
		// ɾ��Answer
		String hql = "delete from Answer a where a.paperid=?";
		answerDao.batchEntityByHQL(hql, pid);
		// ��ɾ���������
		hql = "delete from tb_paper_questions where paperID=?";
		questionsDao.executeSQL(hql, pid);
		// ��ɾ���ɼ�
		String sql = "delete from UserPaper where paper.id=?";
		paperdao.batchEntityByHQL(sql, pid);
		// ��ɾ��paper
		hql = "Update Paper p set p.isDelete=0 where p.id=?";
		paperdao.batchEntityByHQL(hql, pid);
	}

	@Override
	public void batchDeletePaper(String ids) {
		if (!ValidateUtil.isNull(ids)) {
			String idsArr[] = StringUtil.strSplit(ids, ",");
			for (String id : idsArr) {
				deletePaPer(id);
			}
		}
	}

	@Override
	public List<Paper> findAllEnabledPaper() {
		String hql = "from Paper p where p.cloesd=true and p.isDelete=1";
		return paperdao.findEntityByHQL(hql);
	}

	@Override
	public void toggleStatus(String pid) {
		Paper p = paperdao.getEntity(pid);
		String hql = "update Paper p set p.cloesd=? where p.id=?";
		paperdao.batchEntityByHQL(hql, !p.isCloesd(), pid);
	}

	@Override
	public void saveOrUpdatePaper(Paper model) {
		paperdao.saveOrUpdateEntity(model);
	}

	public void inestPaper(Paper paper) {
		paperdao.saveEntity(paper);
	}

	/**
	 * ���ݲ���ƴhql���
	 */
	private String accordingParamHql(Map<String, Object> map, String hql,
			String... sortfields) {

		// ƴ����������
		if (map != null && !map.isEmpty()) {
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					if ("p.id".equals(entry.getKey())) {
						hql += " and p.id='" + entry.getValue() + "'";
					} else if (entry.getKey().equals("title")) {
						hql += " and p.title like '" + entry.getValue() + "'";
					} else {
						hql += " and " + entry.getKey() + "="
								+ entry.getValue();
					}
				}
			}
			// ƴ������
			if (!ValidateUtil.isNull(sortfields)) {
				String fied=" order by ";
				for (String s : sortfields) {
					if (!ValidateUtil.isNull(s))
						fied += s + ",";
				}
				if(" order by ".equals(fied)){
					fied=" ";
				}
				hql =hql+ fied.substring(0, fied.length() - 1);
			}
		}
		return hql;
	}

	@Override
	public List<PaperType> findPaperTypers(User user) {
		String hql = "from TypeDictionary where type=?";
		List<TypeDictionary> dictionarys = typeDictionaryDao.findEntityByHQL(
				hql, TypeDictionary.PAPER_TYPE);
		List<PaperType> list = new ArrayList<PaperType>();
		PaperType type = null;
		for (TypeDictionary dictionary : dictionarys) {
			type = new PaperType();
			type.setPaperTypeNmae(dictionary.getName());
			type.setAllNum(findParperTypeAllNum(dictionary.getValue()));
			type.setFinshNum(findPaperTypefinshNum(user, dictionary.getValue()));
			type.setPaperTypeId(dictionary.getValue());
			list.add(type);
		}

		return list;
	}

	public long findParperTypeAllNum(int paperType) {
		String hql = "select count(*) from Paper p where p.cloesd=true and p.isDelete=1 and p.paperType=?";
		Long num = (Long) paperdao.uniqueResult(hql, paperType);
		return num == null ? 0 : num;
	}

	@SuppressWarnings("rawtypes")
	public long findPaperTypefinshNum(User user, int paperType) {
		String sql = "SELECT COUNT(*) FROM (SELECT DISTINCT up.paperID FROM tb_user_paper up WHERE up.userID=? and up.paperID in (SELECT id FROM tb_paper p WHERE p.paperType=?)) a";
		List list = paperdao
				.executeSQLQuery(null, sql, user.getId(), paperType);
		return ValidateUtil.isNull(list) ? 0 : ((BigInteger) list.get(0))
				.intValue();
	}

	@Override
	public PageResult findPageWrongs(Page page, Map<String, Object> map,
			String... sortfields) {
		String hql = "select count(*) from WrongQuestions  where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		Long count = null;
		count = (Long) wrongQuestionsDao.uniqueResult(hql);
		count = count == null ? 0 : count;
		page = PageUtil.createPage(page.getNumPerPage(), count,
				page.getCurrentPage());// �����ܼ�¼��������ҳ��Ϣ
		hql = "from WrongQuestions w join fetch w.questions questions where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		List<WrongQuestions> list = wrongQuestionsDao.findEntityByPage(hql,
				page.getCurrentPage(), page.getNumPerPage());// ͨ����ҳ��Ϣȡ������
		PageResult result = new PageResult(page, list);// ��װ��ҳ��Ϣ�ͼ�¼��Ϣ�����ظ����ô�
		return result;
	}

	@Override
	public void deleteWrongQuestion(String wid) {
		String hql = "delete from WrongQuestions where id=?";
		wrongQuestionsDao.batchEntityByHQL(hql, wid);
	}

	@Override
	public WrongQuestions findWrongQuestionById(String wid) {
		return wrongQuestionsDao.getEntity(wid);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageResult findPaperQuestionsByType(Page page,
			Map<String, Object> map, String... sortfields) {
		String hql = "select count(*) from Paper p join p.questions q where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		Long count = null;
		count = (Long) questionsDao.uniqueResult(hql);
		count = count == null ? 0 : count;
		page = PageUtil.createPage(page.getNumPerPage(), count,
				page.getCurrentPage());// �����ܼ�¼��������ҳ��Ϣ
		hql = "select q.id,q.answer,q.title,q.insertTime from Paper p join p.questions q where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		List list = questionsDao.findEntityByPage(hql, page.getCurrentPage(),
				page.getNumPerPage());// ͨ����ҳ��Ϣȡ������

		PageResult result = new PageResult(page, changeQuestionsBean(list));// ��װ��ҳ��Ϣ�ͼ�¼��Ϣ�����ظ����ô�
		return result;
	}

	/**
	 * �����ѯ����List<Object[]> ת��Ϊlist<Questions>
	 */
	@SuppressWarnings({ "rawtypes" })
	private List<Questions> changeQuestionsBean(List list) {
		List<Questions> questions = new ArrayList<Questions>();
		Questions q = null;
		Object[] o;
		for (int i = 0; i < list.size(); i++) {
			q = new Questions();
			o = (Object[]) list.get(i);
			q.setId(o[0].toString());
			q.setAnswer(o[1].toString());
			q.setTitle(o[2].toString());
			q.setInsertTime(((Date) o[3]));
			questions.add(q);
		}
		return questions;
	}

	@Override
	public void clearQuestion(String pid, int type) {
		// ��ɾ��Answer
		String hql = "delete from tb_answers where questionid in (SELECT q.ID FROM tb_questions q join tb_paper_questions pq where pq.questionsID=q.ID and pq.paperID=? and q.questionsType=?)";
		answerDao.executeSQL(hql, pid, type);
		hql = "SELECT q.ID FROM tb_questions q join tb_paper_questions pq where pq.questionsID=q.ID and pq.paperID=? and q.questionsType=?";
		Object list[] = questionsDao.executeSQLQuery(null, hql, pid, type)
				.toArray();
		System.out.println(StringUtil.arr2SqlStr(list));
		// ��ɾ������
		hql = "delete from tb_paper_questions where paperID=? and questionsID in ("
				+ StringUtil.arr2SqlStr(list) + ")";
		questionsDao.executeSQL(hql, pid);
		alterPaperDetailMess(pid); // ����ͳ��������Ϣ
	}

	@Override
	public Questions findQuestionById(String qid) {
		return questionsDao.getEntity(qid);
	}

	@Override
	public void deleteQuestion(String pid, String qid) {
		// ��ɾ��Answer
		String hql = "delete Answer a where a.questionId=?";
		answerDao.batchEntityByHQL(hql, qid);
		// ��ɾ���������
		hql = "delete from tb_paper_questions  where paperID=? and questionsID=?";
		questionsDao.executeSQL(hql, pid, qid);
		alterPaperDetailMess(pid);
	}

	@Override
	public void batchDeleteQuestion(String pid, String[] ids) {
		if (!ValidateUtil.isNull(ids)) {
			for (String id : ids) {
				deleteQuestion(pid, id);
			}
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void alterPaperDetailMess(String pid) {
		Paper paper = getPaperWithChildren(pid, true);
		// ��ѯָ���Ծ�ĵ����� ��������������ͷ���
		String sql = "SELECT q.questionsType,COUNT(*) FROM tb_questions q WHERE q.id in (select questionsID from tb_paper_questions where paperID=?) GROUP BY q.questionsType ORDER BY q.questionsType ASC";
		List list = paperdao.executeSQLQuery(null, sql, paper.getId());
		int num[] = new int[paper.getMaxType()];
		List<Integer> type = new LinkedList<Integer>();
		int temp;
		for (int i = 0; i < list.size(); i++) { // �Բ�ѯ������д���
			Object[] o = (Object[]) list.get(i);
			temp = (Integer) o[0];
			type.add(temp);
			num[temp] = ((BigInteger) o[1]).intValue();
		}
		int count = 0;
		int allscore = 0;
		int itemScore[] = paper.getItemScoreArr();
		for (int i = 0; i < num.length; i++) {
			count += num[i];
			allscore += num[i] * itemScore[i];
		}
		paper.setAllNumber(count); // �����ܵ�����
		paper.setAllScore(allscore); // �����ܷ�
		paper.setItemNumber(StringUtil.arr2Str(num)); // �����Ծ�ĸ����������������
		paper.setItemType(StringUtil.arr2Str(type.toArray())); // /�����Ծ����������
	}

	@Override
	public void saveOrUpdateQuestion(Questions q) {
		questionsDao.saveOrUpdateEntity(q);
	}

	@Override
	public void savIntelligentPaper(Paper model,
			ArrayList<TemplateForm> templateForms) {
		paperdao.saveEntity(model);// �ȱ����Ծ�
		this.intelligenceAddPaperQuestion(model.getId(), templateForms); //���Ծ����������
	}

	@Override
	public void intelligenceAddPaperQuestion(String id,
			ArrayList<TemplateForm> templateForms) {
		ArrayList<String> qids = new ArrayList<String>();
		Random random = new Random(); // ���������
		String ids[] = null;
		String qid = null;
		long num;
		for (TemplateForm tf : templateForms) {
			for (Entry<QuestionType, Long> entry : tf.getNumMap().entrySet()) {
				ids = findQuestionsBankByType(tf.getId(), entry.getKey()
						.ordinal()); // �������е���������
				num = entry.getValue(); // ���������
				int length = ids.length;
				// �������
				if (num >= length) { // ��Ӹ�ģ���ȫ������ʱ
					qids.addAll(Arrays.asList(ids));
				} else {
					for (int i = 0; i < num; i++) {
						int temp = random.nextInt(length);
						qid = ids[temp];
						qids.add(qid);
						ids[temp] = ids[length - 1];
						length--;
					}
				}
			}
		}
		addPaperQuestion(id, qids.toArray()); // ���Ծ����������

	}

	private String[] findQuestionsBankByType(String tid, long type) {
		String sql = "select ID from tb_questions where isDelete=1 and questionsType=? and templateID=?";
		Object qids[] = questionsDao.executeSQLQuery(null, sql, type, tid)
				.toArray();
		return StringUtil.strSplit(StringUtil.arr2Str(qids), ",");
	}

	@Override
	public void addPaperQuestion(String pid, Object[] qids) {
		if (!ValidateUtil.isNull(qids)) {
			String sql = "delete from tb_paper_questions where paperID=? and questionsID in ("
					+ StringUtil.arr2SqlStr(qids) + ")";
			paperdao.executeSQL(sql, pid);
			sql = "insert into tb_paper_questions (paperID,questionsID) values ";
			for (Object qid : qids) {
				sql += "('" + pid + "','" + (String) qid + "'),";
			}
			paperdao.executeSQL(sql.substring(0, sql.length() - 1));
		}

	}

	@Override
	public Paper findFormalTestPaper() {
		String hql="from Paper  where isDelete=1 and formalTest=true";
		List<Paper> papers=paperdao.findEntityByHQL(hql);
		Paper paper=ValidateUtil.isNull(papers)?null:papers.get(0);
		if(paper!=null){
			for (Questions q : paper.getQuestions()) {
				q.getId();
			}
		}
		return paper;
	}
}
