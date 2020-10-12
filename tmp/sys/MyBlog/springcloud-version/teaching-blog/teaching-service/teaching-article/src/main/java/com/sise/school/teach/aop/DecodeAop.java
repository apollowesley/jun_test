package com.sise.school.teach.aop;

import com.sise.school.teach.utils.DecodeUtil;
import com.sise.school.teach.vo.req.HeadBodyVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author idea
 * @data 2018/9/22
 */
@Aspect
@Component
@Slf4j
@Order(2)
public class DecodeAop {

    @Pointcut("@annotation(com.sise.school.teach.annotation.DecodeHandle)")
    public void pointCut() {

    }

    /**
     * 切面
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("pointCut()")
    public Object decode(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            //对传输的字符串数据做解密操作
            if (arg instanceof HeadBodyVO) {
                decodeHeadBodyVOField((HeadBodyVO) arg);
            }
        }
        pjp.getArgs();
        return pjp.proceed();
    }

    /**
     * 加密请求体处理
     *
     * @param headBodyVO
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void decodeHeadBodyVOField(HeadBodyVO headBodyVO) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object object = headBodyVO.getBody();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                Method getterMethod = buildGetterMethodName(object, field.getName());
                Object value = getterMethod.invoke(object);
                if (value != null) {
                    String decodeStr = DecodeUtil.decodeStr((String) value);
                    field.set(object, decodeStr);
                }
            }
        }
    }

    /**
     * 创建getter方法
     *
     * @param object
     * @param fieldName
     * @return
     * @throws NoSuchMethodException
     */
    public static Method buildGetterMethodName(Object object, String fieldName) throws NoSuchMethodException {
        return object.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
    }


}
