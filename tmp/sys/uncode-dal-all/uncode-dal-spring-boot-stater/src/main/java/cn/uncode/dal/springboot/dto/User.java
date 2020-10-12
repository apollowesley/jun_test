package cn.uncode.dal.springboot.dto;

import java.util.Date;

import cn.uncode.dal.annotation.Field;

/**
 * Created by KevinBlandy on 2017/2/28 15:08
 */
public class User {
	private Long id;
	private String name;
	private String tel;
	
	@Field(name = "create_time")
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
	
