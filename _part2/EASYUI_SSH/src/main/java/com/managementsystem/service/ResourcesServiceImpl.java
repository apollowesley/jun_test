package com.managementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.managementsystem.dao.ResourcesDao;
import com.managementsystem.entity.Resources;

@Service
public class ResourcesServiceImpl extends BaseServiceImpl<Long, Resources> implements ResourcesService{
	@Autowired
	private ResourcesDao resourcesDao;

	public ResourcesDao getResourcesDao() {
		return resourcesDao;
	}


	
}
