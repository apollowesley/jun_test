package cn.afterturn.http.proxy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.afterturn.http.client.ClientBuild;
import cn.afterturn.http.entity.enums.RequestDataTypeEnum;
import cn.afterturn.http.util.*;
import com.alibaba.fastjson.util.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.afterturn.http.annotation.IRequestIgnore;
import cn.afterturn.http.annotation.IRequestMethod;
import cn.afterturn.http.annotation.IRequestParam;
import cn.afterturn.http.entity.enums.RequestResultEnum;
import cn.afterturn.http.entity.enums.RequestTypeEnum;
import cn.afterturn.http.entity.enums.SignTypeEnmu;
import cn.afterturn.http.entity.params.ParamsEntity;

/**
 * 请求切面代理
 *
 * @author JueYue 2014年2月20日--上午10:41:07
 */
public class RequestProxyHandler implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 6732992402113927625L;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RequestProxyHandler.class);

    private static IRequestMethod defaultMethodParams;

    @Autowired
    private ClientBuild clientBuild;
    @Autowired
    private IJsonServer jsonServer;

    /**
     * 获取基础URL
     *
     * @param iRequest
     * @param paramsEntityList
     * @return
     */
    private String getBaseUrl(IRequestMethod iRequest, List<ParamsEntity> paramsEntityList) {
        if (iRequest != null && StringUtils.isNotBlank(iRequest.url())) {
            if (iRequest.url().contains("${")) {
                return LemurHttpConfig.getConfig(iRequest.url());
            }
            return iRequest.url();
        }
        for (int i = 0, le = paramsEntityList.size(); i < le; i++) {
            if ("URL".equalsIgnoreCase(paramsEntityList.get(i).getName())) {
                return (String) paramsEntityList.get(i).getValue();
            }
        }
        throw new RuntimeException("没有找到请求地址");
    }

    private IRequestMethod getDefaultMethodParams() {
        if (defaultMethodParams != null) {
            return defaultMethodParams;
        }
        try {
            return BaseAnnotation.class.getMethod("requestMethod").getAnnotation(
                    IRequestMethod.class);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("获取默认注解失败");
        }

    }

    private Class<?> getListRealType(String genericReturnType) {
        String realType = genericReturnType.replace("java.util.List<", "").replace(">", "");
        try {
            return Class.forName(realType);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    private List<ParamsEntity> getParamsEntityList(Object[] parameters, Method method,
                                                   IRequestMethod iRequest) throws Exception {
        Annotation[][] annotations = method.getParameterAnnotations();
        List<ParamsEntity> list = new ArrayList<ParamsEntity>();
        for (int i = 0, le = parameters.length; i < le; i++) {
            if (annotations[i] != null && annotations[i].length > 0
                    && annotations[i][0] instanceof IRequestIgnore) {
                continue;
            }
            if (ClassUtil.isJavaClass(parameters[i].getClass())) {
                addJavaClassValue(annotations[i], parameters[i], iRequest, list);
            } else {
                addUserClassValue(annotations[i], parameters[i], iRequest, list);
            }
        }
        if (!iRequest.sign().equals(SignTypeEnmu.NULL)) {
            list.add(new ParamsEntity(iRequest.signName(), CalculateSignUtil.signCal(
                    iRequest.sign(), iRequest.defaultKey(), list)));
        }
        return list;
    }


    private String getParamsEntity(Object[] parameters, Method method,
                                   IRequestMethod iRequest) throws Exception {
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0, le = parameters.length; i < le; i++) {
            if (annotations[i] != null && annotations[i].length > 0
                    && annotations[i][0] instanceof IRequestIgnore) {
                continue;
            }
            if (parameters[i] instanceof String) {
                return (String) parameters[i];
            } else if (parameters[i] instanceof Map || !ClassUtil.isJavaClass(parameters[i].getClass())) {
                return jsonServer.toJson(parameters[i]);
            }
        }
        throw new RuntimeException("参数不正确,JSON传参只允许string或者对象");
    }

    /**
     * 添加用户自定义类型参数
     *
     * @param annotations
     * @param object
     * @param iRequest
     * @param list
     * @throws Exception
     */
    private void addUserClassValue(Annotation[] annotations, Object object,
                                   IRequestMethod iRequest, List<ParamsEntity> list)
            throws Exception {
        ParamsEntity prefix = new ParamsEntity();
        for (int k = 0, ale = annotations.length; k < ale; k++) {
            if (annotations[k] instanceof IRequestParam) {
                prefix.setName(((IRequestParam) annotations[k]).value());
                prefix.setOrder(((IRequestParam) annotations[k]).order());
                prefix.setSign(((IRequestParam) annotations[k]).isSign());
            }
        }
        addUserClassValueRecycle(prefix, object, list, iRequest);
    }

    /**
     * 循环入参
     *
     * @param prefix
     * @param object
     * @param list
     * @param iRequest
     * @throws Exception
     */
    private void addUserClassValueRecycle(ParamsEntity prefix, Object object,
                                          List<ParamsEntity> list, IRequestMethod iRequest)
            throws Exception {
        ReflectorUtil reflectorUtil = ReflectorUtil.fromCache(object.getClass());
        Field filed;
        ParamsEntity entity;
        for (int i = 0; i < reflectorUtil.getFieldList().size(); i++) {
            filed = reflectorUtil.getFieldList().get(i);
            if (filed.getAnnotation(IRequestIgnore.class) != null) {
                continue;
            }
            entity = new ParamsEntity();
            if (filed.getAnnotation(IRequestParam.class) != null) {
                IRequestParam params = filed.getAnnotation(IRequestParam.class);
                entity.setName(prefix.getName() + params.value());
                entity.setOrder(params.order());
                entity.setSign(params.isSign());
            } else {
                entity.setName(prefix.getName() + filed.getName());
            }
            if (ClassUtil.isJavaClass(filed.getType())) {
                if (iRequest.isTranscoding()) {
                    entity.setValue(URLEncoder.encode(String.valueOf(reflectorUtil.getValue(object,
                            filed.getName())), iRequest.encode().getValue()));
                } else {
                    entity
                            .setValue(String.valueOf(reflectorUtil.getValue(object, filed.getName())));
                }
                list.add(entity);
            } else {
                addUserClassValueRecycle(entity, reflectorUtil.getValue(object, filed.getName()),
                        list, iRequest);
            }
        }
    }

    /**
     * 添加java 自带类型参数
     *
     * @param annotations
     * @param parameters
     * @param iRequest
     * @param list
     * @throws Exception
     */
    private void addJavaClassValue(Annotation[] annotations, Object parameters,
                                   IRequestMethod iRequest, List<ParamsEntity> list)
            throws Exception {
        ParamsEntity entity = new ParamsEntity();
        for (int k = 0, ale = annotations.length; k < ale; k++) {
            if (annotations[k] instanceof IRequestParam) {
                entity.setName(((IRequestParam) annotations[k]).value());
                entity.setOrder(((IRequestParam) annotations[k]).order());
                entity.setSign(((IRequestParam) annotations[k]).isSign());
            }
        }
        if (StringUtils.isEmpty(entity.getName())) {
            throw new RuntimeException("这个参数没有注释名称:" + parameters.toString());
        }
        if (!"URL".equalsIgnoreCase(entity.getName())) {
            if (iRequest.isTranscoding()) {
                entity.setValue(URLEncoder.encode(String.valueOf(parameters), iRequest.encode()
                        .getValue()));
            } else {
                entity.setValue(String.valueOf(parameters));
            }
        } else {
            entity.setValue(String.valueOf(parameters));
        }
        list.add(entity);
    }

    /**
     * 拼接URL
     *
     * @param paramsEntityList
     * @param iRequest
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getParamsForGet(String baseUrl, List<ParamsEntity> paramsEntityList,
                                   IRequestMethod iRequest) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        boolean isAddAnd = false;
        if (baseUrl.contains("?")) {
            isAddAnd = true;
        }
        for (int i = 0, le = paramsEntityList.size(); i < le; i++) {
            if ("URL".equalsIgnoreCase(paramsEntityList.get(i).getName())) {
                continue;
            }
            if (isAddAnd) {
                stringBuilder.append("&");
            } else {
                stringBuilder.append("?");
                isAddAnd = true;
            }
            stringBuilder.append(paramsEntityList.get(i).getName());
            stringBuilder.append("=");
            stringBuilder.append(paramsEntityList.get(i).getValue());
        }
        return stringBuilder.toString();
    }

    private List<NameValuePair> getRequestBodyForPost(List<ParamsEntity> paramsEntityList) {
        List<NameValuePair> data = new ArrayList<NameValuePair>(paramsEntityList.size());
        for (int i = 0; i < paramsEntityList.size(); i++) {
            data.add(new BasicNameValuePair(paramsEntityList.get(i).getName(), paramsEntityList.get(i)
                    .getValue()));
        }
        return data;
    }

    private HttpEntity getRequestBodyForWebservice(String encode, String content,
                                                   List<ParamsEntity> paramsEntityList)
            throws Exception {
        Collections.sort(paramsEntityList);
        Object[] paramsArr = new Object[paramsEntityList.size()];
        for (int i = 0, le = paramsEntityList.size(); i < le; i++) {
            paramsArr[i] = paramsEntityList.get(i).getValue();
        }
        String soapXml = String.format(content, paramsArr);
        byte bytes[] = soapXml.getBytes(encode);
        InputStream inputStream = new ByteArrayInputStream(bytes, 0, bytes.length);
        HttpEntity requestEntity = new InputStreamEntity(inputStream, bytes.length, ContentType.create("application/xml", Consts.UTF_8));
        return requestEntity;
    }

    private String getResponseResult(IRequestMethod iRequest, Object param)
            throws Exception {
        if (iRequest.type().equals(RequestTypeEnum.POST)) {
            if (iRequest.dataType().equals(RequestDataTypeEnum.FORM)) {
                return getResponseResultByPost(iRequest, (List<ParamsEntity>) param);
            } else {
                return getResponseResultByPost(iRequest, (String) param);
            }
        } else if (iRequest.type().equals(RequestTypeEnum.GET)) {
            return getResponseResultByGet(iRequest, (List<ParamsEntity>) param);
        } else if (iRequest.type().equals(RequestTypeEnum.WEBSERVICE)) {
            return getResponseResultByWebService(iRequest, (List<ParamsEntity>) param);
        } else if (iRequest.type().equals(RequestTypeEnum.REST)) {
            return getResponseResultByRest(iRequest, (List<ParamsEntity>) param);
        }
        return null;
    }

    /**
     * 获取数据get请求
     *
     * @param iRequest
     * @param paramsEntityList
     * @return
     */
    private String getResponseResultByGet(IRequestMethod iRequest,
                                          List<ParamsEntity> paramsEntityList) throws IOException {
        String url = getParamsForGet(getBaseUrl(iRequest, paramsEntityList), paramsEntityList,
                iRequest);
        return getResponseResultByUrl(iRequest, url);
    }

    /**
     * 获取数据 post请求
     *
     * @param iRequest
     * @param paramsEntityList
     * @return
     */
    private String getResponseResultByPost(IRequestMethod iRequest,
                                           List<ParamsEntity> paramsEntityList) {
        HttpPost httpPost = new HttpPost(getBaseUrl(iRequest, paramsEntityList));
        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(getRequestBodyForPost(paramsEntityList)));
            response = clientBuild.getClient().execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return clientBuild.getBody(response);
            } else {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("请求状态{},参数{},信息{}", response.getStatusLine().getStatusCode(), jsonServer.toJson(paramsEntityList),
                            clientBuild.getBody(response));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.close(response);
        }
        return null;
    }

    /**
     * 获取数据 post请求
     *
     * @param iRequest
     * @param json
     * @return
     */
    private String getResponseResultByPost(IRequestMethod iRequest, String json) {
        HttpPost httpPost = new HttpPost(getBaseUrl(iRequest, null));
        CloseableHttpResponse response = null;
        try {
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=UTF-8");
            httpPost.setHeader("Accept", "application/json; charset=UTF-8");
            StringEntity entity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(entity);
            response = clientBuild.getClient().execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return clientBuild.getBody(response);
            } else {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("请求状态{},参数{},信息{}", response.getStatusLine().getStatusCode(), json,
                            clientBuild.getBody(response));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.close(response);
        }
        return null;
    }

    /**
     * Rest请求 2014年5月15日
     *
     * @param iRequest
     * @param paramsEntityList
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getResponseResultByRest(IRequestMethod iRequest,
                                           List<ParamsEntity> paramsEntityList)
            throws UnsupportedEncodingException {
        Collections.sort(paramsEntityList);
        String url = getBaseUrl(iRequest, paramsEntityList);
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        for (int i = 0; i < paramsEntityList.size(); i++) {
            if (!paramsEntityList.get(i).getName().equalsIgnoreCase("URL")) {
                url += "/" + paramsEntityList.get(i).getValue();
            }
        }
        return getResponseResultByUrl(iRequest, url);
    }

    private String getResponseResultByUrl(IRequestMethod iRequest, String url) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("请求URL:{}", url);
        }
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = clientBuild.getClient().execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return clientBuild.getBody(response);
            } else {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("请求状态{},参数{},信息{}", response.getStatusLine().getStatusCode(), url,
                            clientBuild.getBody(response));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.close(response);
        }
        return null;
    }

    /**
     * 获取数据 webserver请求
     *
     * @param iRequest
     * @param paramsEntityList
     * @return
     * @throws Exception
     */
    private String getResponseResultByWebService(IRequestMethod iRequest,
                                                 List<ParamsEntity> paramsEntityList)
            throws Exception {
        HttpPost httpPost = new HttpPost(getBaseUrl(iRequest, paramsEntityList));
        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(getRequestBodyForWebservice(iRequest.encode().getValue(),
                    iRequest.webserver(), paramsEntityList));
            response = clientBuild.getClient().execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return clientBuild.getBody(response);
            } else {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("请求状态{},参数{},信息{}", response.getStatusLine().getStatusCode(), jsonServer.toJson(paramsEntityList),
                            clientBuild.getBody(response));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.close(response);
        }
        return null;
    }

    private Object getReturnObject(Method method, String responseResult, IRequestMethod iRequest) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("responseResult is  : {}", responseResult);
        }
        if (responseResult == null) {
            return null;
        }
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            if (returnType.isAssignableFrom(String.class)) {
                return responseResult;
            }
            // 基本类型
            if (returnType.isPrimitive()) {
                Number number = jsonServer.parseJson(responseResult, BigDecimal.class);
                if ("int".equals(returnType.getSimpleName())) {
                    return number.intValue();
                }
                if ("long".equals(returnType.getSimpleName())) {
                    return number.longValue();
                }
                if ("double".equals(returnType.getSimpleName())) {
                    return number.doubleValue();
                }
            }
            if (iRequest.result().equals(RequestResultEnum.JSON)) {
                // List类型
                if (returnType.isAssignableFrom(List.class)) {
                    returnType = getListRealType(method.getGenericReturnType().toString());
                    return jsonServer.parseJsonList(responseResult, returnType);
                }
                return jsonServer.parseJson(responseResult, returnType);
            } else {
                return XMLParseUtil.getEntity(responseResult, returnType, iRequest.encode()
                        .getValue());
            }
        }
        return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            IRequestMethod iRequest = method.getAnnotation(IRequestMethod.class);
            if (iRequest == null) {
                iRequest = getDefaultMethodParams();
            }
            if (RequestDataTypeEnum.JSON.equals(iRequest.dataType())) {
                String param = getParamsEntity(args, method, iRequest);
                return getReturnObject(method, getResponseResult(iRequest, param), iRequest);
            } else {
                List<ParamsEntity> paramsEntityList = getParamsEntityList(args, method, iRequest);
                return getReturnObject(method, getResponseResult(iRequest, paramsEntityList), iRequest);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

}
