package com.iotechn.iot.executor.api;

import com.iotechn.iot.commons.core.annotation.HttpMethod;
import com.iotechn.iot.commons.core.annotation.HttpOpenApi;
import com.iotechn.iot.commons.core.annotation.HttpParam;
import com.iotechn.iot.commons.core.annotation.HttpParamType;
import com.iotechn.iot.commons.core.annotation.param.NotNull;
import com.iotechn.iot.commons.entity.IPage;
import com.iotechn.iot.commons.entity.exception.ServiceException;
import com.iotechn.iot.executor.entity.ExecutorDeviceLogDo;
import com.iotechn.iot.executor.entity.ExecutorRegisterDo;
import com.iotechn.iot.executor.model.InvokerInfoModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-08
 * Time: 下午5:06
 */
@HttpOpenApi(group = "executor",description = "执行系统开放Api")
public interface ExecutionOpenService {

    @HttpMethod(description = "调用Groovy方法")
    public Object invokeGroovyMethod(
            @NotNull @HttpParam(name = "uuid", description = "调用私有方法密钥") String uuid,
            @NotNull @HttpParam(name = "methodName", description = "调用的方法名") String methodName,
            @HttpParam(name = "args", description = "调用传入参数数组") String args[],
            @HttpParam(name = "invokerInfo") InvokerInfoModel invokerInfoModel) throws ServiceException;

    @HttpMethod(description = "添加Groovy类")
    public String addClass(
            @NotNull @HttpParam(name = "content", description = "类的内容") String content,
            @HttpParam(name = "title", description = "设备标题 用于用户展示") String title,
            @HttpParam(name = "description", description = "设备描述") String description,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws  ServiceException;

    @HttpMethod(description = "重新加载类")
    public String reloadClass(
            @NotNull @HttpParam(name = "uuid", description = "唯一标识") String uuid,
            @HttpParam(name = "title", description = "标题") String title,
            @HttpParam(name = "description", description = "执行器描述") String description,
            @NotNull @HttpParam(name = "content", description = "新的Groovy代码") String content,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "获取执行器")
    public IPage<ExecutorRegisterDo> getExecutors(
            @NotNull @HttpParam(name = "page", description = "页码") Integer page,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "执行器详情")
    public String getExecutorContent(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId,
            @NotNull @HttpParam(name = "uuid", description = "uuid") String uuid) throws ServiceException;

    @HttpMethod(description = "获取设备日志keys")
    public List<String> getDeviceLogKeysBySk(
            @NotNull @HttpParam(name = "secretKey", description = "设备secretKey") String secretKey) throws ServiceException;

    @HttpMethod(description = "获取设备运行时产生的日志")
    public IPage<ExecutorDeviceLogDo> getDeviceLogPage(
            @NotNull @HttpParam(name = "secretKey", description = "设备secretKey") String secretKey,
            @NotNull @HttpParam(name = "key", description = "日志分组Key") String key,
            @NotNull @HttpParam(name = "pageNo", description = "页码") Integer pageNo) throws ServiceException;

    @HttpMethod(description = "测试调用通路")
    public String test(
            @NotNull @HttpParam(name = "abc", description = "") String abc);

}
