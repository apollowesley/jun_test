package com.xlz.engine.common.config;

import java.io.Serializable;

public class Cmd implements Serializable{

	private static final long serialVersionUID = 1L;
	private String cmd;
	private String applicationId;
	private Integer version;
	
	public Cmd() {
		super();
	}
	public Cmd(String cmd,String applicationId) {
		super();
		this.cmd = cmd;
		this.applicationId = applicationId;
	}
	
	public Cmd(String cmd, String applicationId, Integer version) {
		super();
		this.cmd = cmd;
		this.applicationId = applicationId;
		this.version = version;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "Cmd [cmd=" + cmd + ", applicationId=" + applicationId + ", version=" + version + "]";
	}
}
