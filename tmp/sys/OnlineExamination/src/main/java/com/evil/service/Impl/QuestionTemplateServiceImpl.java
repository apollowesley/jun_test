package com.evil.service.Impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.evil.dao.BaseDao;
import com.evil.pojo.Questions;
import com.evil.pojo.assist.QuestionTemplate;
import com.evil.service.QuestionTemplateService;
import com.evil.util.QuestionsUtil;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Service("questionTemplateService")
public class QuestionTemplateServiceImpl extends
		BaseServiceImpl<QuestionTemplate> implements QuestionTemplateService {

	@Override
	@Resource(name = "questionTemplateDao")
	public void setBaseDao(BaseDao<QuestionTemplate> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public List<QuestionTemplate> findAllTemplates(boolean isTypeNum) {
		String hql = "from QuestionTemplate where parent=null";
		List<QuestionTemplate> questions = this.findEntityByHQL(hql);
		for (QuestionTemplate q : questions) {
			recursionTemplates(q, isTypeNum);
		}
		return questions;
	}

	private void recursionTemplates(QuestionTemplate q, boolean isTypeNum) {
		if (isTypeNum) {
			setQuestionTypeNum(q);
		}
		if (!ValidateUtil.isNull(q.getChildrens())) {
			for (QuestionTemplate qt : q.getChildrens()) {
				recursionTemplates(qt, isTypeNum);
			}
		}
	}

	/**
	 * ����ģ��Ĳ�ͬ�������������
	 */
	@SuppressWarnings("rawtypes")
	private void setQuestionTypeNum(QuestionTemplate q) {
		String sql = "select q.questionsType,count(*) from tb_questions q where templateID=? GROUP BY q.questionsType ORDER BY q.questionsType ASC";
		List list = this.executeSQLQuery(null, sql, q.getId());
		Integer temp;
		for (int i = 0; i < list.size(); i++) { // �Բ�ѯ������д���
			Object[] o = (Object[]) list.get(i);
			temp = (Integer) o[0];
			q.getQuestionTypeNum()[temp] = ((BigInteger) o[1]).intValue();
		}
	}

	@Override
	public List<String> findTemplateChildrensByid(String id) {
		String sql = "select id from tb_questionTemplate where parentID=?";
		Object o[] = this.executeSQLQuery(null, sql, id).toArray();
		List<String> tids = new ArrayList<String>();
		String childrens[] = StringUtil.strSplit(
				StringUtil.ObjectArrayToString(o), ",");// ��ò�ѯ���
		if (!ValidateUtil.isNull(childrens)) { // �ݹ��ѯ���к��ӵ�id
			tids.addAll(Arrays.asList(childrens));
			for (String s : childrens) {
				tids.addAll(findTemplateChildrensByid(s));
			}
		}
		return tids;
	}

	@Override
	public void saveOrUpdateTemplate(QuestionTemplate model, String pid) {
		if (model.getId() != null) { // �޸�
			QuestionTemplate q = this.getEntity(model.getId());
			q.setName(model.getName());
			q.setIntroduce(model.getIntroduce());

		} else { // ���
			this.saveEntity(model);
			if (!ValidateUtil.isNull(pid)) {
				QuestionTemplate q = this.getEntity(pid);
				model.setParent(q);
				Set<Questions> questions = q.getQuestions();
				if (!ValidateUtil.isNull(questions)) {
					String sql = "update tb_questions set templateID=? where templateID=?";
					this.executeSQL(sql, model.getId(), pid);
				}
			}
		}

	}

	@Override
	public void deleteQuestionTemplate(String id) {
		List<String> childrens = this.findTemplateChildrensByid(id);// ��ø�ģ������е���ģ��
		childrens.add(id);
		String ids = StringUtil.arr2SqlStr(childrens.toArray());
		String sql="select ID from tb_questions where templateID in ("+ids+")";
		String qids=StringUtil.arr2SqlStr(this.executeSQLQuery(null, sql).toArray());
		// ��ɾ���𰸱����������
		 sql = "delete from tb_answers where questionid in (" + qids + ")";
		this.executeSQL(sql);
		// ��ɾ���������������
		sql = "delete from tb_wrongquestions where questionsID in ("+ qids + ")";
		this.executeSQL(sql);
		// ��ɾ���Ծ�����������
		sql = "delete from tb_paper_questions where questionsID in (" + qids + ")";
		this.executeSQL(sql);
		 sql = "update tb_questions set isDelete=0 where templateID in ("+ ids + ")"; //ɾ��ģ���µ�����
		this.executeSQL(sql);
		String hql = "update QuestionTemplate set parent.id =null where id in ("+ ids + ")"; // �޸ĸ��ڵ��idΪ��
		this.batchEntityByHQL(hql);
		hql = "delete from QuestionTemplate where id in (" + ids + ")"; // ��ģ������е���ģ��
		this.batchEntityByHQL(hql);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public long[] findAllQuestionTypeNum() {
		long all[] = new long[QuestionsUtil.typeNum];
		String sql = "select q.questionsType,count(*) from tb_questions q GROUP BY q.questionsType ORDER BY q.questionsType ASC";
		List list = this.executeSQLQuery(null, sql);
		Integer temp;
		for (int i = 0; i < list.size(); i++) { // �Բ�ѯ������д���
			Object[] o = (Object[]) list.get(i);
			temp = (Integer) o[0];
			all[temp] = ((BigInteger) o[1]).intValue();
		}
		return all;
	}

	@Override
	public long[] findQuestionTypeByTemplateId(String id) {
		QuestionTemplate q =new QuestionTemplate();
		q.setId(id);
		if (q != null) {
			setQuestionTypeNum(q);
		}
		return q.getQuestionTypeNum();
	}
}
