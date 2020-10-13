package com.xlz.gray.service;

import com.xlz.commons.base.service.BaseService;
import com.xlz.gray.model.Application;
import com.xlz.gray.model.ApplicationCmd;

public interface ApplicationCmdService extends BaseService<ApplicationCmd> {

	void saveCmd(String cmd, Application application);
	
}

