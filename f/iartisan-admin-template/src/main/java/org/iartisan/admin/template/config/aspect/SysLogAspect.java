package org.iartisan.admin.template.config.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.iartisan.admin.template.service.entity.LogEntity;
import org.iartisan.admin.template.service.management.LogManagementService;
import org.iartisan.runtime.web.annotation.WebLog;
import org.iartisan.runtime.web.aop.WebAspect;
import org.iartisan.runtime.web.authentication.RealmBean;
import org.iartisan.runtime.web.contants.WebConstants;
import org.iartisan.runtime.web.utils.IPUtil;
import org.iartisan.runtime.web.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * <p>
 * 日志切面
 *
 * @author King
 * @since 2018/4/17
 */
@Aspect
@Component
public class SysLogAspect extends WebAspect {

    @Autowired
    private LogManagementService logManagementService;

    @Pointcut("@annotation(org.iartisan.runtime.web.annotation.WebLog)")
    public void doAspect() {

    }

    @Override
    public void around(ProceedingJoinPoint point) {

    }

    @Before("doAspect()")
    public void around(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        WebLog webLog = method.getAnnotation(WebLog.class);
        LogEntity entity = new LogEntity();
        if (webLog != null) {
            //注解上的描述
            entity.setMethodDesc(webLog.value());
        }
        entity.setMethod(method.getDeclaringClass().getName() + "." + method.getName());
        entity.setIp(IPUtil.getIP(WebUtil.getHttpServletRequest()));
        RealmBean realmBean = (RealmBean) WebUtil.getShiroSession().getAttribute(WebConstants._USER);
        if (null != realmBean) {
            entity.setUserId(realmBean.getUserId());
            entity.setUserName(realmBean.getUserName());
        }
        entity.setStartTime(new Date());
        logManagementService.saveData(entity);
    }
}
