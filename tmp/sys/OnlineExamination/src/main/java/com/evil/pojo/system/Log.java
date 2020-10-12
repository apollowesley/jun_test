package com.evil.pojo.system;

import java.util.Date;

public class Log {

	private String id;
	private String operator;// 操作人
	private String operName;// 操作的名字
	private String operParams;// 操作参数
	private String content;// 操作的类容
	private String operResult;// 操作结果,成功,失败
	private String resultMsg;// 结果消息
	private Date operTime = new Date(); // 操作时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getOperParams() {
		return operParams;
	}
	public void setOperParams(String operParams) {
		this.operParams = operParams;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOperResult() {
		return operResult;
	}
	public void setOperResult(String operResult) {
		this.operResult = operResult;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	

}
