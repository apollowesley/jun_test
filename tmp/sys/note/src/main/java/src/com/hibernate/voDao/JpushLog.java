package com.hibernate.voDao;

import java.util.Date;

/**
 * JpushLog entity. @author MyEclipse Persistence Tools
 */

public class JpushLog implements java.io.Serializable {

	// Fields

	private Integer id;
	private String sendJpush;
	private String responseCode;
	private String responseMessage;
	private short retryTimes;
	private short retryAgain;
	private Date retryNextTime;

	// Constructors

	/** default constructor */
	public JpushLog() {
	}

	/** minimal constructor */
	public JpushLog(Integer id, String sendJpush, String responseCode, String responseMessage, short retryTimes, short retryAgain) {
		this.id = id;
		this.sendJpush = sendJpush;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.retryTimes = retryTimes;
		this.retryAgain = retryAgain;
	}

	/** full constructor */
	public JpushLog(Integer id, String sendJpush, String responseCode, String responseMessage, short retryTimes, short retryAgain, Date retryNextTime) {
		this.id = id;
		this.sendJpush = sendJpush;
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
		this.retryTimes = retryTimes;
		this.retryAgain = retryAgain;
		this.retryNextTime = retryNextTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSendJpush() {
		return this.sendJpush;
	}

	public void setSendJpush(String sendJpush) {
		this.sendJpush = sendJpush;
	}

	public String getResponseCode() {
		return this.responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return this.responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public short getRetryTimes() {
		return this.retryTimes;
	}

	public void setRetryTimes(short retryTimes) {
		this.retryTimes = retryTimes;
	}

	public short getRetryAgain() {
		return this.retryAgain;
	}

	public void setRetryAgain(short retryAgain) {
		this.retryAgain = retryAgain;
	}

	public Date getRetryNextTime() {
		return this.retryNextTime;
	}

	public void setRetryNextTime(Date retryNextTime) {
		this.retryNextTime = retryNextTime;
	}

}