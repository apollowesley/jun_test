package com.team.blogs.common.redis.interceptor;

import com.team.blogs.common.redis.annotation.CacheLock;
import com.team.blogs.common.redis.annotation.CacheParam;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author: xiaokai
 * @Description: 通过接口注入的方式生成不同的规则
 * @Date: 2019/6/26
 * @Version: 1.0
 */
public class LockKeyGenerator implements CacheKeyGenerator {

    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lockAnnotation = method.getAnnotation(CacheLock.class);
        final Object[] args = pjp.getArgs();
        final Parameter[] parameters = method.getParameters();
        StringBuilder builder = new StringBuilder();
        //TODO:默认解析方法里面带 CacheParam注解的属性，如果没有尝试着解析实体对象中的
        for (int i = 0; i < parameters.length; i++) {
            final CacheParam annotaion = parameters[i].getAnnotation(CacheParam.class);
            if (annotaion == null) {
                continue;
            }
            builder.append(lockAnnotation.delimiter()).append(args[i]);
        }
        if (StringUtils.isEmpty(builder.toString())) {
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                final Object obj = args[i];
                final Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    final CacheParam annotation = field.getAnnotation(CacheParam.class);
                    if (annotation == null) {
                        continue;
                    }
                    field.setAccessible(true);
                    builder.append(lockAnnotation.delimiter()).append(ReflectionUtils.getField(field, obj));
                }

            }
        }
        return lockAnnotation.prefix() + builder.toString();
    }
}
