package com.xlz.gray.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xlz.commons.base.mapper.BaseMapper;
import com.xlz.commons.base.service.impl.BaseServiceImpl;
import com.xlz.gray.mapper.ApplicationCmdMapper;
import com.xlz.gray.model.Application;
import com.xlz.gray.model.ApplicationCmd;
import com.xlz.gray.service.ApplicationCmdService;

@Service
public class ApplicationCmdServiceImpl extends BaseServiceImpl<ApplicationCmd> implements ApplicationCmdService {

	@Autowired
	private ApplicationCmdMapper applicationCmdMapper;
	@Override
	protected BaseMapper<ApplicationCmd> getDAO() {
		return applicationCmdMapper;
	}
	
	@Override
	public void saveCmd(String cmd, Application application) {
		//获取当前应用的最大版本号
		Integer version = applicationCmdMapper.findMaxVersion(application.getApplicationId());
		
		ApplicationCmd t = new ApplicationCmd();
		t.setApplicationCode(application.getApplicationId());
		t.setGrayCmd(cmd);
		t.setCreateUser(application.getUpdateUser());
		t.setVersion(version == null ? 1: version+1);
		applicationCmdMapper.save(t );
	}
	
}
