package com.evil.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.evil.pojo.Paper;
import com.evil.pojo.Questions;
import com.evil.pojo.User;
import com.evil.pojo.assist.QuestionTemplate;
import com.evil.pojo.statistics.QuestionStatisticsModel;
import com.evil.pojo.system.AdminUser;
import com.evil.pojo.system.TypeDictionary;
import com.evil.service.AdminUserService;
import com.evil.service.GradeService;
import com.evil.service.LogService;
import com.evil.service.PaperService;
import com.evil.service.QuestionBankService;
import com.evil.service.QuestionTemplateService;
import com.evil.service.RoleService;
import com.evil.service.StatisticalService;
import com.evil.service.TypeDictionaryService;
import com.evil.service.UserService;
import com.evil.util.MD5Util;
import com.evil.util.Page;
import com.evil.util.QuestionsUtil;
import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

public class test {
	private static ClassPathXmlApplicationContext ctx;
	private static UserService userservice;
	private static PaperService paperService;
	private static QuestionBankService questionBankService;
	private static LogService logService;
	private static TypeDictionaryService typeService;
	private static GradeService gradeService;
	private static StatisticalService statisticalService;
	private static QuestionTemplateService questionTemplateService;

	/**
	 * @param args
	 */
	public static void before() {
		ctx = new ClassPathXmlApplicationContext("applicationContext*.xml");
		userservice = (UserService) ctx.getBean("UserService");
		paperService = (PaperService) ctx.getBean("PaperService");
		questionBankService = (QuestionBankService) ctx
				.getBean("QuestionBankService");
		statisticalService=(StatisticalService) ctx.getBean("statisticalService");
		logService = (LogService) ctx.getBean("logService");
		gradeService = (GradeService) ctx.getBean("gradeService");
		typeService = (TypeDictionaryService) ctx
				.getBean("typeDictionaryService");
		questionTemplateService = (QuestionTemplateService) ctx
				.getBean("questionTemplateService");
		System.out.println("初始化");
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
//		before();
		System.out.println(MD5Util.encode("123456"));
//		 RoleService roleService=(RoleService) ctx.getBean("roleService");
//		 AdminUserService adminUserService=(AdminUserService) ctx.getBean("adminUserService");
//		roleService.findRolesNotInRange(adminUserService.getEntity("adasdas").getRoles());
/*		List<AdminUser> adminUsers=adminUserService.findAdminUserPage(new Page(), null, null).getList();
		for (AdminUser adminUser : adminUsers) {
			System.out.println(adminUser.getName());
		}*/
/*		List<QuestionStatisticsModel> questionModels=statisticalService.statisticalQuestionModel("ff8080814c2bf5f0014c2bf6f07d0001");
		for (QuestionStatisticsModel qm : questionModels) {
			System.out.print(qm.getQuestions().getId()+","+qm.getCount()+","+qm.getRightCount()+",");
			for (int i = 0; i < qm.getOptionsCount().length; i++) {
				System.out.print(qm.getOptionsCount()[i]+",");
			}
			System.out.println();
		}*/
		//questionBankService.ImportQuestionsDatabase(new File("questions.xls"));
		
		/*
		 * ArrayList<TemplateForm> templateForms=new ArrayList<TemplateForm>();
		 * TemplateForm t=new TemplateForm(); t.setId("111");
		 * templateForms.add(t); t=new TemplateForm(); t.setId("112");
		 * templateForms.add(t); t=new TemplateForm(); t.setId("112");
		 * templateForms=TemplateForm.ListDeduplication(templateForms);
		 * System.out
		 * .println(templateForms.size()+","+templateForms.get(0).getJudgeNum
		 * ());
		 */
		// System.out.println(questionTemplateService.getEntity(""));
		// System.out.println(questionTemplateService.getEntity(""));
		/*
		 * List<String> ids= questionTemplateService.findTemplateChildrensByid(
		 * "8a8181e44cd62aa7014cd62aaf110001"); for (String s : ids) {
		 * System.out.println(s); }
		 */
		/*
		 * List<QuestionTemplate>
		 * list=questionTemplateService.findAllTemplates(false); for
		 * (QuestionTemplate q : list) { System.out.println(Print(q, 0,""));; }
		 */
		// System.out.println(q.getChildrens());
		// questionTemplateService.getOrgById("8a8181e44cd62aa7014cd62aaf110001");
		// paperService.alterPaperDetailMess("ff8080814cb38be6014cb38d21960001");
		// paperService.deleteQuestion("ff8080814c2bf5f0014c2bf6f07d0001",
		// "ff8080814c2bf5f0014c2bf855730009");
		// paperService.Test();
		/*
		 * EnumMap<QuestionType, Long> numMap=new EnumMap<QuestionType,
		 * Long>(QuestionType.class); numMap.put(QuestionType.multiple,20l);
		 * Paper p=new Paper(); p.setId("111111111");
		 * paperService.savIntelligentPaper(p, numMap);
		 */

		// System.out.println(typeService.getMaxValueByType(0));
		// typeService.deleteClassifyByPid("aaa");
		// List<TypeDictionary> list =typeService.findDictionaryByType(0);
		// System.out.println(list.get(0).getDescribe());

		/*
		 * Log log= logService.getEntity("ff8080814c300a6a014c30d22dca000d");
		 * System.out.println(log.getContent()); // // Page page = new Page();
		 * // page.setNumPerPage(6); // page.setCurrentPage(1); // Map<String,
		 * Object> map = new HashMap<String, Object>(); // map.put("isDelete",
		 * "1"); // map.put("userId","4028810f4a3ec432014a3ec5c7ad0001"); //
		 * map.put("paper.paperType", 0); // PageResult pageResult =
		 * gradeService.findPageGrade(page, map); SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy-MM-dd"); String dateNowStr = sdf.format(new
		 * Date()); String startTime="10:30:20";
		 * startTime=dateNowStr+" "+startTime; sdf=new
		 * SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 * System.out.println(startTime);
		 * System.out.println(sdf.parse(startTime)); TypeDictionary type=new
		 * TypeDictionary(); type.setName("其他类型"); type.setType(0);
		 * typeService.saveEntity(type); Page page = new Page();
		 * page.setNumPerPage(10); page.setCurrentPage(1); Map<String, Object>
		 * map = new HashMap<String, Object>(); map.put("title", "%%");
		 * map.put("cloesd", null); map.put("paperType", 0); PageResult
		 * pageResult = paperService.findPagePaper(page,
		 * map,"id desc","title asc"); pageResult.getList(); // Paper //
		 * paper=paperService
		 * .getPaperWithChildren("4028810f4a3ec432014a3ec6c41f0002", // true);
		 * // System.out.println(paper.getMaxType());
		 * 
		 * Page page = new Page(); page.setCurrentPage(1);
		 * page.setNumPerPage(6); Map<String, Object> map = new HashMap<String,
		 * Object>(); map.put("questions.questionsType", 0); PageResult
		 * pageResult = paperService.findPageWrongs(page, map); for (Object w :
		 * pageResult.getList()) {
		 * System.out.println(((WrongQuestions)w).getQuestions().getAnswer()); }
		 * 
		 * 
		 * 
		 * Page page=new Page(); page.setNumPerPage(20); PageResult pageResult=
		 * paperService.findPagePaper(page, new HashMap<String, Object>());
		 * 
		 * //
		 * paperService.alterPaperDetailMess("4028810f4a3ec432014a3ec6c41f0002"
		 * ); // paperService.alterPaperDetailMess("qqq");
		 * 
		 * Paper
		 * p=paperService.getPaperWithChildren("ff8080814a2e0125014a2e0281c20001 "
		 * , true); Questions q=null; for (int i = 0; i < 100; i++) { q=new
		 * Questions(); q.setAnswer("error"); q.setQuestionsType(0);
		 * q.setTitle("这是第"+i+"题"); q.setIsDelete("1"); q.setPaper(p);
		 * paperService.saveOrUpdateQuestion(q); }
		 * 
		 * // Paper //
		 * paper=paperService.findById("4028810f4a3ec432014a3ec6c41f0002"); //
		 * paperService.saveOrUpdatePaper(paper);
		 */}

	/*
	 * 
	 * 递归打印出一颗树。
	 */
	private static String Print(QuestionTemplate o, int level, String mess) {
		String preLevel = "";
		for (int i = 0; i < level; i++) {
			preLevel += "----";
		}
		mess = o.getName() + o.getQuestionNum() + ","
				+ o.getQuestionTypeNum()[0];
		for (QuestionTemplate org : o.getChildrens()) {
			mess += Print(org, level + 1, mess);
		}
		return mess;

	}

}
