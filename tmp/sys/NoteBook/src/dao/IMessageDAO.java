package dao;

import java.util.List;

import model.Message;
import model.Users;

public interface IMessageDAO {
	/** ��ѯ���û���������Ϣ */
	public List<Message> showMessage(Users user);
}
