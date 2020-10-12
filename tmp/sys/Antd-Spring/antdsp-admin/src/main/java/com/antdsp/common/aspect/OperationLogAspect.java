package com.antdsp.common.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.antdsp.common.annotation.OperationLog;
import com.antdsp.dao.jpa.system.SystemLogJpa;
import com.antdsp.data.entity.User;
import com.antdsp.data.entity.system.SystemLog;

@Aspect
@Component
public class OperationLogAspect {
	
	private static final String X_REAL_IP ="x-real-ip";
	
	@Autowired
	private SystemLogJpa systemLogJpa;

	@Pointcut(value="@annotation(com.antdsp.common.annotation.OperationLog)")
	public void pointCut() {}
	
	@Around(value="pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long time = System.currentTimeMillis();
		
		try {
			Object result = joinPoint.proceed();
			time = System.currentTimeMillis() - time;
			this.saveLog(joinPoint, time);
			return result;
		}catch(Exception e) {
			
		}
		return new Object();
	}
	
	private void saveLog(ProceedingJoinPoint joinPoint , long executeTime) {
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		Method excuteMthod = ms.getMethod();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		User current = (User) SecurityUtils.getSubject().getPrincipal();
		OperationLog optLog = excuteMthod.getAnnotation(OperationLog.class);
		
		SystemLog systemLog = new SystemLog();
		systemLog.setCreator("");
		systemLog.setModifier("");
		systemLog.setId(null);
		systemLog.setOptName(optLog.name());
		systemLog.setOptorId(current.getId());
		/**
		 * 由于前后端分离，ant design使用代理的方式访问后台，因此getRemoteHost() 获取的总是ant design 项目的代理地址，也即本机服务器的地址
		 * 因此使用nginx 做反向代理，并配置上以下信息，
		 * proxy_set_header   Host              $http_host;
		 * proxy_set_header   X-Real-IP         $remote_addr;
		 * 
		 * x-real-ip 即客户端ip地址
		 */
		systemLog.setOptorIp(getIp(request));
		systemLog.setOptorName(current.getLoginname());
		systemLog.setRunTime(executeTime);
		systemLog.setOptType(request.getMethod());
		systemLog.setOptURI(request.getRequestURI());
		systemLog.setOptDetail(dealDetail(ms.getParameterNames(), joinPoint.getArgs()));
		systemLog.onPreInsert();
		ms.getParameterNames();
		systemLogJpa.save(systemLog);
	}
	
	private String dealDetail(String[] paramNames, Object[] args) {
		if(args != null && args.length > 0) {
			Map<String, Object> map = new HashMap<>();
			for(int i=0; i< args.length; i++) {
				map.put(paramNames[i], args[i]);
			}
			return JSON.toJSONString(map);
		}
		return "";
	}
	
	private String getIp(HttpServletRequest request) {
		String ip = request.getHeader(X_REAL_IP);
		if(ip == null || ip.length() == 0 || "unknown".equals(ip)) {
			ip = request.getRemoteHost();
		}
		return ip;
	}
}
