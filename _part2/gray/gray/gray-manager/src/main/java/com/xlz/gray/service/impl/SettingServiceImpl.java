package com.xlz.gray.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xlz.commons.base.mapper.BaseMapper;
import com.xlz.commons.base.mapper.FilterRule;
import com.xlz.commons.base.service.impl.BaseServiceImpl;
import com.xlz.gray.mapper.SettingMapper;
import com.xlz.gray.model.Setting;
import com.xlz.gray.service.SettingService;

@Service
public class SettingServiceImpl extends BaseServiceImpl<Setting> implements SettingService {

	@Autowired
	private SettingMapper settingMapper;
	@Override
	protected BaseMapper<Setting> getDAO() {
		return settingMapper;
	}
	@Override
	public List<Setting> findListBySettingType(String settingType) {
		List<FilterRule> filterRuleList = new ArrayList<>();
		filterRuleList.add(new FilterRule("setting_type","=",settingType));
    	filterRuleList.add(new FilterRule("status","=","1"));
    	List<Setting> list= settingMapper.findAll(filterRuleList );
		return list;
	}
	
}
