package com.tienon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienon.mapper.LeaveMapper;
import com.tienon.pojo.Message;

@Service
public class QueryAllServiceImpl implements QueryAllService {
	@Autowired
	private LeaveMapper mapper;

	@Override
	public Map<String, Object> selectAlldata(Integer currentpage) {
		Integer page = (currentpage - 1) * 10;
		List<Message> list = mapper.queryAll(page);
		int count = mapper.queryCountAll();
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("code", 0);
		result.put("msg", "");
		result.put("count", count);
		result.put("data", list);

		return result;
	}

}
