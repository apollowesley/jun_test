package com.managementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.managementsystem.dao.ProcedureConfigsDao;
import com.managementsystem.entity.ProcedureConfigs;

@Service
public class ProcedureConfigsServiceImpl extends BaseServiceImpl<Long, ProcedureConfigs> implements ProcedureConfigsService{
	@Autowired
	private ProcedureConfigsDao procedureConfigsDao;

	public ProcedureConfigsDao getResourcesDao() {
		return procedureConfigsDao;
	}
}
