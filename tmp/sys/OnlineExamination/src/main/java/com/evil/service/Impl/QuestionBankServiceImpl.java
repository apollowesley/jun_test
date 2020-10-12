package com.evil.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.evil.dao.AnswerDao;
import com.evil.dao.BaseDao;
import com.evil.dao.WrongQuestionsDao;
import com.evil.pojo.Questions;
import com.evil.pojo.assist.QuestionTemplate;
import com.evil.service.QuestionBankService;
import com.evil.util.Page;
import com.evil.util.PageResult;
import com.evil.util.PageUtil;
import com.evil.util.QuestionsUtil;
import com.evil.util.QuestionsUtil.QuestionType;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

@Service("QuestionBankService")
public class QuestionBankServiceImpl extends BaseServiceImpl<Questions>
		implements QuestionBankService {
	@Override
	@Resource(name = "questionsDao")
	public void setBaseDao(BaseDao<Questions> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource
	private AnswerDao answerDao;
	@Resource
	private WrongQuestionsDao wrongQuestionsDao;
	@Resource
	private BaseDao<QuestionTemplate> questionTemplateDao;

	@Override
	public PageResult findPageQuestions(Page page, Map<String, Object> map,
			String... sortfields) {
		String hql = "select count(*) from Questions  where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		Long count = null;
		count = (Long) this.uniqueResult(hql);
		count = count == null ? 0 : count;
		page = PageUtil.createPage(page.getNumPerPage(), count,
				page.getCurrentPage());// 根据总记录数创建分页信息
		hql = "from Questions where 1=1";
		hql = accordingParamHql(map, hql, sortfields);
		List<Questions> list = this.findEntityByPage(hql,
				page.getCurrentPage(), page.getNumPerPage());// 通过分页信息取得试题
		PageResult result = new PageResult(page, list);// 封装分页信息和记录信息，返回给调用处
		return result;
	}

	@Override
	public Questions findById(String sid) {
		return this.getEntity(sid);
	}

	@Override
	public String accordingParamHql(Map<String, Object> map, String hql,
			String... sortfields) {
		if (map != null && !map.isEmpty()) {
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					if (entry.getKey().equals("title")) {
						hql += " and title like '" + entry.getValue() + "'";
					} else if ("tid".equals(entry.getKey())) {
						hql += " and questionTemplate.id='" + entry.getValue()
								+ "'";
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
	public void batchDeleteQuestion(String ids[]) {
		// 先删除答案表中相关数据
		String hql = "delete from Answer where questionsid in ("
				+ StringUtil.arr2SqlStr(ids) + ")";
		answerDao.batchEntityByHQL(hql);
		// 再删除错题表的相关数据
		hql = "delete from WrongQuestions where questions.id in ("
				+ StringUtil.arr2SqlStr(ids) + ")";
		wrongQuestionsDao.batchEntityByHQL(hql);
		// 在删除试卷里的问题关联
		String sql = "delete from tb_paper_questions where questionsID in ("
				+ StringUtil.arr2SqlStr(ids) + ")";
		this.executeSQL(sql);
		// 最后删除问题
		hql = "update Questions set isDelete=0 where id in ("
				+ StringUtil.arr2SqlStr(ids) + ")";
		this.batchEntityByHQL(hql);
	}

	@Override
	public long getQuestionTypeNum(int type) {
		String hql = "select count(*) from Questions where isDelete=1 and questionsType=?";
		Long num = (Long) this.uniqueResult(hql, type);
		return num == null ? 0 : num;
	}

	@Override
	public List<Questions> findQuestionsBankByType(int type) {
		String hql = "from Questions where isDelete=1 and questionsType=?";
		return this.findEntityByHQL(hql, type);
	}

	@Override
	public String[] findPaperIdByQuestionsIds(String[] ids) {
		String sql = "select paperID from tb_paper_questions where questionsID in ("
				+ StringUtil.arr2SqlStr(ids) + ")";
		Object[] pids = this.executeSQLQuery(null, sql).toArray();
		return (String[]) StringUtil.strSplit(StringUtil.arr2Str(pids), ",");
	}

	@Override
	public void moveQuestions(String[] ids, String tid) {
		String hql = "update Questions set questionTemplate.id=? where id in ("
				+ StringUtil.arr2SqlStr(ids) + ")";
		this.batchEntityByHQL(hql, tid);
	}

	@Override
	public List<Questions> findAllQuestions() {
		String hql = "from Questions where isDelete=1";
		return this.findEntityByHQL(hql);
	}

	@Override
	public void clearQuestionBnakAndTemplate() {
		// 先删除答案表中相关数据
		String sql = "delete from tb_answers";
		this.executeSQL(sql);
		// 再删除错题表的相关数据
		sql = "delete from tb_wrongquestions";
		this.executeSQL(sql);
		// 在删除试卷里的问题关联
		sql = "delete from tb_paper_questions";
		this.executeSQL(sql);
		// 修改试卷的试题信息
		sql = "update tb_paper set itemType='0,0,0',itemNumber='0,0,0',itemScore='0,0,0',allNumber=0,allScore=0";
		this.executeSQL(sql);
		// 删除试题
		String hql = "delete from Questions";
		this.batchEntityByHQL(hql);
		sql = "update tb_questionTemplate set parentID =null "; // 修改父节点的id为空
		this.executeSQL(sql);
		// 删除试题模板
		sql = "delete from tb_questionTemplate";
		this.executeSQL(sql);
	}

	@Override
	public void ImportQuestionsDatabase(File questionsExcel,String fileName) throws Exception {
		if (questionsExcel != null) {
			if (fileName.contains(".")) {
				String postfix = fileName.substring(fileName.indexOf(".") + 1);
				if (!"".equals(postfix)) {
					if ("xls".equals(postfix)) {
						ImportQuestionsByXls(questionsExcel);
					} else if ("xlsx".equals(postfix)) {
						ImportQuestionsByXlsx(questionsExcel);
					}
				} else {
					throw new Exception("文件类型不对");
				}
			} else {
				throw new Exception("文件类型不对");
			}
		} else {
			throw new Exception("文件不能为空");
		}
	}

	private void ImportQuestionsByXlsx(File questionsExcel) throws IOException {
		InputStream is = new FileInputStream(questionsExcel);
//		 XSS
	}

	private void ImportQuestionsByXls(File questionsExcel) throws IOException {
		InputStream is = new FileInputStream(questionsExcel);
		HSSFWorkbook hwb = new HSSFWorkbook(is);
		if (ValidateUtil.isQuestionExcel(hwb)) {
			HSSFSheet sheet = null;
			Questions question = null;
			QuestionTemplate template = null;
			HSSFRow row = null;
			for (int i = 0; i < hwb.getNumberOfSheets(); i++) {
				sheet = hwb.getSheetAt(i);
				if (sheet == null) {
					continue;
				}
				for (int j = 1; j <=sheet.getLastRowNum(); j++) {
					row = sheet.getRow(j);
					if (row != null) {
						template = new QuestionTemplate();
						question = new Questions();
						question.setIsDelete("1");
						template.setName(QuestionsUtil.getHSSFCellValue(row.getCell(1)));
						question.setTitle(QuestionsUtil.getHSSFCellValue(row.getCell(2)));
						question.setAnswer(QuestionsUtil.getHSSFCellValue(row.getCell(3)));
						if("单选题".equals(sheet.getSheetName())||"多选题".equals(sheet.getSheetName())){
							question.setOptionA(QuestionsUtil.getHSSFCellValue(row.getCell(4)));
							question.setOptionB(QuestionsUtil.getHSSFCellValue(row.getCell(5)));
							question.setOptionC(QuestionsUtil.getHSSFCellValue(row.getCell(6)));
							question.setOptionD(QuestionsUtil.getHSSFCellValue(row.getCell(7)));
							question.setContent(QuestionsUtil.getHSSFCellValue(row.getCell(8)));
							if("单选题".equals(sheet.getSheetName())){
								question.setQuestionsType(QuestionType.single.ordinal());
							}else{
								question.setQuestionsType(QuestionType.multiple.ordinal());
							}
						}else{
							question.setContent(QuestionsUtil.getHSSFCellValue(row.getCell(4)));
							question.setQuestionsType(QuestionType.judge.ordinal());
						}
						if(checkQuestionTemplate(template)==null){
							questionTemplateDao.saveEntity(template);
						}else{
							template=checkQuestionTemplate(template);
						}
						
						question.setQuestionTemplate(template);
						this.saveEntity(question);
					}
				}
			}

		}
	}
	
	private QuestionTemplate checkQuestionTemplate(QuestionTemplate template){
		String hql="from QuestionTemplate where name=?";
		List<QuestionTemplate> templates=questionTemplateDao.findEntityByHQL(hql, template.getName());
		return templates.isEmpty()?null:templates.get(0);
	}

}
