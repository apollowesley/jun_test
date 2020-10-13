package com.xlz.gray.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xlz.commons.base.mapper.BaseMapper;
import com.xlz.commons.base.service.impl.BaseServiceImpl;
import com.xlz.gray.mapper.ConfigMapper;
import com.xlz.gray.model.Config;
import com.xlz.gray.service.ConfigService;

@Service
public class ConfigServiceImpl extends BaseServiceImpl<Config> implements ConfigService {

	@Autowired
	private ConfigMapper configMapper;
	@Override
	protected BaseMapper<Config> getDAO() {
		return configMapper;
	}
	
}
