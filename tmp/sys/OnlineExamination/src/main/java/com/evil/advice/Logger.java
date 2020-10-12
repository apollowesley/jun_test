package com.evil.advice;

import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;

import com.evil.pojo.system.AdminUser;
import com.evil.pojo.system.Log;
import com.evil.service.LogService;
import com.evil.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * 日志记录仪
 * 
 * @author frank_evil
 */
public class Logger {

	@Resource
	private LogService logService;

	public Object record(ProceedingJoinPoint pjp) {
		System.out.println("-----------------------开始注入-------------");
		Log log = new Log();
		try {
			ActionContext ac = ActionContext.getContext();
			// 设置操作人
			if (ac != null) {
				Map<String, Object> sessionMap = ac.getSession();
				if (sessionMap != null) {
					AdminUser adminUser = (AdminUser) sessionMap.get("adminUser");
					if (adminUser != null) {
						log.setOperator(adminUser.getName());
					}
				}
			}
			// 设置操作名字
			String mname = pjp.getSignature().getName();
			log.setOperName(mname);
			// 设置操作参数
			Object prams[] = pjp.getArgs();
			log.setOperParams(StringUtil.arr2Str(prams));

			log.setContent("执行操作:"+mname+"("+log.getOperParams()+")");

			// 执行目标对象的方法
			Object result = pjp.proceed();
			// 设置操作成功
			log.setOperResult("success");
			// 设置结果信息
			if (result != null) {
				log.setResultMsg(result.toString());
			}
			return result;
		} catch (Throwable e) {
			e.printStackTrace();
			log.setOperResult("failure");
			log.setResultMsg(e.getMessage());
		} finally {
			System.out.println(log.getContent() + ","+log.getOperParams()+"," + log.getOperResult());
			logService.saveEntity(log);
		}
		return null;
	}

}
