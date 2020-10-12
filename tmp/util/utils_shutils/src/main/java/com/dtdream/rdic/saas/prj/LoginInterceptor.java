package com.dtdream.rdic.saas.prj;

import com.dtdream.rdic.saas.utils.IoUtils;
import org.codehaus.jackson.util.ByteArrayBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    /**
     * preHandle方法处理器拦截，该方法将在Controller处理之前进行调用，同时存在多个Interceptor通过链表组织，
     * 然后SpringMVC根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。当preHandle的返 回值为false，请求就结束了。
     */
    @Override
    public boolean preHandle(
        HttpServletRequest objHttpRequest,
        HttpServletResponse arg1,
        Object arg2
        ) throws Exception
    {
        logPost(objHttpRequest);
        return true;
    }

    /**
     * 打印出请求内容
     * @param objHttpRequest
     */
    private void logPost(HttpServletRequest objHttpRequest) {
        StringBuilder objBuilder = new StringBuilder();
        String[] asValue;
        @SuppressWarnings("unchecked")
        Map<String, String[]> objHttpMap = objHttpRequest.getParameterMap();
        if (objHttpMap.size() > 0) {
            objBuilder.append("parameter:");
            for (Entry<String, String[]> item : objHttpMap.entrySet()) {
                objBuilder.append(' ');
                objBuilder.append(item.getKey()).append('=');
                asValue = item.getValue();
                if (asValue.length > 1)
                    objBuilder.append(item.getValue());
                else {
                    objBuilder.append('[');
                    for (int i = 0; i < asValue.length; ++ i) {
                        if (i != 0)
                            objBuilder.append(',');
                        objBuilder.append(asValue[i]);
                    }
                    objBuilder.append(']');
                }
            }
        } else {
            try {
                ServletInputStream is = objHttpRequest.getInputStream();
                if (is.markSupported()) {
                    is.mark(Integer.MAX_VALUE);
                    ByteArrayBuilder bab = IoUtils.read(is);
                    if (null != bab)
                        objBuilder.append(new String(bab.toByteArray(), "UTF-8"));
                    is.reset();
                } else {
                    objBuilder.append("<body>");
                }
            } catch (IOException e) {
                logger.error("读取内容失败：", e);
            }
        }
        logger.debug(objBuilder.toString());
    }
}
