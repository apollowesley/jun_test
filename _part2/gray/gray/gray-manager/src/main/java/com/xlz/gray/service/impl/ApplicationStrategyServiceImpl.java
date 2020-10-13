package com.xlz.gray.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xlz.commons.base.mapper.BaseMapper;
import com.xlz.commons.base.mapper.FilterRule;
import com.xlz.commons.base.model.BaseDomain;
import com.xlz.commons.base.service.impl.BaseServiceImpl;
import com.xlz.commons.utils.StringUtils;
import com.xlz.engine.common.config.Level;
import com.xlz.engine.common.config.Way;
import com.xlz.engine.common.result.Result;
import com.xlz.engine.common.result.ResultCode;
import com.xlz.gray.mapper.ApplicationStrategyMapper;
import com.xlz.gray.mapper.StrategyMapper;
import com.xlz.gray.model.Application;
import com.xlz.gray.model.ApplicationStrategy;
import com.xlz.gray.model.Setting;
import com.xlz.gray.model.Strategy;
import com.xlz.gray.service.ApplicationStrategyService;
import com.xlz.gray.service.SettingService;

@Service
public class ApplicationStrategyServiceImpl extends BaseServiceImpl<ApplicationStrategy> implements ApplicationStrategyService {

	@Autowired
	private ApplicationStrategyMapper applicationStrategyMapper;
	@Autowired
	private StrategyMapper strategyMapper;
	@Autowired
    private SettingService settingService;
	@Override
	protected BaseMapper<ApplicationStrategy> getDAO() {
		return applicationStrategyMapper;
	}
	
    public String updateApplicationStrategy(HttpServletRequest request,BaseDomain domain,
    		Level level){
    	StringBuilder engines = new StringBuilder();
    	List<FilterRule> filterRuleList = new ArrayList<>();
    	filterRuleList.add(new FilterRule("link_id","=",domain.getId()));
    	filterRuleList.add(new FilterRule("level","=",level));
    	List<ApplicationStrategy>  list = applicationStrategyMapper.findAll(filterRuleList);
    	List<Setting> engineList = settingService.findListBySettingType("gray_engine");
    	Map<String,ApplicationStrategy> set = new HashMap<>();
    	for(ApplicationStrategy model :  list){
    		set.put(model.getType(),model);
    		//engines.append(model.getType()).append(",");
    		ApplicationStrategy entity = new ApplicationStrategy();
    		entity.setId(model.getId());
			entity.setStatus(0);
			model.setStatus(0);
			applicationStrategyMapper.update(entity);
    	}
    	
    	//插入
    	for (Setting type : engineList) {  
    		String strategyId = request.getParameter("strategy_"+type.getSettingValue());
    		ApplicationStrategy entity = new ApplicationStrategy();
    		if(StringUtils.isNotBlank(strategyId)) {
    			engines.append(type.getSettingValue()).append(",");
	    	    if(!set.containsKey(type.getSettingValue())){
    				entity.setLevel(level);
        			entity.setLinkId(domain.getId());
        			entity.setStrategyId(new Long(strategyId));
        			entity.setType(type.getSettingValue());
        			entity.setStatus(1);
        			entity.setCreateUser(domain.getCreateUser());
        			applicationStrategyMapper.save(entity );
    			}else if(set.get(type.getSettingValue()).getStatus().intValue() == 0){
    				entity.setId(set.get(type.getSettingValue()).getId());
    				entity.setStatus(1);
    				entity.setStrategyId(new Long(strategyId));
    				entity.setUpdateUser(domain.getUpdateUser());
    				applicationStrategyMapper.update(entity );
    			}
    		}
    	} 
    	return engines.toString();
    }
    
    public Result<Application> checkStrategy(HttpServletRequest request,String param){
    	//对所选的策略的灰度方式进行校验，如果有错误就报错
    	List<Setting> engineList = settingService.findListBySettingType("gray_engine");
    	for (Setting type : engineList) {  
    		String strategyId = request.getParameter("strategy_"+type.getSettingValue());
    		if(StringUtils.isNotBlank(strategyId)){
    			Strategy strategy = strategyMapper.findById(new Long(strategyId));
	    		Result<Application>result = this.publishCheckByWay(strategy,param);
				if(result != null && !result.getCode().equals(ResultCode.COMMON_SUCCESS.getCode())){
					return result;
				}
	    	}
    	}
    	return new Result<Application> ();
    }
    
    public Result<Application> publishCheckByWay(Strategy strategy,String param){
		Result<Application> result = new Result<Application> ();
		//白名单和正则表达式灰度校验
		if(strategy.getWay() == Way.whitelist || strategy.getWay() == Way.regular){
			//校验参数
			if(StringUtils.isBlank(param)){
				result.setCode(ResultCode.COMMON_BUSINESS_EXCEPTION.getCode());
				result.setMsg("根据您所选的灰度方式校验【灰度参数】为必填项，请检查后重试");
			}
		}
		//小流量灰度校验
		if(strategy.getWay() == Way.flowTatio){
			//校验参数
			if(strategy.getFlowTatio().intValue() < 0 || strategy.getFlowTatio().intValue() > 100){
				result.setCode(ResultCode.COMMON_BUSINESS_EXCEPTION.getCode());
				result.setMsg("小流量灰度制定的流量必须为1-100的整数，请检查后重试");
			}
		}
		return result;
	}
}
