package durcframework.test.common;

import java.util.List;

import org.durcframework.core.MessageResult;

// 自定义的消息类
public class MyMessageHolder implements MessageResult {
	
	private boolean req_success;
	private String response_message;
	private String response_error;
	private List<String> other_errors;

	@Override
	public void setSuccess(boolean success) {
		this.req_success = success;
	}

	@Override
	public void setMessage(String message) {
		this.response_message = message;
	}

	@Override
	public void setMessages(List<String> messages) {
		other_errors = messages;
	}

	public boolean isReq_success() {
		return req_success;
	}

	public String getResponse_message() {
		return response_message;
	}

	public String getResponse_error() {
		return response_error;
	}

	public List<String> getOther_errors() {
		return other_errors;
	}
	
	

}
