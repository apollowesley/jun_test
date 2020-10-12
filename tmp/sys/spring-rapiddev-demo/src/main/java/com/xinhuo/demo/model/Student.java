package com.xinhuo.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.xinhuo.demo.model.Student;
import java.io.Serializable;


/**
 * 描述: 
 * author: 新林
 * date: 2019-06-16
 */
@TableName("pre_student")
public class Student implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    @TableId(value="sno", type= IdType.AUTO)
    private String sno;

    /**
     * 
     */
    private String sname;

    /**
     * 
     */
    private String ssex;

    /**
     * 
     */
    private Date sbirthday;

    /**
     * 
     */
    private String stuClass;

    public String getSno() {
	return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }
    public String getSname() {
	return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
    public String getSsex() {
	return ssex;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex;
    }
    public Date getSbirthday() {
	return sbirthday;
    }

    public void setSbirthday(Date sbirthday) {
        this.sbirthday = sbirthday;
    }
    public String getStuClass() {
	return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }
}