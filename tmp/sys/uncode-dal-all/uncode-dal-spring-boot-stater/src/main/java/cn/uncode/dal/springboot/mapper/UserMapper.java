package cn.uncode.dal.springboot.mapper;

import org.springframework.stereotype.Repository;

import cn.uncode.dal.springboot.dto.User;

/**
 * Created by KevinBlandy on 2017/2/28 15:10
 */
@Repository
public interface UserMapper {
	
	User users();
	
	Integer create(User userEntity);
}
