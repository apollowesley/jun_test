package com.tentcoo.dao;

import java.util.List;

import com.tentcoo.entity.UserMessage;

public interface MessageDao {

	public List<UserMessage> getAllMessage();
	
	public List<UserMessage> getUserMessage(String userNo);
	
	//public UserMessage getPerMessage(String userId);
	
	public boolean addMessage(UserMessage message);
	
	
}
