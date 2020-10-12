package com.evil.pojo;

// default package

import java.util.Date;

import com.evil.util.StringUtil;
import com.evil.util.ValidateUtil;

/**
 * UserPaper entity. @author MyEclipse Persistence Tools
 */

public class UserPaper extends BaseEntity {
	private static final long serialVersionUID = -7532323989195091878L;

	private String id;

	// 完成时间不可修改
	private Date finishTime = new Date();
	private Date startTime=new Date();  //试卷开始考试的时间
	private String score;  //各个选下的成绩
	private String scoreArr[];
	private int  allscore;  // 总分
	private Double accuracy;
	private String isDelete;

	// 从UserPer到Paper的多对一映射
	private Paper paper;
	// 从UserPaper到User的多对一映射
	private User user;

	// Constructors

	/** default constructor */
	public UserPaper() {
	}

	public Date getFinishTime() {
		java.text.DateFormat format2 = new java.text.SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		format2.format(new Date());
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		if (!ValidateUtil.isNull(score)) {
			scoreArr = StringUtil.strSplit(score, ",");
		}
		this.score = score;
	}

	public Double getAccuracy() {
		return this.accuracy;
	}

	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}

	public String getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String[] getScoreArr() {
		return scoreArr;
	}

	public void setScoreArr(String[] scoreArr) {
		this.scoreArr = scoreArr;
	}

	public int getAllscore() {
		return allscore;
	}

	public void setAllscore(int allscore) {
		this.allscore = allscore;
	}
	
	

}