package service;

import java.util.List;

import model.Message;
import model.Users;

public interface IMessageService extends IBaseService {
	/** 查询该用户的所有消息 */
	public List<Message> showMessage(Users user);
}
