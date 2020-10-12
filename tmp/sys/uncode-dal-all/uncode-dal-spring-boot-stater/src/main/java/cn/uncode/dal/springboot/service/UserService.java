package cn.uncode.dal.springboot.service;

import cn.uncode.dal.springboot.dto.User;

/**
 * Created by KevinBlandy on 2017/2/28 15:10
 */
public interface UserService {
	
	User users();
	
	Integer create(User userEntity);
	
	User get(int id);
}
