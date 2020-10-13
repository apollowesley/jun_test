package org.coody.czone.web.blog.domain;

import org.coody.framework.jdbc.entity.DBModel;

@SuppressWarnings("serial")
public class VisitInfo extends DBModel{

	
	private String userId;
	private String dayCode;
	private Long visit;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDayCode() {
		return dayCode;
	}
	public void setDayCode(String dayCode) {
		this.dayCode = dayCode;
	}
	public Long getVisit() {
		return visit;
	}
	public void setVisit(Long visit) {
		this.visit = visit;
	}
	
	

}
