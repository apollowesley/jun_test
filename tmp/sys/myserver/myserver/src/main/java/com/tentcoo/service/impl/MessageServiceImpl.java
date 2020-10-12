package com.tentcoo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tentcoo.dao.MessageDao;
import com.tentcoo.entity.UserMessage;
import com.tentcoo.service.MessageService;



@Transactional
@Service
public class MessageServiceImpl implements MessageService{
	@Autowired
	MessageDao messageDao;
	
	
	
	@Override
	public List<UserMessage> getAllMessageInfo() {
		// TODO Auto-generated method stub
		return messageDao.getAllMessage();
	}

	@Override
	public List<UserMessage> getUserMessageInfo(String userNo) {
		// TODO Auto-generated method stub
		return messageDao.getUserMessage(userNo);
	}

	@Override
	public boolean addMessageInfo(UserMessage message) {
		// TODO Auto-generated method stub
		return messageDao.addMessage(message);
	}

}
