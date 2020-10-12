package com.evil.pojo;

import java.util.Date;


public class Exam extends BaseEntity {
	private static final long serialVersionUID = 6276025893158445925L;
	private String id;
	private String examName; //考试名字
	private int examTime;  //(考试时间以分钟为单位)
	private int joinNumber; //同一用户参加次数
	private int countdown;//倒计时
	private boolean autoSave; //自动保存
	private int autoSavaeInterval=20;//自动保存间隔（最小20秒）
	private boolean manualSave;//允许手动保存
	private boolean frontDeskShow; //是否在前台显示
	private boolean examParsing=true; //是否出现考试解析按钮
	private Date  startTime; //开放时间
	private Date endTime;  //结束时间 
	private boolean showTeamInstruction=true; //显示考试须知
	private String TeamInstruction; //考试须知
	private Date addTime=new Date();
	
	
	private ExamType examType; //与类型的多对一关联
	private Paper paper; //与试卷的多对一关联

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public int getExamTime() {
		return examTime;
	}

	public void setExamTime(int examTime) {
		this.examTime = examTime;
	}

	public int getJoinNumber() {
		return joinNumber;
	}

	public void setJoinNumber(int joinNumber) {
		this.joinNumber = joinNumber;
	}

	public int getCountdown() {
		return countdown;
	}

	public void setCountdown(int countdown) {
		this.countdown = countdown;
	}

	public boolean isAutoSave() {
		return autoSave;
	}

	public void setAutoSave(boolean autoSave) {
		this.autoSave = autoSave;
	}

	public int getAutoSavaeInterval() {
		return autoSavaeInterval;
	}

	public void setAutoSavaeInterval(int autoSavaeInterval) {
		this.autoSavaeInterval = autoSavaeInterval;
	}

	public boolean isManualSave() {
		return manualSave;
	}

	public void setManualSave(boolean manualSave) {
		this.manualSave = manualSave;
	}

	public boolean isExamParsing() {
		return examParsing;
	}

	public void setExamParsing(boolean examParsing) {
		this.examParsing = examParsing;
	}

	public boolean isFrontDeskShow() {
		return frontDeskShow;
	}

	public void setFrontDeskShow(boolean frontDeskShow) {
		this.frontDeskShow = frontDeskShow;
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

	public boolean isShowTeamInstruction() {
		return showTeamInstruction;
	}

	public void setShowTeamInstruction(boolean showTeamInstruction) {
		this.showTeamInstruction = showTeamInstruction;
	}

	public String getTeamInstruction() {
		return TeamInstruction;
	}

	public void setTeamInstruction(String teamInstruction) {
		TeamInstruction = teamInstruction;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public ExamType getExamType() {
		return examType;
	}

	public void setExamType(ExamType examType) {
		this.examType = examType;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	
	
	
	
	
	
	
	

}
