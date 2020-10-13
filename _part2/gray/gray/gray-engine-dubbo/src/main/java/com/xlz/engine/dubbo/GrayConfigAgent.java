package com.xlz.engine.dubbo;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.xlz.engine.common.config.Cmd;
import com.xlz.engine.common.config.Way;
import com.xlz.engine.common.model.Application;
import com.xlz.engine.common.model.ApplicationService;
import com.xlz.engine.common.model.GrayDataCache;
import com.xlz.engine.common.model.Strategy;
import com.xlz.engine.common.result.Result;
import com.xlz.engine.common.result.ResultCode;
import com.xlz.engine.common.utils.HttpUtil;

public class GrayConfigAgent implements ApplicationListener<ContextRefreshedEvent> {

	private Logger logger = LogManager.getLogger(getClass());
	@Autowired
    private ApplicationConfig applicationConfig;
	private Map <String ,Cmd> appCmd = new HashMap<String ,Cmd>();
	
	private String server = "http://127.0.0.1:8080/gray/server/";
	private AtomicInteger syncFalg = new AtomicInteger(0);
	
	public void setServer(String server) {
		this.server = server;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public Result loadGrayConfig(Cmd cmd) {
		logger.info("收到服务端指令===>"+cmd);
		Result result = new Result();
		try {
			String uri = "config?engineType=dubbo&applicationId=" + cmd.getApplicationId();
			String json = HttpUtil.sendGet( server+uri,  "UTF-8");
			Result<Map<?,?>> grayConfig = JSON.parse(json, Result.class);
			if(grayConfig.isSuccess() && grayConfig.getCode().equals(ResultCode.COMMON_SUCCESS.getCode())){
				String applicationId = grayDataParse(cmd,result,grayConfig.getObj());
				Application application = GrayDataCache.getInstance().getApplication(applicationId);
				logger.info("服务端指令执行结果===>"+application);
				System.out.println(application);
				result.setCode(ResultCode.COMMON_SUCCESS.getCode());
				result.setMsg("灰度已经成功开启");
			}else{
				result.setCode(ResultCode.COMMON_BUSINESS_EXCEPTION.getCode());
				result.setMsg("获取服务端数据失败");
				return result;
			}
		} catch (ParseException e) {
			result.setCode(ResultCode.COMMON_SYSTEM_EXCEPTION.getCode());
			result.setMsg("获取到服务端数据解析失败");
			result.setObj(e);
			return result;
		} catch (Exception e) {
			result.setCode(ResultCode.COMMON_SYSTEM_EXCEPTION.getCode());
			result.setMsg("获取服务端数据失败");
			result.setObj(e);
			return result;
		}
		return result;
	}
	
	private String grayDataParse(Cmd cmd,Result result,Map<?,?> data){
		Application application = new Application(data.get("name").toString(),
				data.get("applicationId").toString(), 
				data.get("param").toString(), 
				new Integer(data.get("status").toString()) );
		if("stopApplicationGray".equals(cmd.getCmd()) || application.getStatus().intValue() == 0){
			GrayDataCache.getInstance().removeApplication(application);
		}else{
			if(!"startApplicationGray".equals(cmd.getCmd())){
				application = GrayDataCache.getInstance().getApplication(application.getApplicationId());
			}else{
				String instances[] = ((Map)data.get("config")).get("gray_dubbo").toString().split(";");
				for(String instance : instances){
					application.getGrayInstance().add(instance);
				}
			}
			if("startApplicationGray".equals(cmd.getCmd()) || "reloadApplicationStrategy".equals(cmd.getCmd())){
				application.setStrategy(buildStrategy(data));
				if(application.getStatus() == 1 && application.getStrategy() == null){
					return null;
				}
			}
			List<Map<?,?>> servicesMap = (List<Map<?,?>>)data.get("services");
			for(Map<?,?> service : servicesMap){
				Integer status = new Integer(service.get("status").toString());
				if(status == 0){
					continue;
				}
				ApplicationService applicationService = new ApplicationService (service.get("name").toString(), 
						service.get("version").toString() , 
						service.get("param").toString() , 
						new Integer(service.get("status").toString()) );
				if(!cmd.getCmd().startsWith("start")){
					applicationService = application.getServices().get(applicationService.getName());
				}
				if(cmd.getCmd().startsWith("start") || 
						(cmd.getCmd().startsWith("reload") && cmd.getCmd().endsWith("Strategy") )){
					applicationService.setStrategy(buildStrategy(service));
				}
				if(cmd.getCmd().startsWith("start") || 
						(cmd.getCmd().startsWith("reload") && cmd.getCmd().endsWith("Whitelist") )
						|| applicationService.getStrategy().getWay() == Way.whitelist){
					applicationService.setWhitelists( buildWhitelists(applicationService.getParam(),service));
				}
				if(cmd.getCmd().startsWith("start") ){
					application.getServices().put(applicationService.getName(), applicationService);
				}
			}
			if("startApplicationGray".equals(cmd.getCmd() ) || application.getStrategy().getWay() == Way.whitelist ||
					"reloadApplicationWhitelist".equals(cmd.getCmd())){
				application.setWhitelists( buildWhitelists(application.getParam(),data));
			}
			if("startApplicationGray".equals(cmd.getCmd())){
				GrayDataCache.getInstance().putApplication(application);
			}
		}
		return application.getApplicationId();
	}
	
	private Strategy buildStrategy(Map<?,?> data){
		Map<?,?> strategyMap = (Map<?,?>)data.get("strategy");
		Integer status = new Integer(strategyMap.get("status").toString());
		if(status == 0){
			return null;
		}
		Strategy strategy = new Strategy();
		if(strategyMap.get("name")!= null){
			strategy.setName(strategyMap.get("name").toString());
		}
		if(strategyMap.get("forwardReverse")!= null){
			strategy.setForwardReverse(new Integer(strategyMap.get("forwardReverse").toString()));
		}
		if(strategyMap.get("flowTatio")!= null){
			strategy.setFlowTatio(new Integer(strategyMap.get("flowTatio").toString()));
		}
		if(strategyMap.get("regular")!= null){
			strategy.setRegular(Pattern.compile(strategyMap.get("regular").toString()));
		}
		if(strategyMap.get("way")!= null){
			strategy.setWay(Way.valueOf(strategyMap.get("way").toString()));
		}
		return strategy;
	}
	
	private Set<String> buildWhitelists(String paramName,Map<?,?> data){
		List<Map<?,?>> whitelistsMap = (List<Map<?,?>>) data.get("whitelists");
		Set<String> whitelists = new HashSet<String>();
		if(whitelistsMap == null){
			return whitelists;
		}
		for(Map<?,?> whitelist : whitelistsMap){
			Integer status = new Integer(whitelist.get("status").toString());
			if(status == 0){
				continue;
			}
			String param = whitelist.get("param").toString();
			try {
				Map<String,String> map = (Map<String,String>)JSON.parse(param, Map.class);
				for(Map.Entry<String,String> entry : map.entrySet()){
					if(paramName.equals(entry.getKey()))
						whitelists.add(entry.getKey()+":"+entry.getValue());
				}
			} catch (ParseException e) {
			}
		}
		return whitelists;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent cre) {
		applicationConfig = cre.getApplicationContext().getBean(ApplicationConfig.class);
		//启动时拉取配置
		watchGrayConfig();
		//定时拉取配置
		startTimer();
	}
	
	//定时拉取配置
	private void startTimer(){
		Timer watchTimer = new Timer();
		watchTimer.schedule(new TimerTask() {
		        public void run() {
		        	if (syncFalg.get() == 0) {
						syncFalg.getAndSet(1);
			        	try {
							watchGrayConfig();
						} catch (Exception e) {
							logger.error("加载灰度服务端配置数据异常",e);
						}
		        	syncFalg.getAndSet(0);
		        	}
		        }
		}, 10 , 1000*10);
	}
	
	/**
	 * 配置根据版本号来管理，定时来去配置,如果版本变更，则需要重新进行配置
	 * 拉取引用的配置版本信息，如果配置变更了则准备更新
	 * @throws IOException 
	 */
	private void watchGrayConfig() {
		try {
			//获取当前应用所依赖的应用id
			String uri = "getDependencies?applicationId=" + applicationConfig.getName();
			String json = HttpUtil.sendGet( server+uri,  "UTF-8");
			Result<List<String>> result = JSON.parse(json, Result.class);
			if(result.isSuccess() && result.getCode().equals(ResultCode.COMMON_SUCCESS.getCode())){
				for(String applicationCode : result.getObj()){
					loadGrayConfig(applicationCode) ;
				}
			}else{
				logger.error(ResultCode.COMMON_BUSINESS_EXCEPTION.getCode(),"获取服务端数据失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),"初始化灰度数据失败！");
		}
		
		
	}
	
	/**
	 * 进行实际的配置变更
	 * @throws Exception
	 */
	public void loadGrayConfig(String applicationCode) throws Exception{
		try {
			String version = "0";
			if(appCmd.containsKey(applicationCode)){
				version = appCmd.get(applicationCode).getVersion().toString();
				logger.info("当前应用的配置已经是最新版本，不用更新===>"+version);
			}
			String uri ="cmd?applicationId="+applicationCode +"&version="+version;
			String json = HttpUtil.sendGet( server+uri,  "UTF-8");
			Result<Map<String,Object>> result = JSON.parse(json, Result.class);
			if(result.isSuccess()){
				Integer total = Integer.valueOf(result.getObj().get("total").toString());
				if(total.intValue() == 0){
					return ;
				}
				Map serverCmd = (Map)result.getObj().get("cmd");
				String grayCmd = serverCmd.get("grayCmd").toString();
				Cmd cmd= null;
				if(total.intValue() > 1){
					//拉取所有配置
					grayCmd = "startApplicationGray";
				}
				try {
					cmd = new Cmd(grayCmd,applicationCode,
							((Long)serverCmd.get("version")).intValue());
					loadGrayConfig(cmd);
					appCmd.put(applicationCode, cmd);
				} catch (Exception e) {
					logger.error("从服务端拉取配置信息异常",e);
				}
			}else{
				logger.error("从服务端拉取命令数据异常");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),"初始化灰度数据失败！");
		}
	}
}
