package com.evil.struts2.frontendAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.ParameterAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.Answer;
import com.evil.pojo.Paper;
import com.evil.pojo.Questions;
import com.evil.pojo.User;
import com.evil.pojo.UserPaper;
import com.evil.service.GradeService;
import com.evil.service.PaperService;
import com.evil.struts2.UserAware;
import com.evil.struts2.action.BaseAction;
import com.evil.util.JsonUtil;
import com.evil.util.StringUtil;

@Component("EngagePaperAction")
@Scope("prototype")
public class EngagePaperAction  extends BaseAction<Paper> implements UserAware,
ParameterAware {
	private static final long serialVersionUID = -4732291286191791364L;

	private List<Paper> paperList; // 保存试卷列表

	private List<Questions> questionList; // 保存题目列表

	private Map<String, String[]> paramsMap;// 接受所有的参数
	private String pid;
	private User user;// 接受User对象
	private String bingTime;  //开始时间
	
	
	@Resource(name = "PaperService")
	private PaperService paperservice;
	@Resource
	private GradeService gradeService;
	
	/**
	 * 注入ParameterAware
	 */
	@Override
	public void setParameters(Map<String, String[]> arg0) {
		this.paramsMap=arg0;
	}
	
	/**
	 *注入user
	 */
	@Override
	public void setUser(User user) {
		this.user=user;
	}

	public List<Paper> getPaperList() {
		return paperList;
	}

	public void setPaperList(List<Paper> paperList) {
		this.paperList = paperList;
	}

	public List<Questions> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Questions> questionList) {
		this.questionList = questionList;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getBingTime() {
		return bingTime;
	}

	public void setBingTime(String bingTime) {
		this.bingTime = bingTime;
	}

	/**
	 * 对试卷进行处理
	 */
	public void doEngagePaper() {
		System.out.println(bingTime);
		List<Answer> list = processAnswers();   //接受的提交的答案 
		UserPaper userPaper = new UserPaper();
		Paper paper = paperservice.getPaperWithChildren(pid, false);  //获得当前试卷
		int allscore[] = paperservice.calculateScore(paper, list,user);  //计算得分
		//保存成绩
		userPaper.setIsDelete("1");
		userPaper.setPaper(paper);
		userPaper.setScore(StringUtil.arr2Str(allscore));
		userPaper.setAllscore(allscore[allscore.length-1]);
		userPaper.setUser(user);
		userPaper.setStartTime(StringUtil.constructionTime(bingTime));
		gradeService.saveUserPaper(userPaper,list);
		//将得分信息传入前台
		String scores[]=StringUtil.strSplit(userPaper.getScore(), ",");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("scores", scores);
		dataMap.put("gid", userPaper.getId());
		JsonUtil.writeJsonData(dataMap);
	}
	
	/**
	 * 对答案进行处理
	 */
	private List<Answer> processAnswers() {
		String key = "";
		String values[] = null;
		Answer answer = null;
		List<Answer> list = new ArrayList<Answer>();  //所答案的集合
		for (Entry<String, String[]> entity : paramsMap.entrySet()) {   //遍历所有提交的参数
			key = entity.getKey();
			values = entity.getValue();
			if (key.startsWith("answer")){ //获得所有以answer开头的参数 （答案信息）
				answer = new Answer();
				answer.setAnswers(StringUtil.arr2Str(values));
				answer.setQuestionId(key.substring(6));
				answer.setPaperid(pid);
				answer.setUserid(user.getId());
				list.add(answer);
			}
		}
		return list;
	}
	
}
