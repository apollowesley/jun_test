package com.course.model;

/**
 * 课程model
 */
public class Course {

    /* 课程ID*/
    private Long cId;

    /* 课程名称*/
    private String cName;

    /* 授课教师*/
    private String teacher;

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
