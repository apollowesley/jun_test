package com.xlz.gray.restful;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xlz.commons.base.mapper.FilterRule;
import com.xlz.commons.base.mapper.PageQuery;
import com.xlz.commons.utils.PageInfo;
import com.xlz.engine.common.result.Result;
import com.xlz.engine.common.result.ResultCode;
import com.xlz.event.RegistryContainer;
import com.xlz.gray.model.Application;
import com.xlz.gray.model.ApplicationCmd;
import com.xlz.gray.service.ApplicationCmdService;
import com.xlz.gray.service.ApplicationService;

@RequestMapping("/server")
@Controller
public class PullApplicationConfig {

	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ApplicationCmdService applicationCmdService;
	@Autowired(required=false)
    private RegistryContainer registryContainer;
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/config")
	@ResponseBody
	public Object config(String applicationId,String engineType) {
		return applicationService.publishConfig(applicationId, engineType);
	}
	
	/**
	 * 拉取已经配置的灰度应用应用
	 * @return
	 */
	/*@RequestMapping("/applicationCmd")
	@ResponseBody
	@Deprecated
	public Object applicationCmd(String applicationId,String engineType) {
		List<String> list = new ArrayList<>();
		//拉取已经开启灰度，并且匹配相应引擎的灰度策略
		List<FilterRule> filterRuleList = new ArrayList<>();
		filterRuleList.add(new FilterRule("status", "=", 1));
		List<Application> applist = applicationService.findAll(filterRuleList);
		for(Application application : applist){
			filterRuleList.clear();
			filterRuleList.add(new FilterRule("application_id", "=", application.getApplicationId()));
			filterRuleList.add(new FilterRule("type", "=", engineType));
			List<ApplicationInstance> appinsList = applicationInstanceService.findAll(filterRuleList);
			if(appinsList.size() > 0)
				list.add(String.valueOf(application.getApplicationId()));
			
			if("rocketmq".equals(engineType)){
				list.add(application.getApplicationId());
			}
		}
		
		return list;
	}*/
	
	/**
	 * 拉取已经配置灰度引擎的应用列表
	 * @return
	 */
	@RequestMapping("/applications")
	@ResponseBody
	public Object applications(String engineType) {
		List<Map<String,String>> list = new ArrayList<>();
		//拉取已经开启灰度，并且匹配相应引擎的灰度策略
		List<FilterRule> filterRuleList = new ArrayList<>();
		List<Application> applist = applicationService.findAll(filterRuleList);
		for(Application application : applist){
			if(application.getEngine().indexOf(engineType) != -1){
				Map<String,String> param = new HashMap<>();
				param.put("applicationCode", application.getApplicationId());
				param.put("status", application.getStatus().toString());
				list.add(param);
			}
		}
		
		return list;
	}
	
	/**
	 * 引擎拉取服务端的操作命令
	 * @return
	 */
	@RequestMapping("/cmd")
	@ResponseBody
	public Object cmd(String applicationId,String engineType,Integer version) {
		Result<Map<String ,Object>> result = new Result<>();
		if (StringUtils.isBlank(applicationId)) {
			result.setCode(ResultCode.COMMON_BUSINESS_EXCEPTION.getCode());
			result.setMsg("参数中[applicationId]不能为空");
			return result;
		}
		
		List<FilterRule> filterRuleList = new ArrayList<>();
		filterRuleList.add(new FilterRule("application_code", "=", applicationId));
		filterRuleList.add(new FilterRule("version", ">", version));
		filterRuleList.add(new FilterRule("status", "=", 1));
		Integer count = applicationCmdService.getTotalCount(filterRuleList);
		PageQuery pageQuery = new PageQuery(1,1);
		pageQuery.setStart(count);
		if(count >0){
			pageQuery.setStart(count-1);
		}
		
		pageQuery.setSort("version");
		PageInfo<ApplicationCmd> cmdPage = applicationCmdService.findByPage(filterRuleList, pageQuery );
		result = new Result<>(ResultCode.COMMON_SUCCESS);
		Map<String ,Object> param = new HashMap<>();
		param.put("total", count);
		param.put("cmd", cmdPage.getRows()!=null && !cmdPage.getRows().isEmpty()? cmdPage.getRows().get(0):null);
		result.setObj(param);
		return result;
	}
	
	
	/**
	 * 获取dubbo应用的依赖应用
	 * @return
	 */
	@RequestMapping("/getDependencies")
	@ResponseBody
	public Object getDependencies(String applicationId) {
		Result<Set<String>> result = new Result<Set<String>>();
		
		if (StringUtils.isBlank(applicationId)) {
			result.setCode(ResultCode.COMMON_BUSINESS_EXCEPTION.getCode());
			result.setMsg("参数中[applicationId]不能为空");
			return result;
		}
		Set<String> dependencies = registryContainer.getDependencies(applicationId, false);
		
		result = new Result<Set<String>>(ResultCode.COMMON_SUCCESS);
		result.setObj(dependencies);
		return result;
	}

}