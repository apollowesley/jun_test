package org.apache.center.service;

import org.apache.center.model.User;
import org.apache.playframework.service.BaseService;

public interface UserService extends BaseService<User> {
	
	public boolean save(User user);
}