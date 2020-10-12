package com.iotechn.iot.gw.controller;

import com.alibaba.fastjson.JSONObject;
import com.iotechn.iot.commons.core.annotation.HttpMethod;
import com.iotechn.iot.commons.core.annotation.HttpParam;
import com.iotechn.iot.commons.core.annotation.HttpParamType;
import com.iotechn.iot.commons.core.annotation.ResultType;
import com.iotechn.iot.commons.core.annotation.param.Enum;
import com.iotechn.iot.commons.core.annotation.param.NotNull;
import com.iotechn.iot.commons.core.annotation.param.Range;
import com.iotechn.iot.commons.core.annotation.param.TextFormat;
import com.iotechn.iot.commons.entity.exception.ServiceException;
import com.iotechn.iot.commons.entity.exception.ServiceExceptionDefinition;
import com.iotechn.iot.gw.Const;
import com.iotechn.iot.gw.api.ApiManager;
import com.iotechn.iot.gw.exception.GatewayExceptionDefinition;
import com.iotechn.iot.gw.exception.GatewayServiceException;
import com.iotechn.iot.gw.model.GatewayResponse;
import com.iotechn.iot.gw.util.IpAdrressUtil;
import com.iotechn.iot.user.entity.UserDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-08
 * Time: 下午11:00
 */
@Controller
@RequestMapping("/m.api")
public class ApiController {

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
    private static final String USER_REDIS_PREFIX = "USER_SESSION_";

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String invoke(HttpServletRequest req, HttpServletResponse res) {
        try {
            Object obj = process(req, res);
            if(Const.IGNORE_PARAM_LIST.contains(obj.getClass())){
                return obj.toString();
            }
            return JSONObject.toJSONString(obj);
        } catch (ServiceException e) {
            GatewayResponse gatewayResponse = new GatewayResponse();
            gatewayResponse.setTimestamp(System.currentTimeMillis());
            gatewayResponse.setCode(e.getCode());
            gatewayResponse.setMsg(e.getMessage());
            return (JSONObject.toJSONString(gatewayResponse));
        }
    }


