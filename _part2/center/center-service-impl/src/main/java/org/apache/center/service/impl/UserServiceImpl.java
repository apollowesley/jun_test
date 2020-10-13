package org.apache.center.service.impl;

import org.apache.center.common.exception.MessagePrompt;
import org.apache.center.dao.UserMapper;
import org.apache.center.model.User;
import org.apache.center.service.UserService;
import org.apache.center.service.exception.CenterMessagePromptException;
import org.apache.playframework.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Component  
@Service(timeout = 3000)
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

	@Override
	public boolean save(User user) {
		User selectUser = new User();
		selectUser.setUserName(user.getUserName());
		selectUser = selectOne(selectUser);
		//判断用户名是否存在，存在抛出用户存在异常
		if (selectUser != null) {
			logger.error(MessagePrompt.USER_NAME_REGISTERED.getMessage());
			throw new CenterMessagePromptException(MessagePrompt.USER_NAME_REGISTERED.getCode(), MessagePrompt.USER_NAME_REGISTERED.getMessage());
		} else {
			return insert(user);
		}
	}
}


