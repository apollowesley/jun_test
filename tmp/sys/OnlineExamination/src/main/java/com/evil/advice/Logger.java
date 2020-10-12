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
 * ��־��¼��
 * 
 * @author frank_evil
 */
public class Logger {

	@Resource
	private LogService logService;

	public Object record(ProceedingJoinPoint pjp) {
		System.out.println("-----------------------��ʼע��-------------");
		Log log = new Log();
		try {
			ActionContext ac = ActionContext.getContext();
			// ���ò�����
			if (ac != null) {
				Map<String, Object> sessionMap = ac.getSession();
				if (sessionMap != null) {
					AdminUser adminUser = (AdminUser) sessionMap.get("adminUser");
					if (adminUser != null) {
						log.setOperator(adminUser.getName());
					}
				}
			}
			// ���ò�������
			String mname = pjp.getSignature().getName();
			log.setOperName(mname);
			// ���ò�������
			Object prams[] = pjp.getArgs();
			log.setOperParams(StringUtil.arr2Str(prams));

			log.setContent("ִ�в���:"+mname+"("+log.getOperParams()+")");

			// ִ��Ŀ�����ķ���
			Object result = pjp.proceed();
			// ���ò����ɹ�
			log.setOperResult("success");
			// ���ý����Ϣ
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
