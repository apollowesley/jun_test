package com.xlz.gray.service;

import java.util.List;

import com.xlz.commons.base.service.BaseService;
import com.xlz.gray.model.Setting;

public interface SettingService extends BaseService<Setting> {
	/**
	 * 是否包含引擎的参数
	 * @param containsSetting
	 * @return
	 */
	List<Setting> findListBySettingType(String settingType);
}

