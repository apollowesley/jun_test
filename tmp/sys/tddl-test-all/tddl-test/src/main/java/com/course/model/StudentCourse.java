package com.course.model;

import java.util.Date;

/**
 * 选课表
 */
public class StudentCourse {

    /* 选课ID*/
    private Long scId;

    /* 课程ID*/
    private Long cId;

    /* 学生ID*/
    private Long sId;

    /* 成绩*/
    private double score;

    /* 考试时间*/
    private Date examDate;

    public Long getScId() {
        return scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }
}
