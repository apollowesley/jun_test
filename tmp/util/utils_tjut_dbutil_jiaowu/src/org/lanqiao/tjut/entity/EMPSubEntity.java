package org.lanqiao.tjut.entity;
/**
 * 实体类的bean 
 * -- 实例类bean中的属性和数据表 emp_sub表中每个字段具有一一对应关系
 * 
 * @author Administrator
 *
 */
public class EMPSubEntity {
	/*
	 * 采用数据库字段名和实体类的属性名一致的原则进行对应
	 * 
	 * 要求：实体类的属性名命名规则：前两个字母小写
	 * 类型采用对应的类型
	 * 另外主键在数据中是 number类型，在实体类中推荐采用 Integer类型
	 *
	 * 
	 */
	// 主键
	private Integer empid;
	private String last_name;
	private Integer job_id;
	private double salary;
	
	
	public Integer getEmpid() {
		return empid;
	}
	public void setEmpid(Integer empid) {
		this.empid = empid;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public Integer getJob_id() {
		return job_id;
	}
	public void setJob_id(Integer job_id) {
		this.job_id = job_id;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "EMPSubBean [empid=" + empid + ", last_name=" + last_name 
				+ ", job_id=" + job_id + ", salary=" + salary
				+ "]";
	}
	
	
}
