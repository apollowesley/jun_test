package com.xlz.gray.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.xlz.commons.base.mapper.BaseMapper;
import com.xlz.commons.base.mapper.FilterRule;
import com.xlz.commons.base.service.impl.BaseServiceImpl;
import com.xlz.engine.common.config.Level;
import com.xlz.engine.common.config.Way;
import com.xlz.engine.common.result.Result;
import com.xlz.engine.common.result.ResultCode;
import com.xlz.gray.mapper.ApplicationMapper;
import com.xlz.gray.model.Application;
import com.xlz.gray.model.Config;
import com.xlz.gray.model.Setting;
import com.xlz.gray.model.Strategy;
import com.xlz.gray.model.WhiteList;
import com.xlz.gray.service.ApplicationInstanceService;
import com.xlz.gray.service.ApplicationService;
import com.xlz.gray.service.ApplicationServiceService;
import com.xlz.gray.service.ApplicationStrategyService;
import com.xlz.gray.service.ConfigService;
import com.xlz.gray.service.SettingService;
import com.xlz.gray.service.StrategyService;
import com.xlz.gray.service.WhiteListService;

@Service
public class ApplicationServiceImpl extends BaseServiceImpl<Application> implements ApplicationService {

	@Autowired
	private ApplicationMapper applicationMapper;
	@Autowired
	private StrategyService strategyService;
	@Autowired
	private WhiteListService whiteListService;
	@Autowired
	private ApplicationServiceService applicationServiceService;
	@Autowired
    private SettingService settingService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private ApplicationStrategyService applicationStrategyService;
    
	@Override
	protected BaseMapper<Application> getDAO() {
		return applicationMapper;
	}
	
	@Override
	public Object saveGrayConfig(Application entity,HttpServletRequest request) throws IOException {
		List<FilterRule> filterRuleList = new ArrayList<>();
    	filterRuleList.add(new FilterRule("application_id","=",entity.getApplicationId()));
        Integer count = getDAO().getTotalCount(filterRuleList);
        if (count != null && count.intValue() > 0) {
            return renderError("被灰度系统唯一标识已存在!");
        }
        
        //对所选的策略的灰度方式进行校验
        Result<Application> result = applicationStrategyService.checkStrategy(request,entity.getParam());
    	if(result != null && !result.getCode().equals(ResultCode.COMMON_SUCCESS.getCode())){
			return renderError(result.getMsg());
		}
        entity.setCreateUser(getShiroUser().getName());
        getDAO().save(entity);
        String engines = applicationStrategyService.updateApplicationStrategy(request, entity,Level.application);
        //修改engines
        entity.setEngine(engines);
        getDAO().update(entity);
        
        //保存灰度配置
        List<Setting> engineList = findGraySetting();
        saveGrayConfig(request,engineList,entity.getId());
        return renderSuccess("添加成功");
	}
	
	@Override
	public List<Setting> findGraySetting(){
    	//取出引擎列表
    	List<Setting> list= settingService.findListBySettingType("gray_engine");
    	//对数据分类
    	for(Setting setting : list){
    		setting.setChildrens(
    				settingService.findListBySettingType("gray_engine"+"_"+setting.getSettingValue()));
    	}
		return list;
    }
	
	private void saveGrayConfig(HttpServletRequest request,List<Setting> engineList,
			Long applicationId) throws IOException{
    	//将参数值获取，save到grap_config
        List<FilterRule> filterRuleList = new ArrayList<>();
        for(Setting entry : engineList){
        	Config config = new Config();
        	//获取所有单个参数暂存map，转为json
        	Map<String,String> paramValues = new HashMap<>();
        	for(Setting setting : entry.getChildrens()){
        		String value = request.getParameter(entry.getSettingValue()+"_"+setting.getSettingParam());
        		paramValues.put(setting.getSettingParam(), value);
        	}
        	config.setSettingValue(JSON.json(paramValues));
        	filterRuleList.clear();
        	filterRuleList.add(new FilterRule("status","=","1"));
        	filterRuleList.add(new FilterRule("application_id","=",applicationId));
        	filterRuleList.add(new FilterRule("system_setting_id","=",entry.getId()));
        	List<Config> configList = configService.findAll(filterRuleList);
        	//按照applicationid和settingid判断当前配置是否存在，不存在插入，存在更新
        	if(configList == null || configList.isEmpty()){
        		config.setApplicationId(applicationId);
            	config.setSystemSettingId(entry.getId());
            	config.setStatus(1);
        		config.setCreateUser(getShiroUser().getName());
        		configService.save(config );
        	}else{
        		config.setId(configList.get(0).getId());
        		config.setUpdateUser(getShiroUser().getName());
        		configService.update(config);
        	}
        }
    }
	
	
    
