package com.tentcoo.service;

import java.util.List;

import com.tentcoo.entity.UserMessage;

public interface MessageService {

	
	public List<UserMessage> getAllMessageInfo();
	
	public List<UserMessage> getUserMessageInfo(String userNo);
	
	//public UserMessage getPerMessage(String userId);
	
	public boolean addMessageInfo(UserMessage message);
	
	
	
}
