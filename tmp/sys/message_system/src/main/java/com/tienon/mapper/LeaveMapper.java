package com.tienon.mapper;

import java.util.List;

import com.tienon.pojo.Message;

public interface LeaveMapper {

	Integer regist(String username, String password);

	Integer login(String username, String password);

	Integer leave(String username, String topic, String message);

	Integer check(String username);

	List<Message> queryAll(Integer page);

	Integer queryCountAll();

	void del(int id);

	void copy(int id);

}
