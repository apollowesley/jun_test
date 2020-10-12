package com.tentcoo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class IdEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@GenericGenerator(name = "tableGenerator", strategy = "uuid")
	@Column(name = "f_id", nullable = false, length = 32, unique = true)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

