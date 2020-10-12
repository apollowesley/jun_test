package org.frameworkset.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.frameworkset.orm.annotation.Column;
import com.frameworkset.orm.annotation.ESId;

import java.util.Date;

public class Demo extends ESBaseData{
	@ESId
	private long demoId;
	private String contentbody;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@Column(dataformat = "yyyy-MM-dd HH:mm:ss.SSS")
	private Date agentStarttime;
	private String applicationName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;

	public String getContentbody() {
		return contentbody;
	}

	public void setContentbody(String contentbody) {
		this.contentbody = contentbody;
	}

	public Date getAgentStarttime() {
		return agentStarttime;
	}

	public void setAgentStarttime(Date agentStarttime) {
		this.agentStarttime = agentStarttime;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public long getDemoId() {
		return demoId;
	}

	public void setDemoId(long demoId) {
		this.demoId = demoId;
	}
}
