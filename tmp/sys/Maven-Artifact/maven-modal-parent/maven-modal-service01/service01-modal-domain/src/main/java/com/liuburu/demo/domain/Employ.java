package com.liuburu.demo.domain;

import java.util.Date;

/**
 * 测试01实体类
 *
 * @author liuburu
 * @create 2017/09/24
 **/
public class Employ {
    private String empno;   //  员工编号
    private String ename;   //  员工姓名
    private String job;     //  员工工作
    private String mgr;     //  经理
    private Date hireDate;  //  雇用日期
    private double sal;     //  薪水
    private float comm;     //  奖金
    private String deptno;  //  部门编号

    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMgr() {
        return mgr;
    }

    public void setMgr(String mgr) {
        this.mgr = mgr;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public float getComm() {
        return comm;
    }

    public void setComm(float comm) {
        this.comm = comm;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }
}
