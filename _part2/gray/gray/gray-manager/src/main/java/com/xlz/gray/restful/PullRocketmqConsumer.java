package com.xlz.gray.restful;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xlz.commons.base.mapper.FilterRule;
import com.xlz.commons.utils.StringUtils;
import com.xlz.engine.common.result.Result;
import com.xlz.engine.common.result.ResultCode;
import com.xlz.gray.model.Application;
import com.xlz.gray.model.Setting;
import com.xlz.gray.service.ApplicationService;

@RequestMapping("/pullRocketmqConsumer")
@Controller
public class PullRocketmqConsumer {

	@Autowired
	private ApplicationService applicationService;
	
	/**
	 * 根据应用id拉取对应的consumer的配置参数
	 * @param applicationId
	 * @return
	 */
	@RequestMapping("/config")
	@ResponseBody
	public Result<Map<String,String>> applicationConfig(String applicationId) {
		Result<Map<String,String>> result = new Result<Map<String,String>>();
		//获取应用中配置的多个consumer
		List<FilterRule> filterRuleList = new ArrayList<>();
        if (StringUtils.isNotBlank(applicationId)) {
            filterRuleList.add(new FilterRule("application_id","=",applicationId));
        }else{
        	//异常
        	return new Result<Map<String,String>>(ResultCode.COMMON_BUSINESS_EXCEPTION);
        }
        List<Application> applist = applicationService.findAll(filterRuleList);
		//TODO 检查应用是否存在，不存在异常，存在继续获取配置
        
        Application application = applist.get(0);
        Map<String,String> resultObj = new HashMap<>();
        List<Setting> settingConfigs = applicationService.findGraySettingConfig(application.getId());
        for(Setting setting : settingConfigs){
        	if("rocketmq".equals(setting.getSettingValue())){
        		for(Setting item : setting.getChildrens()){
        			resultObj.put(item.getSettingParam(), item.getSettingValue());
        		}
        		break;        	}
        }
        resultObj.put("is_gray", String.valueOf(application.getStatus()==1));
        result.setObj(resultObj);
		return result;
	}
}