    private Object process(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        try {
            ApiManager apiManager = applicationContext.getBean(ApiManager.class);
            Map<String, String[]> parameterMap = request.getParameterMap();
            String[] gps = parameterMap.get("_gp");
            String[] mts = parameterMap.get("_mt");
            if(gps == null  || mts == null || gps.length == 0 || mts.length == 0){
                throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_METHOD_NOT_EXIST);
            }
            String _gp = gps[0];
            String _mt = mts[0];
            String[] _types = parameterMap.get("_type");
            String _type = null;
            if (_types != null && _types.length > 0) {
                _type = _types[0];
            }
            Method method = apiManager.getMethod(_gp, _mt);
            if (method == null) {
                throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_METHOD_NOT_EXIST);
            }
            HttpMethod httpMethod = method.getAnnotation(HttpMethod.class);
            if (httpMethod == null) {
                //只起标记作用防止调到封闭方法了
                throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_METHOD_NOT_ANNOTATION);
            }
            Object serviceBean = applicationContext.getBean(method.getDeclaringClass());
            Parameter[] methodParameters = method.getParameters();
            //传入参数的数组，与method对象获取的参数长度相同
            Object[] args = new Object[methodParameters.length];
            for (int i = 0; i < methodParameters.length; i++) {
                Parameter methodParam = methodParameters[i];
                HttpParam httpParam = methodParam.getAnnotation(HttpParam.class);
                if (httpParam == null) {
                    throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_METHOD_NOT_ANNOTATION);
                }
                if (httpParam.type() == HttpParamType.COMMON) {
                    String[] paramArray = parameterMap.get(httpParam.name());
                    if (paramArray != null && paramArray.length > 0 && !StringUtils.isEmpty(paramArray[0])) {
                        Class<?> type = methodParam.getType();
                        //参数校验
                        checkParam(type, methodParam, paramArray[0]);
                        if (String.class == type) {
                            args[i] = paramArray[0];
                        } else if (Const.IGNORE_PARAM_LIST.contains(type)) {
                            Constructor<?> constructor = type.getConstructor(String.class);
                            args[i] = constructor.newInstance(paramArray[0]);
                        } else if (type.isArray()) {
                            //若是数组
                            Class<?> itemType = type.getComponentType();
                            Object realType[] = (Object[]) Array.newInstance(itemType,paramArray.length);
                            if(paramArray.length > 0){
                                for(int j = 0; j < paramArray.length; j++) {
                                    if(Const.IGNORE_PARAM_LIST.contains(itemType)){
                                        Constructor<?> constructor = itemType.getConstructor(String.class);
                                        realType[j] = constructor.newInstance(paramArray[j]);
                                    }else {
                                        realType[j] = JSONObject.parseObject(paramArray[j], itemType);
                                    }
                                }
                            }
                            args[i] = realType;
                        } else {
                            //Json解析
                            args[i] = JSONObject.parseObject(paramArray[0], type);
                        }
                    } else {
                        if (methodParam.getAnnotation(NotNull.class) != null) {
                            logger.error("missing :" + httpParam.name());
                            throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                        }
                        args[i] = null;
                    }
                } else if (httpParam.type() == HttpParamType.USER_ID) {
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if ("sessionId".equals(cookie.getName())) {
                                String user = stringRedisTemplate.opsForValue().get(USER_REDIS_PREFIX + cookie.getValue());
                                if (!StringUtils.isEmpty(user)) {
                                    UserDo userDo = JSONObject.parseObject(user, UserDo.class);
                                    args[i] = userDo.getId();
                                    stringRedisTemplate.expire(USER_REDIS_PREFIX + cookie.getValue(), 30, TimeUnit.MINUTES);
                                    break;
                                }
                            }
                        }

                    }
                    if (args[i] == null) {
                        String[] sessionIds = parameterMap.get("sessionId");
                        if (sessionIds != null && sessionIds.length > 0) {
                            String user = stringRedisTemplate.opsForValue().get(USER_REDIS_PREFIX + sessionIds[0]);
                            if (!StringUtils.isEmpty(user)) {
                                UserDo userDo = JSONObject.parseObject(user, UserDo.class);
                                args[i] = userDo.getId();
                                stringRedisTemplate.expire(USER_REDIS_PREFIX + sessionIds[0], 30, TimeUnit.MINUTES);
                                break;
                            }
                        }
                    }
                    if (args[i] == null) {
                        throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_USER_NOT_LOGIN);
                    }
                } else if (httpParam.type() == HttpParamType.IP) {
                    args[i] = IpAdrressUtil.getIpAdrress(request);
                } else if (httpParam.type() == HttpParamType.COOKIE) {
                    Cookie[] cookies = request.getCookies();
                    for(Cookie cookie : cookies){
                        if(httpParam.name().equals(cookie.getName())) {
                            args[i] = cookie.getValue();
                            break;
                        }

                    }

                    if(args[i] == null){
                        String[] paramArray = parameterMap.get(httpParam.name());
                        if(paramArray.length > 0){
                            args[i] = paramArray[0];
                        }
                    }
                }
            }
            Object invokeObj = method.invoke(serviceBean, args);
            ResultType resultType = httpMethod.type();
            if (!StringUtils.isEmpty(_type) && "raw".equals(_type)) {
                //如果是不用包装的直接返回
                return invokeObj;
            }
            //下面是需要包装返回的
            if (resultType == ResultType.COOKIE) {
                //加入Cookie时处理
                if (StringUtils.isEmpty(httpMethod.retName())) {
                    throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_METHOD_NOT_ANNOTATION);
                } else {
                    //setCookie
                    Cookie cookie = new Cookie(httpMethod.retName(), (String) invokeObj);
                    cookie.setPath("/");
                    if (httpMethod.maxAge() != -1) {
                        cookie.setMaxAge(httpMethod.maxAge());
                    }
                    response.addCookie(cookie);
                }
            }
            GatewayResponse gatewayResponse = new GatewayResponse();
            gatewayResponse.setCode(200);
            gatewayResponse.setTimestamp(System.currentTimeMillis());
            gatewayResponse.setResult(invokeObj);
            return gatewayResponse;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                InvocationTargetException proxy = (InvocationTargetException) e;
                Throwable targetException = proxy.getTargetException();
                if (targetException instanceof ServiceException) {
                    throw (ServiceException) targetException;
                }
            }
            logger.error("[网关] 系统未知异常", e);
            throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_SYSTEM_INNER_UNKNOWN_EXCEPTION);
        }
    }


    private void checkParam(Class<?> type, Parameter methodParam, String target) throws ServiceException {
        if (type == String.class) {
            TextFormat textFormat = methodParam.getAnnotation(TextFormat.class);
            if (textFormat != null) {
                String regex = textFormat.regex();
                if (!StringUtils.isEmpty(regex)) {
                    //如果正则生效，则直接使用正则校验
                    if (!target.matches(regex)) {
                        throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                    }
                } else {
                    boolean notChinese = textFormat.notChinese();
                    if (notChinese) {
                        if (target.matches("[\\u4e00-\\u9fa5]+")) {
                            throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                        }
                    }

                    String[] contains = textFormat.contains();
                    for (int j = 0; j < contains.length; j++) {
                        if (!target.contains(contains[j])) {
                            throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                        }
                    }

                    String[] notContains = textFormat.notContains();
                    for (int j = 0; j < notContains.length; j++) {
                        if (target.contains(notContains[j])) {
                            throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                        }
                    }

                    String startWith = textFormat.startWith();
                    if (!StringUtils.isEmpty(startWith)) {
                        if (!target.startsWith(startWith)) {
                            throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                        }
                    }

                    String endsWith = textFormat.endsWith();
                    if (!StringUtils.isEmpty(target)) {
                        if (!target.endsWith(endsWith)) {
                            throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                        }
                    }
                    int targetLength = target.length();
                    int length = textFormat.length();
                    if (length != -1) {
                        if (targetLength != length) {
                            throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                        }
                    }

                    if (targetLength < textFormat.lengthMin()) {
                        throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                    }

                    if (targetLength > textFormat.lengthMax()) {
                        throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                    }
                }
            }
        } else if (type == Integer.class) {
            Range range = methodParam.getAnnotation(Range.class);
            Integer integer = new Integer(target);
            if (range != null) {
                if (integer > range.max() || integer < range.min()) {
                    throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                }
            }
            Enum annotation = methodParam.getAnnotation(Enum.class);
            if (annotation != null) {
                int[] value = annotation.value();
                boolean contains = false;
                for (int i = 0; i < value.length; i++) {
                    if (value[i] == integer) {
                        contains = true;
                    }
                }
                if (!contains) {
                    throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                }
            }
        } else if (type == Long.class) {
            Range range = methodParam.getAnnotation(Range.class);
            if (range != null) {
                Long integer = new Long(target);
                if (integer > range.max() || integer < range.min()) {
                    throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_PARAM_CHECK_FAILED);
                }
            }
        }
    }
}
