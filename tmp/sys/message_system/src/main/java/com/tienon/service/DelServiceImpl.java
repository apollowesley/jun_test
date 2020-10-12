package com.tienon.service;

import org.springframework.stereotype.Service;

import com.tienon.mapper.LeaveMapper;

@Service
public class DelServiceImpl implements DelService {
	private LeaveMapper mapper;
	
	@Override
	public void del(int id) {
		mapper.copy(id);
		mapper.del(id);
	}

}
