package com.evil.pojo;

// default package

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

/**
 * Paper entity. @author MyEclipse Persistence Tools
 */

public class Paper extends BaseEntity {
	private static final long serialVersionUID = 503596610148759559L;
	// Fields

	private String id;
	private String title = "";
	private String itemType = "";
	private int itemTypeArr[]; // 问题类型数组
	private String itemNumber = "0,0,0,0";
	private int itemNumberArr[]; // 问题数量数组
	private String itemScore = "0,0,0,0";
	private int itemScoreArr[]; // 问题分值数组
	private Integer allNumber;  //问题总数量
	private Integer allScore;   //试卷总分数
	private Integer exanTime;  //试卷考试时间
	private boolean cloesd;	   //是否通过审核
	private String isDelete = "0";

	private int maxType;		//问题的类型数量
	private Integer paperType;     //试卷的类型
	private long gradeNum;        //成绩数
	private double maxScore;     //最高分
	
	private boolean formalTest;  //是否为正式考试试卷
	
	private boolean examParsing=true; //是否出现考试解析按钮
	private Date  startTime; //开放时间
	private Date endTime;  //结束时间 
	private Date addTime=new Date();
//	private boolean showTeamInstruction=true; //显示考试须知
//	private String TeamInstruction; //考试须知


	// 从Paper到Questions的一对多关联关系
	private transient Set<Questions> questions = new TreeSet<Questions>();

	// 从Paper到UserPaper的一对多关联关系
	private transient Set<UserPaper> userPapers = new HashSet<UserPaper>();

	// Constructors

	/** default constructor */
	public Paper() {
	}

	/** full constructor */
	public Paper(String title, String itemType, String itemNumber,
			String itemScore, Integer allNumber, Integer allScore,
			Integer exanTime, String isDelete, Set<Questions> questions) {
		this.title = title;
		this.itemType = itemType;
		this.itemNumber = itemNumber;
		this.itemScore = itemScore;
		this.allNumber = allNumber;
		this.allScore = allScore;
		this.exanTime = exanTime;
		this.isDelete = isDelete;
		this.questions = questions;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
		if (!ValidateUtil.isNull(itemType)) {
			String itemTypes[] = StringUtil.strSplit(itemType, ",");
			itemTypeArr = new int[itemTypes.length];
			if (!ValidateUtil.isNull(itemTypes)) {
				for (int i = 0; i < itemTypes.length; i++) {
					itemTypeArr[i] = Integer.parseInt(itemTypes[i]);
				}
			}
		}
	}

	public String getItemNumber() {
		return this.itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		if (!ValidateUtil.isNull(itemNumber)) {
			String itemNumbers[] = StringUtil.strSplit(itemNumber, ",");
			itemNumberArr = new int[itemNumbers.length];
			if (!ValidateUtil.isNull(itemNumbers)) {
				for (int i = 0; i < itemNumbers.length; i++) {
					itemNumberArr[i] = Integer.parseInt(itemNumbers[i]);
				}
			}
		}
		this.itemNumber = itemNumber;
	}

	public String getItemScore() {
		return this.itemScore;
	}

	public void setItemScore(String itemScore) {
		if (!ValidateUtil.isNull(itemScore)) {
			String itemScores[] = StringUtil.strSplit(itemScore, ",");
			itemScoreArr = new int[itemScores.length];
			if (!ValidateUtil.isNull(itemScores)) {
				for (int i = 0; i < itemScores.length; i++) {
					itemScoreArr[i] = Integer.parseInt(itemScores[i]);
				}
			}
		}
		this.itemScore = itemScore;
	}

	public Integer getAllNumber() {
		return this.allNumber;
	}

	public void setAllNumber(Integer allNumber) {
		this.allNumber = allNumber;
	}

	public Integer getAllScore() {
		return this.allScore;
	}

	public void setAllScore(Integer allScore) {
		this.allScore = allScore;
	}

	public Integer getExanTime() {
		return this.exanTime;
	}

	public void setExanTime(Integer exanTime) {
		this.exanTime = exanTime;
	}

	public String getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public Set<Questions> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Questions> questions) {
		this.questions = questions;
	}

	public Set<UserPaper> getUserPapers() {
		return userPapers;
	}

	public void setUserPapers(Set<UserPaper> userPapers) {
		this.userPapers = userPapers;
	}

	public int getMaxType() {
		return maxType;
	}

	public void setMaxType(Integer maxType) {
		this.maxType = maxType==null?0:maxType;
	}

	public boolean isCloesd() {
		return cloesd;
	}

	public void setCloesd(boolean cloesd) {
		this.cloesd = cloesd;
	}

	public int[] getItemTypeArr() {
		return itemTypeArr;
	}

	public int[] getItemNumberArr() {
		return itemNumberArr;
	}

	public int[] getItemScoreArr() {
		return itemScoreArr;
	}

	public void setItemTypeArr(int[] itemTypeArr) {
		this.itemTypeArr = itemTypeArr;
	}

	public void setItemNumberArr(int[] itemNumberArr) {
		this.itemNumberArr = itemNumberArr;
	}

	public void setItemScoreArr(int[] itemScoreArr) {
		this.itemScoreArr = itemScoreArr;
	}

	public Integer getPaperType() {
		return paperType;
	}

	public void setPaperType(Integer paperType) {
		this.paperType = paperType;
	}

	public long getGradeNum() {
		return gradeNum;
	}

	public void setGradeNum(Long gradeNum) {
		this.gradeNum = gradeNum==null?0:gradeNum;
	}

	public double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore==null?0:maxScore;
	}

	public boolean isFormalTest() {
		return formalTest;
	}

	public void setFormalTest(boolean formalTest) {
		this.formalTest = formalTest;
	}

	public boolean isExamParsing() {
		return examParsing;
	}

	public void setExamParsing(boolean examParsing) {
		this.examParsing = examParsing;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	
	
	


	
}