package org.beetl.json.performance;

import org.beetl.json.annotation.Json;
import org.beetl.json.annotation.JsonPolicy;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//beetl
@Json(
	    policys={
	            @JsonPolicy(location="id", action="i")	                          
	    }
	)
//jackson
@JsonIgnoreProperties(value = { "id" })
public class AnnotationModel {
	//fastjson
	@JSONField(serialize = false)
	private int id = 1;
	private String name= "a" ;
	boolean old = true ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOld() {
		return old;
	}
	public void setOld(boolean old) {
		this.old = old;
	}
	
	
}
