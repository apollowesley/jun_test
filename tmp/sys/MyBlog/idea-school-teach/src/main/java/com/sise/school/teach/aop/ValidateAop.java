package com.sise.school.teach.aop;

import com.sise.school.teach.annotation.ValidateLogin;
import com.sise.school.teach.bussiness.user.service.Userservice;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.utils.TokenUtil;
import com.sise.school.teach.vo.req.HeadBodyVO;
import com.sise.school.teach.vo.req.HeadVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 校验切面
 *
 * @author idea
 * @data 2018/9/22
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class ValidateAop {

    public static String AUTHENTICATION = "authentication";

    @Autowired
    public Userservice userservice;

    /**
     * 登录认证拦截器
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@within(org.springframework.web.bind.annotation.RequestMapping)")
    public Object aop(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        ValidateLogin validateLogin = AnnotationUtils.findAnnotation(method, ValidateLogin.class);
        if (validateLogin != null) {
            if (isTokenValidate()) {
                headVoHandle(pjp);
                log.info("【登录网关】请求接口" + method.getName() + ",携带参数{}", pjp.getArgs());
                return pjp.proceed();
            }
            log.error("【登录网关】未登录认证");
            return ResponseBuilder.buildAuthErrorResponseVO();
        } else {
            return pjp.proceed();
        }
    }

    /**
     * 判断token是否合法
     *
     * @return
     */
    private boolean isTokenValidate() {
        return TokenUtil.getTokenFromHeader(getRequest()) != null;
    }

    /**
     * 拦截参数注入token
     *
     * @param pjp
     */
    private void headVoHandle(ProceedingJoinPoint pjp) {
        String token = TokenUtil.getToken(getRequest());
        if (StringUtils.isNotBlank(token)) {
            Object[] args = pjp.getArgs();
            for (Object arg : args) {
                if (arg instanceof HeadBodyVO) {
                    HeadVO headVO = new HeadVO();
                    headVO.setToken(TokenUtil.getToken(getRequest()));
                    ((HeadBodyVO) arg).setHead(headVO);
                }
            }
        }
    }

    /**
     * 获得请求
     *
     * @return
     */
    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request;
    }

    /**
     * d
     * 请求头部的参数获取
     *
     * @param pjp
     * @return
     */
    private HeadBodyVO getHeadBodyVO(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if (arg instanceof HeadBodyVO) {
                return (HeadBodyVO) arg;
            }
        }
        return null;
    }

    /**
     * 防止请求体里面的body为空
     *
     * @param method
     * @param headBody
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void preventBodyNullAndSetToken(Method method, HeadBodyVO headBody, HeadVO headVO) throws IllegalAccessException, InstantiationException {
        Object body = headBody.getBody();
        if (body != null) {
            return;
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (parameterType.equals(HeadBodyVO.class)) {
                ResolvableType resolvableType = ResolvableType.forMethodParameter(method, i);
                Class<?> bodyClass = resolvableType.getGeneric(i).resolve();
                headBody.setBody(bodyClass.newInstance());
            }
        }
    }
}
