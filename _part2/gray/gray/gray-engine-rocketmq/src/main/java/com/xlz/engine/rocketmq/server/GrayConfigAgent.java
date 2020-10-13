package com.xlz.engine.rocketmq.server;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.xlz.engine.common.config.Cmd;
import com.xlz.engine.common.config.Way;
import com.xlz.engine.common.model.Application;
import com.xlz.engine.common.model.ApplicationService;
import com.xlz.engine.common.model.GrayDataCache;
import com.xlz.engine.common.model.Strategy;
import com.xlz.engine.common.result.Result;
import com.xlz.engine.common.result.ResultCode;
import com.xlz.engine.common.utils.HttpUtil;

public class GrayConfigAgent {
	
	private final static Logger logger = LoggerFactory.getLogger(GrayConfigAgent.class);
	private Map <String ,Cmd> appCmd = new HashMap<String ,Cmd>();
	private static AtomicInteger syncFalg = new AtomicInteger(0);
	static {
		Timer watchTimer = new Timer();
		watchTimer.schedule(new TimerTask() {
		        public void run() {
		        	if (syncFalg.get() == 0) {
						syncFalg.getAndSet(1);
			        	try {
							GrayConfigAgent.getInstance().watchGrayConfig();
						} catch (Exception e) {
							logger.error("加载灰度服务端配置数据异常",e);
						}
		        	syncFalg.getAndSet(0);
		        	}
		        }
		}, 10 , 1000*10);
	}
	private static GrayConfigAgent instance = new GrayConfigAgent();
	
	private String server = "http://127.0.0.1:8080/gray/server/";
	
	private GrayConfigAgent() {
	}
	
	public static GrayConfigAgent getInstance(){
		return instance;
	}

	public Set<String> getWhitelist(String consumerId,String applicationId) {
		//先按照consumerId获取灰度白名单，如果没有获取到就获取applicationId对应的灰白名单
		Application application = GrayDataCache.getInstance().getApplication(applicationId);
		ApplicationService service = application.getServices().get(consumerId);
		if(service != null){
			return service.getWhitelists();
		}
		return application.getWhitelists();
	}
	
	public Strategy getGrayStrategy(String consumerId,String applicationId) {
		//先按照consumerId获取灰度策略，如果没有获取到就获取applicationId对应的灰度策略
		Application application = GrayDataCache.getInstance().getApplication(applicationId);
		ApplicationService service = application.getServices().get(consumerId);
		if(service != null){
			return service.getStrategy();
		}
		return application.getStrategy();
	}
	
	public String getGrayParam(String consumerId,String applicationId) {
		//先按照consumerId获取灰度策略，如果没有获取到就获取applicationId对应的灰度策略
		Application application = GrayDataCache.getInstance().getApplication(applicationId);
		ApplicationService service = application.getServices().get(consumerId);
		if(service != null){
			return service.getParam();
		}
		return application.getParam();
	}
	
	public Map<String,String> getCurrentExtendParam(String consumerId,String applicationId) {
		//先按照consumerId获取灰度白名单，如果没有获取到就获取applicationId对应的灰度白名单
		Application application = GrayDataCache.getInstance().getApplication(applicationId);
		ApplicationService service = application.getServices().get(consumerId);
		if(service != null){
			return service.getExtendParam();
		}
		return application.getExtendParam();
	}
	
	public Pattern getGrayRegular(String consumerId, String applicationId) {
		//先按照consumerId获取灰度正则，如果没有获取到就获取applicationId对应的灰度正则
		Application application = GrayDataCache.getInstance().getApplication(applicationId);
		ApplicationService service = application.getServices().get(consumerId);
		if(service != null){
			return service.getStrategy().getRegular();
		}
		return application.getStrategy().getRegular();
	}
	
	
	/**
	 * 配置根据版本号来管理，定时来去配置,如果版本变更，则需要重新进行配置
	 * 拉取引用的配置版本信息，如果配置变更了则准备更新
	 * @throws IOException 
	 */
	public void watchGrayConfig() throws IOException{
		String uri = "applications?engineType=rocketmq";
		String json = HttpUtil.sendGet( server+uri,  "UTF-8");
		List<Map<String,String>> applist = JSON.parseObject(json, List.class);
		for(Map<String,String> entry: applist){
			String applicationCode = entry.get("applicationCode");
			//从缓存移除状态为关闭的应用
			if("0".equals(entry.get("status"))){
				Application application = GrayDataCache.getInstance().getApplication(applicationCode);
				GrayDataCache.getInstance().removeApplication(application);
			}else{
				//拉取服务端命令
				String version = "0";
				if(appCmd.containsKey(applicationCode)){
					version = appCmd.get(applicationCode).getVersion().toString();
					logger.info("当前应用的配置已经是最新版本，不用更新===>"+version);
				}
				uri = "cmd?applicationId="+entry.get("applicationCode") +"&version="+version;
				json = HttpUtil.sendGet( server+uri,  "UTF-8");
				Result<Map<String,Object>> result = JSON.parseObject(json, Result.class);
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
								(Integer)serverCmd.get("version"));
						loadGrayConfig(cmd);
						appCmd.put(applicationCode, cmd);
					} catch (Exception e) {
						logger.error("从服务端拉取配置信息异常",e);
					}
				}else{
					logger.error("从服务端拉取命令数据异常");
				}
			}
		}
	}
	
	/**
	 * 进行实际的配置变更
	 * @throws Exception
	 */
	public void loadGrayConfig(Cmd cmd) throws Exception{
		String applicationId = cmd.getApplicationId();
		String uri = "config?engineType=rocketmq&applicationId="+applicationId;
		String json = HttpUtil.sendGet( server+uri,  "UTF-8");
		Result<Map<?,?>> grayConfig = JSON.parseObject(json, Result.class);
		if(grayConfig.isSuccess() && grayConfig.getCode().equals(ResultCode.COMMON_SUCCESS.getCode())){
			grayDataParse(cmd,grayConfig.getObj());
			Application application = GrayDataCache.getInstance().getApplication(applicationId);
			System.out.println(application);
			logger.info("从服务端同步灰度配置成功===>"+applicationId);
		}else{
			logger.info("从服务端同步灰度配置异常===>"+applicationId);
		}
		
	}
	
	private String grayDataParse(Cmd cmd,Map<?,?> data){
		Application application = new Application(data.get("name").toString(),
				data.get("applicationId").toString(), 
				data.get("param").toString(), 
				new Integer(data.get("status").toString()) );
		if("stopApplicationGray".equals(cmd.getCmd()) || application.getStatus().intValue() == 0){
			GrayDataCache.getInstance().removeApplication(application);
		}else{
			if(!"startApplicationGray".equals(cmd.getCmd())){
				application = GrayDataCache.getInstance().getApplication(application.getApplicationId());
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
				Map<String,String> map = (Map<String,String>)JSON.parseObject(param, Map.class);
				for(Map.Entry<String,String> entry : map.entrySet()){
					if(paramName.equals(entry.getKey()))
						whitelists.add(entry.getKey()+":"+entry.getValue());
				}
			} catch (Exception e) {
			}
		}
		return whitelists;
	}
	
	public static void main(String args[]){
		GrayConfigAgent.getInstance();
	}
}
