package com.xlz.gray.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.xlz.commons.base.BaseController;
import com.xlz.engine.common.result.Result;
import com.xlz.engine.common.result.ResultCode;
import com.xlz.event.RegistryContainer;
import com.xlz.gray.model.Application;
import com.xlz.gray.model.ApplicationService;
import com.xlz.gray.model.Setting;
import com.xlz.gray.model.Strategy;
import com.xlz.gray.service.ApplicationServiceService;
import com.xlz.gray.service.ApplicationStrategyService;
import com.xlz.gray.service.SettingService;

@PropertySource({"classpath:config/application.properties"})
public class GrayBaseController extends BaseController{

    @Autowired
    private ApplicationStrategyService applicationStrategyService;
    @Autowired
    protected ApplicationServiceService applicationServiceService;
    @Autowired
    protected com.xlz.gray.service.ApplicationService applicationService;
    @Autowired
    private SettingService settingService;
    
    @Value("${active.grays}")
    protected String activeGrays;
    
    /**
     * 校验灰度数据完整性
     * @param application
     * @return
     */
    protected Result<Application> publishCompleteCheck(String applicationId) {
		Result<Application> result = new Result<Application> ();
		//TODO 获取已经开通的策略类型，对其进行参数校验
		List<Setting> engineList = settingService.findListBySettingType("gray_engine");
		for (Setting type : engineList) {  
			Result<Application> data = applicationService.publishConfig(applicationId, type.getSettingValue());
			if(!result.getCode().equals(ResultCode.COMMON_SUCCESS.getCode())){
				result.setMsg(result.getMsg()+"<br/>");
				result.setCode(data.getCode());
				break ;
			}else{
				Application application = data.getObj();
				//按照不同引擎和策略分别校验
				Strategy strategy = (Strategy)application.getStrategy();
				//控制台未勾选，不检查
				if(strategy.getStatus().intValue() != 1){
					continue;
				}
				//按照灰度方式进行校验
				result = applicationStrategyService.publishCheckByWay(strategy,application.getParam());
				if(!result.getCode().equals(ResultCode.COMMON_SUCCESS.getCode())){
					return result;
				}
				//服务的配置校验
				for(ApplicationService service : application.getServices()){
					//按照灰度方式进行校验
					strategy = (Strategy)service.getStrategy();
					result = applicationStrategyService.publishCheckByWay(strategy,service.getParam());
					if(!result.getCode().equals(ResultCode.COMMON_SUCCESS.getCode())){
						return result;
					}
				}
			}
		}
		return result;
	}
	
}
