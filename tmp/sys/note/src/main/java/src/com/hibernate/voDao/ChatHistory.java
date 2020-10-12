package com.hibernate.voDao;

import java.util.Date;

/**
 * ChatHistory entity. @author MyEclipse Persistence Tools
 */

public class ChatHistory implements java.io.Serializable {

	// Fields

	private long id;
	private Integer uidSend;
	private Integer uidReceive;
	private String messageContent;
	private Date messageDatetime;
	private long messageTimestamp;
	private short messageStatus;
	private Integer messageType;
	private short displayDatetime;

	// Constructors

	/** default constructor */
	public ChatHistory() {
	}

	/** minimal constructor */
	public ChatHistory(Integer uidSend, Integer uidReceive, String messageContent, Date messageDatetime, short messageStatus, Integer messageType) {
		this.uidSend = uidSend;
		this.uidReceive = uidReceive;
		this.messageContent = messageContent;
		this.messageDatetime = messageDatetime;
		this.messageStatus = messageStatus;
		this.messageType = messageType;
	}

	/** full constructor */
	public ChatHistory(Integer uidSend, Integer uidReceive, String messageContent, Date messageDatetime, long messageTimestamp, short messageStatus, Integer messageType,
			short displayDatetime) {
		this.uidSend = uidSend;
		this.uidReceive = uidReceive;
		this.messageContent = messageContent;
		this.messageDatetime = messageDatetime;
		this.messageTimestamp = messageTimestamp;
		this.messageStatus = messageStatus;
		this.messageType = messageType;
		this.displayDatetime = displayDatetime;
	}

	// Property accessors

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getUidSend() {
		return this.uidSend;
	}

	public void setUidSend(Integer uidSend) {
		this.uidSend = uidSend;
	}

	public Integer getUidReceive() {
		return this.uidReceive;
	}

	public void setUidReceive(Integer uidReceive) {
		this.uidReceive = uidReceive;
	}

	public String getMessageContent() {
		return this.messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Date getMessageDatetime() {
		return this.messageDatetime;
	}

	public void setMessageDatetime(Date messageDatetime) {
		this.messageDatetime = messageDatetime;
	}

	public long getMessageTimestamp() {
		return this.messageTimestamp;
	}

	public void setMessageTimestamp(long messageTimestamp) {
		this.messageTimestamp = messageTimestamp;
	}

	public short getMessageStatus() {
		return this.messageStatus;
	}

	public void setMessageStatus(short messageStatus) {
		this.messageStatus = messageStatus;
	}

	public Integer getMessageType() {
		return this.messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public short getDisplayDatetime() {
		return this.displayDatetime;
	}

	public void setDisplayDatetime(short displayDatetime) {
		this.displayDatetime = displayDatetime;
	}

}