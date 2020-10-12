package opensdk.entity;

import java.util.Date;

public class Student {

	private Integer id;
	private String name;
	private Integer politicsStatus = 1;
	private String nationality = "汉族";
	private String stuNo;
	private Byte gender = 1;
	private Integer department = 16;
	private String address;
	private String mobile;
	private Date registDate = new Date();
	private Date birthday;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setPoliticsStatus(Integer politicsStatus) {
		this.politicsStatus = politicsStatus;
	}

	public Integer getPoliticsStatus() {
		return this.politicsStatus;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public String getStuNo() {
		return this.stuNo;
	}

	public void setGender(Byte gender) {
		this.gender = gender;
	}

	public Byte getGender() {
		return this.gender;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}

	public Integer getDepartment() {
		return this.department;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public Date getRegistDate() {
		return this.registDate;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public String getGenderStr() {
		return gender == 1 ? "男" : "女";
	}
}
