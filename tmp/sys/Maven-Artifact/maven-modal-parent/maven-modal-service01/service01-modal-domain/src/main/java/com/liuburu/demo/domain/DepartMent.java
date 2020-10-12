package com.liuburu.demo.domain;

/**
 * 部门
 *
 * @author liuburu
 * @create 2017/10/15
 **/
public class DepartMent {
    /**
     * 部门编号
     */
    private String deptNo;
    /**
     * 部门名称
     */
    private String dName;
    /**
     * 部门位置
     */
    private String loc;

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
}
