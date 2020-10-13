package com.xlz.gray.service;

import java.util.List;

import com.xlz.commons.base.service.BaseService;
import com.xlz.engine.common.config.Level;
import com.xlz.gray.model.Strategy;

public interface StrategyService extends BaseService<Strategy> {
	
	List<Strategy> findStrategyByLink(Long id, String engineType,Level level);
	
}

