package com.jfast.web.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.jfast.common.annotation.Param;
import com.jfast.common.annotation.ParamsType;
import com.jfast.common.annotation.ParamsValidate;
import com.jfast.common.utils.ObjectUtils;
import com.jfast.common.utils.TypeConvert;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * controller 层参数校验拦截器
 * @author zengjintao
 * @version 1.0
 * @create_at 2018/12/22 19:31
 */
@Component
public class ParamsValidateInterceptor extends BaseInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            ParamsValidate paramsValidate = handlerMethod.getMethod().getAnnotation(ParamsValidate.class);
            if (paramsValidate != null) {
                Param[] params = paramsValidate.params();
                ParamsType paramsType = paramsValidate.paramsType();
                return checkParam(request, response, params, paramsType);
            }
        }
        return true;
    }

    private boolean checkParam(HttpServletRequest request, HttpServletResponse response, Param[] params,
                               ParamsType paramsType) {
        Map dataMap = null;
        boolean isJsonData = false;
        if (paramsType == ParamsType.JSON_DATA) {
            String data = readData(request);
            dataMap = JSONObject.parseObject(data);
            isJsonData = true;
        }
        for (Param param : params) {
            String name = param.name();
            Object value = null;
            if (paramsType == ParamsType.FORM_DATA) {
                value = TypeConvert.convert(request.getParameter(name));
            } else if (isJsonData && dataMap != null) {
                value = TypeConvert.convert(dataMap.get(name));
            }
            if (ObjectUtils.isEmpty(value)) {
                Map resultMap = new HashMap<>();
                resultMap.put("code", param.errorCode());
                resultMap.put("message", param.message());
                renderJson(response, resultMap);
                return false;
            }
        }
        return true;
    }
}
