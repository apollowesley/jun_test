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

	private List<Paper> paperList; // �����Ծ��б�

	private List<Questions> questionList; // ������Ŀ�б�

	private Map<String, String[]> paramsMap;// �������еĲ���
	private String pid;
	private User user;// ����User����
	private String bingTime;  //��ʼʱ��
	
	
	@Resource(name = "PaperService")
	private PaperService paperservice;
	@Resource
	private GradeService gradeService;
	
	/**
	 * ע��ParameterAware
	 */
	@Override
	public void setParameters(Map<String, String[]> arg0) {
		this.paramsMap=arg0;
	}
	
	/**
	 *ע��user
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
	 * ���Ծ���д���
	 */
	public void doEngagePaper() {
		System.out.println(bingTime);
		List<Answer> list = processAnswers();   //���ܵ��ύ�Ĵ� 
		UserPaper userPaper = new UserPaper();
		Paper paper = paperservice.getPaperWithChildren(pid, false);  //��õ�ǰ�Ծ�
		int allscore[] = paperservice.calculateScore(paper, list,user);  //����÷�
		//����ɼ�
		userPaper.setIsDelete("1");
		userPaper.setPaper(paper);
		userPaper.setScore(StringUtil.arr2Str(allscore));
		userPaper.setAllscore(allscore[allscore.length-1]);
		userPaper.setUser(user);
		userPaper.setStartTime(StringUtil.constructionTime(bingTime));
		gradeService.saveUserPaper(userPaper,list);
		//���÷���Ϣ����ǰ̨
		String scores[]=StringUtil.strSplit(userPaper.getScore(), ",");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("scores", scores);
		dataMap.put("gid", userPaper.getId());
		JsonUtil.writeJsonData(dataMap);
	}
	
	/**
	 * �Դ𰸽��д���
	 */
	private List<Answer> processAnswers() {
		String key = "";
		String values[] = null;
		Answer answer = null;
		List<Answer> list = new ArrayList<Answer>();  //���𰸵ļ���
		for (Entry<String, String[]> entity : paramsMap.entrySet()) {   //���������ύ�Ĳ���
			key = entity.getKey();
			values = entity.getValue();
			if (key.startsWith("answer")){ //���������answer��ͷ�Ĳ��� ������Ϣ��
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
