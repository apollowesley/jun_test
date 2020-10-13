package com.xlz.gray.model;

import com.xlz.commons.base.model.BaseDomain;
import com.xlz.engine.common.config.Level;

public class ApplicationStrategy extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 1为用户号，2为用户名，3为电话号码 */
	private Long linkId;  
	/** strategy_id */
	private Long strategyId;  
	/** 类型 */
	private String type;  
	/** 1系统级，2服务级 */
	private Level level;  
  	
	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}
	public Long getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(Long strategyId) {
		this.strategyId = strategyId;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

}
