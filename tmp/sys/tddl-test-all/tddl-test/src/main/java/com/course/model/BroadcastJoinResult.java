package com.course.model;

/**
 * Created by justwe on 2016/1/24.
 */
public class BroadcastJoinResult {

    /* 学生ID*/
    private Long sId;

    /* 姓名*/
    private String sName;

    /* 学校名*/
    private String school;

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
