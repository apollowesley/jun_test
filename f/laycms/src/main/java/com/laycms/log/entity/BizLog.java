/**
 * 
 */
package com.laycms.log.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.laycms.base.entity.BaseEntity;

/**
 * @author <p>
 *         Innate Solitary 于 2012-8-11 下午6:23:23
 *         </p>
 * 
 */
@Entity
@Table(name="bizlog")
public class BizLog extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer logId;
	private Integer userId;
	private String userName;
	private String remoteIp;
	private Integer entityId;
	@Enumerated(EnumType.STRING)
	private LogOperationEnum operation;
	@Enumerated(EnumType.STRING)
	private LogStatusEnum status;
	private String description;
	
	private Date createTime;
	
	public BizLog() {
    }
	
	
	public BizLog(Integer userId, String userName, String remoteIp, Integer entityId, String description,LogOperationEnum operation, LogStatusEnum status) {
	    super();
	    this.userId = userId;
	    this.userName = userName;
	    this.remoteIp = remoteIp;
	    this.entityId = entityId;
	    this.setDescription(description);
	    this.operation = operation;
	    this.status = status;
    }

	


	public BizLog(Integer logId, Integer userId, String userName, String remoteIp, Integer entityId,
			LogOperationEnum operation, LogStatusEnum status, String description) {
		super();
		this.logId = logId;
		this.userId = userId;
		this.userName = userName;
		this.remoteIp = remoteIp;
		this.entityId = entityId;
		this.operation = operation;
		this.status = status;
		this.description = description;
	}


	public Integer getLogId() {
		return logId;
	}


	public void setLogId(Integer logId) {
		this.logId = logId;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getRemoteIp() {
		return remoteIp;
	}


	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}


	public Integer getEntityId() {
		return entityId;
	}


	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}


	public LogOperationEnum getOperation() {
		return operation;
	}


	public void setOperation(LogOperationEnum operation) {
		this.operation = operation;
	}


	public LogStatusEnum getStatus() {
		return status;
	}


	public void setStatus(LogStatusEnum status) {
		this.status = status;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
