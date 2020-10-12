package org.durcframework.core;

import java.util.List;

public class DefaultMessageResult implements MessageResult {
	private static final long serialVersionUID = -2690874099333724778L;
	
	private boolean success = true;
	private String message;
	private List<String> messages;
	
	private Object attach;

	public static DefaultMessageResult success(String message) {
		DefaultMessageResult result = success();
		result.setMessage(message);
		return result;
	}

	public static DefaultMessageResult success() {
		DefaultMessageResult result = new DefaultMessageResult();
		result.setSuccess(true);
		return result;
	}

	public static DefaultMessageResult error(String errorMsg) {
		DefaultMessageResult result = new DefaultMessageResult();
		result.setSuccess(false);
		result.setMessage(errorMsg);
		return result;
	}

	public static DefaultMessageResult error(String errorMsg, List<String> errors) {
		DefaultMessageResult result = error(errorMsg);
		result.setMessages(errors);
		return result;
	}
	
	public void setError(String message) {
		this.success = false;
		this.setMessage(message);
	}

	public boolean getSuccess() {
		return success;
	}
	
	public boolean getIsError() {
		return !this.getSuccess();
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getMessages() {
		return messages;
	}

	public Object getAttach() {
		return attach;
	}

	@Override
	public void setAttach(Object attach) {
		this.attach = attach;
	}

}
