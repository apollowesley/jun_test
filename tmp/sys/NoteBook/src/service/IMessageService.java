package service;

import java.util.List;

import model.Message;
import model.Users;

public interface IMessageService extends IBaseService {
	/** ��ѯ���û���������Ϣ */
	public List<Message> showMessage(Users user);
}
