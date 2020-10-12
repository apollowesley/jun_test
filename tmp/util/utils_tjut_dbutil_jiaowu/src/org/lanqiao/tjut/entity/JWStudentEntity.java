package org.lanqiao.tjut.entity;

public class JWStudentEntity {
	private Integer s_id;
	private int s_num;
	private int s_code;
	private String s_name;
	private String s_sex;
	private int s_age;
	private int s_pho;
	private String s_adress;
	private int s_con;
	public Integer getS_id() {
		return s_id;
	}
	public void setS_id(Integer s_id) {
		this.s_id = s_id;
	}
	public int getS_num() {
		return s_num;
	}
	public void setS_num(int s_num) {
		this.s_num = s_num;
	}
	public int getS_code() {
		return s_code;
	}
	public void setS_code(int s_code) {
		this.s_code = s_code;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getS_sex() {
		return s_sex;
	}
	public void setS_sex(String s_sex) {
		this.s_sex = s_sex;
	}
	public int getS_age() {
		return s_age;
	}
	public void setS_age(int s_age) {
		this.s_age = s_age;
	}
	public int getS_pho() {
		return s_pho;
	}
	public void setS_pho(int s_pho) {
		this.s_pho = s_pho;
	}
	public String getS_adress() {
		return s_adress;
	}
	public void setS_adress(String s_adress) {
		this.s_adress = s_adress;
	}
	public int getS_con() {
		return s_con;
	}
	public void setS_con(int s_con) {
		this.s_con = s_con;
	}
	@Override
	public String toString() {
		return "JWStudentEntity [s_id=" + s_id + ", s_num=" + s_num + ", s_code=" + s_code + ", s_name=" + s_name
				+ ", s_sex=" + s_sex + ", s_age=" + s_age + ", s_pho=" + s_pho + ", s_adress=" + s_adress + ", s_con="
				+ s_con + "]";
	}
	
}
