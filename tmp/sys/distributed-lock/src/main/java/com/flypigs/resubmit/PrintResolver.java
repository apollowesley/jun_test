package com.flypigs.resubmit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 仅仅是为了测试，无实际业务意义
 */
public class PrintResolver implements ResubmitResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintResolver.class);
    @Override
    public boolean isReturn() {
        return false;
    }

    @Override
    public Object resolve(Method method) {
        StringBuilder sb = new StringBuilder();
        Class clazz = method.getDeclaringClass();
        sb.append(clazz.getName());
        String methodName = method.getName();
        sb.append(".").append(methodName).append("(");
        Class [] args = method.getParameterTypes();
        if(args != null){
            for(int i = 0; i < args.length; i++) {
                Class arg = args[i];
                sb.append(arg.getSimpleName());
                if(i + 1 < args.length ){
                    sb.append(",");
                }
            }
        }
        sb.append(")");
        sb.append(": 存在重复提交");
        LOGGER.warn(sb.toString());
        return null;
    }
}
