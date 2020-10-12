package com.zyguo.tools.push;

import java.io.Serializable;

public class HuaweiPushResult implements Serializable {
	private static final long serialVersionUID = -5760176615469808581L;
	private String message;
	private int resultcode;
	private String requestID;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getResultcode() {
		return resultcode;
	}

	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	
	@Override
	public String toString(){
		return "msg=" + message + ",code=" + resultcode + ",requestId=" + requestID;
	}

}
