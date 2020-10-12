package com.course.model;

/**
 * 班级表
 */
public class SClass {

    /* 班级ID */
    private Long classId;

    /* 班级名称 */
    private String className;

    /* 班主任 */
    private String header;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
