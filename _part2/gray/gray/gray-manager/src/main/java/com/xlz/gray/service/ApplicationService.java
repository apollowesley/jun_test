package com.xlz.gray.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xlz.commons.base.service.BaseService;
import com.xlz.engine.common.result.Result;
import com.xlz.gray.model.Application;
import com.xlz.gray.model.Setting;

public interface ApplicationService extends BaseService<Application> {
	
	Object saveGrayConfig(Application entity,HttpServletRequest request) throws IOException;
	
	Object editGrayConfig(Application entity,String oldApplicationId,HttpServletRequest request) throws IOException;
	
	Result<Application> publishConfig(String applicationId,String engineType);

	/**
	 * 按照灰度引擎获取所有配置的灰度参数
	 * @return
	 */
	List<Setting> findGraySetting();
	
	/**
	 * 按照灰度引擎获取所有配置的灰度参数和设置值
	 * @return
	 */
	List<Setting> findGraySettingConfig(Long applicationId);
	
}

