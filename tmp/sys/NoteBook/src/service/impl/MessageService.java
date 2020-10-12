package service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import model.Message;
import model.Users;
import service.BaseService;
import service.IMessageService;
import dao.IMessageDAO;

//@Transactional
public class MessageService extends BaseService implements IMessageService {
	
	private IMessageDAO messageDAO;
	
	public IMessageDAO getMessageDAO() {
		return messageDAO;
	}

	public void setMessageDAO(IMessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	/** ��ѯ���û���������Ϣ */
	public List<Message> showMessage(Users user){
		return messageDAO.showMessage(user);
	}
	
}