	@Override
	public Result<Application> publishConfig(String applicationId, String engineType) {
		Result<Application> result = new Result<Application>();

		if (StringUtils.isBlank(applicationId)) {
			result.setCode(ResultCode.COMMON_BUSINESS_EXCEPTION.getCode());
			result.setMsg("参数中[applicationId]不能为空");
			return result;
		}
		if (engineType == null || "".equals(engineType)) {
			result.setCode(ResultCode.COMMON_BUSINESS_EXCEPTION.getCode());
			result.setMsg("参数中[engineType]不能为空");
			return result;
		}

		// 获取系统信息，并且判断系统是否启用灰度
		List<FilterRule> filterRuleList = new ArrayList<>();
		filterRuleList.add(new FilterRule("application_id", "=", applicationId));
		List<Application> applicationList = this.findAll(filterRuleList);
		Application application;
		if (!applicationList.isEmpty()) {
			application = applicationList.get(0);
		} else {
			result.setCode(ResultCode.COMMON_BUSINESS_EXCEPTION.getCode());
			result.setMsg("根据标识在灰度管控系统中未查找到此系统");
			return result;
		}
		// 获取系统级的灰度策略和其他信息
		Map<String, Object> returnMap = getStrategyAndOther(engineType, application.getId(), Level.application);
		if (returnMap == null) {
			result.setCode(ResultCode.COMMON_BUSINESS_EXCEPTION.getCode());
			result.setMsg("此系统所绑定的策略中不存在类型为[" + engineType + "]的策略");
			return result;
		}

		application.setWhitelists(returnMap.get("whitelists"));
		application.setStrategy(returnMap.get("strategy"));
		
		filterRuleList.clear();
		filterRuleList.add(new FilterRule("application_id", "=", application.getId()));
		filterRuleList.add(new FilterRule("type", "=", engineType));
		//获取系统下的服务包含按照引擎类型获取
		List<com.xlz.gray.model.ApplicationService> applicationServiceList = applicationServiceService.findAll(filterRuleList);
		
		List<com.xlz.gray.model.ApplicationService> applicationServices = new ArrayList<>();
		for(com.xlz.gray.model.ApplicationService applicationService : applicationServiceList) {
			// 获取服务级的灰度策略和其他信息
			returnMap = getStrategyAndOther(engineType, applicationService.getId(), Level.service);
			if (returnMap != null) {
				applicationServices.add(applicationService);
				applicationService.setStrategy(returnMap.get("strategy"));
				applicationService.setWhitelists(returnMap.get("whitelists"));
			}
		}
		
		application.setServices(applicationServices);
		
		//加入引擎的配置信息
		List<Setting> list = this.findGraySettingConfig(application.getId());
		for(Setting setting : list){
			if(engineType.equals(setting.getSettingValue())){
				for(Setting item : setting.getChildrens()){
					application.getConfig().put(item.getSettingParam(), item.getSettingValue());
				}
			}
		}
		result = new Result<Application>(ResultCode.COMMON_SUCCESS);
		result.setObj(application);
		return result;
	}
	
	/**
	 * 根据参数获取策略信息
	 * 
	 * @param engineType
	 * @param linkId
	 * @param level
	 * @return
	 */
	private Map<String, Object> getStrategyAndOther(String engineType, Long linkId, Level level) {
		// 找到系统并且系统已经启用灰度，继续获取系统所绑定的策略,系统在启用灰度之前必须保障存在可用策略
		List<Strategy> strategyList = strategyService.findStrategyByLink(linkId,engineType, level);
		if (strategyList.size() != 1) {
			return null;
		}
		Map<String, Object> resultMap = new HashMap<>();
		Strategy strategy = strategyList.get(0);
		// 根据不同策略获取不同信息，比如白名单策略需要获取
		if (strategy.getWay() == Way.whitelist) {
			// 获取白名单
			List<WhiteList> whitelists = whiteListService.findWhitelistByLinkId(linkId,level);
			resultMap.put("whitelists", whitelists);
		}
		resultMap.put("strategy", strategy);
		
		return resultMap;
	}

	@Override
	public Object editGrayConfig(Application entity,String oldApplicationId, HttpServletRequest request) throws IOException {
		if(!oldApplicationId.equals(entity.getApplicationId())){
	    	List<FilterRule> filterRuleList = new ArrayList<>();
	    	filterRuleList.add(new FilterRule("application_id","=",entity.getApplicationId()));
	        Integer count = getDAO().getTotalCount(filterRuleList);
	        if (count != null && count.intValue() > 0) {
	            return renderError("被灰度系统名已存在!");
	        }
    	}
    	//对所选的策略的灰度方式进行校验
        Result<Application> result = applicationStrategyService.checkStrategy(request,entity.getParam());
    	if(result != null && !result.getCode().equals(ResultCode.COMMON_SUCCESS.getCode())){
			return renderError(result.getMsg());
		}
    	entity.setUpdateUser(getShiroUser().getName());
       
        applicationStrategyService.updateApplicationStrategy(request, entity,Level.application);
        String engines = applicationStrategyService.updateApplicationStrategy(request, entity,Level.application);
        //修改engines
        entity.setEngine(engines);
        getDAO().update(entity);
        
        //保存灰度配置
        List<Setting> engineList = findGraySetting();
        saveGrayConfig(request,engineList,entity.getId());
        return renderSuccess("修改成功！");
	}

	@Override
	public List<Setting> findGraySettingConfig(Long applicationId) {
		List<FilterRule> filterRuleList = new ArrayList<>();
		//获取配置参数和对应的值
    	List<Setting> engineList = this.findGraySetting();
    	for(Setting setting : engineList){
    		//如果configlist中有配置项则设置为true，前端会设置已勾选
    		filterRuleList.clear();
    		filterRuleList.add(new FilterRule("status","=","1"));
        	filterRuleList.add(new FilterRule("application_id","=",applicationId));
        	filterRuleList.add(new FilterRule("system_setting_id","=",setting.getId()));
        	List<Config> configList = configService.findAll(filterRuleList);
        	if(configList != null && configList.size() > 0){
        		//反序列化config的json，设置值
        		Map paramValues = new HashMap();
				try {
					paramValues = JSON.parse(configList.get(0).getSettingValue(),Map.class);
				} catch (ParseException e) {
				}
        		for(Setting item : setting.getChildrens()){
        			String val = String.valueOf(paramValues.get(item.getSettingParam()));
        			item.setSettingValue(
        					val != null && !"null".equals(val)?
        					val : (item.getSettingValue() == null ? "" : item.getSettingValue()));
        		}
        	}
    	}
		return engineList;
	}
}
