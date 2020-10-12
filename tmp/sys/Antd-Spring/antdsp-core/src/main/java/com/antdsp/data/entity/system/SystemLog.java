package com.antdsp.data.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.antdsp.data.entity.AbstractEntity;

/**
 * 
 * <p>title:SystemLog</p>
 * <p>Description: 系统操作日志表</p>
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * @author lijiantao
 * @date 2019年6月17日
 * @email a496401006@qq.com
 *
 */
@Table(name="tb_system_log")
@Entity
public class SystemLog extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 操作者Id
	 */
	@Column(name="optor_id", nullable=false)
	private Long optorId;
	
	/**
	 * 操作者名称
	 */
	@Column(name="optor_name", nullable=false, length=64)
	private String optorName;
	
	/**
	 * 操作者IP
	 */
	@Column(name="optor_ip", nullable=false, length=64)
	private String optorIp;
	
	/**
	 * 操作名称
	 */
	@Column(name="opt_name", nullable=false, length=64)
	private String optName;
	
	/**
	 * 请求uri
	 */
	@Column(name="opt_uri", nullable=false, length=128)
	private String optURI;
	
	/**
	 * 操作请求方式
	 */
	@Column(name="opt_type", nullable=false, length=16)
	private String optType;

	/**
	 * 操作详情
	 */
	@Column(name="opt_detail", nullable=false, length=1024)
	private String optDetail;
	
	/**
	 * 操作时间
	 */
	@Column(name="run_time", nullable=false)
	private long runTime;

	public Long getOptorId() {
		return optorId;
	}

	public void setOptorId(Long optorId) {
		this.optorId = optorId;
	}

	public String getOptorName() {
		return optorName;
	}

	public void setOptorName(String optorName) {
		this.optorName = optorName;
	}

	public String getOptorIp() {
		return optorIp;
	}

	public void setOptorIp(String optorIp) {
		this.optorIp = optorIp;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOptDetail() {
		return optDetail;
	}

	public void setOptDetail(String optDetail) {
		this.optDetail = optDetail;
	}

	public long getRunTime() {
		return runTime;
	}

	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}

	public String getOptURI() {
		return optURI;
	}

	public void setOptURI(String optURI) {
		this.optURI = optURI;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}
	
	
}
