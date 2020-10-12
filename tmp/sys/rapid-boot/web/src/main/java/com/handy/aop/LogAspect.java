package com.handy.aop;


import com.handy.common.annotation.AddLoginLog;
import com.handy.common.constants.Constants;
import com.handy.common.vo.ResultVO;
import com.handy.service.entity.log.LogLogin;
import com.handy.service.entity.sys.SysAccount;
import com.handy.service.service.log.ILogLoginService;
import com.handy.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hs
 * @Description: {}
 * @date 2019/12/2 10:19
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ILogLoginService logLoginService;

    @Pointcut("@annotation(com.handy.common.annotation.AddLoginLog)")
    @Order(1)
    public void addElasticsearchLog() {
    }

    /**
     * 返回通知（正常返回）
     */
    @AfterReturning(value = "addElasticsearchLog()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        // 开始方法
        ResultVO result1 = (ResultVO) result;
        if (ResultVO.SUCCESS_CODE.equals(result1.getCode())) {
            AddLoginLog loginLog = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(AddLoginLog.class);
            val sysAccount = (SysAccount) request.getSession().getAttribute(Constants.USERSESSION);
            val logLogin = new LogLogin();
            logLogin.setAccountId(sysAccount.getId())
                    .setCode(sysAccount.getCode())
                    .setName(sysAccount.getName())
                    .setIp(IpUtil.getIp(request))
                    .setCategoryId(loginLog.logLoginCategory().getCategoryId())
                    .setCategoryName(loginLog.logLoginCategory().getCategoryName());
            val rst = logLoginService.save(logLogin);
            log.info("记录登录日志..结果:" + rst);
        }
    }
}