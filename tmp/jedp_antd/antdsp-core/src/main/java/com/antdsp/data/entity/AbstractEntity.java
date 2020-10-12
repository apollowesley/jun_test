package com.antdsp.data.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id" , nullable = false)
	private Long id;
	
	@Column(name="modifier" , nullable = false)
	private String modifier;
	
	@Column(name="creator" , nullable = false)
	private String creator;
	
	@Column(name="created" , nullable = false)
	private Timestamp created;
	
	@Column(name="modified" , nullable = false)
	private Timestamp modified;
	
	/**
	 * 在插入数据库之前设置 时间
	 */
	public void onPreInsert(){
		this.setCreated(now());
		this.setModified(now());
	}
	/**
	 * 在更新对象之前设置 时间
	 */
	public void onPreUpdate(){
		this.setModified(now());
	}
	
	private Timestamp now(){
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public Timestamp getModified() {
		return modified;
	}
	public void setModified(Timestamp modified) {
		this.modified = modified;
	}
}
