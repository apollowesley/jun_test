package com.iotechn.iot.device.api;

import com.iotechn.iot.commons.core.annotation.HttpMethod;
import com.iotechn.iot.commons.core.annotation.HttpOpenApi;
import com.iotechn.iot.commons.core.annotation.HttpParam;
import com.iotechn.iot.commons.core.annotation.HttpParamType;
import com.iotechn.iot.commons.core.annotation.param.Enum;
import com.iotechn.iot.commons.core.annotation.param.NotNull;
import com.iotechn.iot.commons.core.annotation.param.Range;
import com.iotechn.iot.commons.core.annotation.param.TextFormat;
import com.iotechn.iot.commons.entity.IPage;
import com.iotechn.iot.commons.entity.exception.ServiceException;
import com.iotechn.iot.device.entity.DeviceCommandDo;
import com.iotechn.iot.device.entity.DeviceDo;
import com.iotechn.iot.device.entity.DeviceParamDo;
import com.iotechn.iot.device.entity.model.DeviceBasicInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-14
 * Time: 下午7:24
 */
@HttpOpenApi(group = "device", description = "Device开放Api")
public interface DeviceOpenService {

    @HttpMethod(description = "创建一个设备")
    public DeviceDo createDevice(
            @NotNull @TextFormat(notContains = {"$", "#"}, notChinese = true) @HttpParam(name = "name", description = "设备名称,用于模块权限校验") String name,
            @NotNull @TextFormat(notChinese = true) @HttpParam(name = "password", description = "密码,用于模块权限校验") String password,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "更新设备信息")
    public Boolean updateDevice(
            @NotNull @HttpParam(name = "deviceId", description = "设备Id") Long deviceId,
            @NotNull @HttpParam(name = "secretKey", description = "secretKey 用于鉴权") String secretKey,
            @HttpParam(name = "password", description = "密码 用户修改密码") String password,
            @HttpParam(name = "title", description = "前端显示的标题") String title,
            @HttpParam(name = "description", description = "前端显示的备注") String description,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "获取用户创建的设备")
    public IPage<DeviceDo> getDevicePage(
            @NotNull @Range(min = 1) @HttpParam(name = "page", description = "页码") Integer page,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;


    @HttpMethod(description = "获取设备预设命令")
    public IPage<DeviceCommandDo> getDeviceCommandPage(
            @NotNull @Range(min = 1) @HttpParam(name = "page", description = "页码") Integer page,
            @NotNull @HttpParam(name = "deviceId", description = "设备Id") Long deviceId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;


    @HttpMethod(description = "获取设备预设参数(定义)")
    public IPage<DeviceParamDo> getDeviceParamPage(
            @NotNull @Range(min = 1) @HttpParam(name = "page", description = "页码") Integer page,
            @NotNull @HttpParam(name = "deviceId", description = "设备Id") Long deviceId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;


    @HttpMethod(description = "设备删除")
    public Boolean deleteDevice(
            @NotNull @HttpParam(name = "deviceId", description = "设备Id") Long deviceId,
            @NotNull @HttpParam(name = "secretKey", description = "secretKey 用户鉴权") String secretKey,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "获取device系统基本信息")
    public DeviceBasicInfo getDeviceBasicInfo(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "添加设备参数")
    public DeviceParamDo addDeviceParam(
            @NotNull @HttpParam(name = "deviceId",description = "设备Id") Long deviceId,
            @NotNull @HttpParam(name = "title", description = "参数标题 用于前端显示 可中文") String title,
            @NotNull @HttpParam(name = "name", description = "参数名称 用于定位 仅英文") String name,
            @NotNull @Range(min = 1) @HttpParam(name = "expire", description = "过期时间（SEC）") Integer expire,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId,
            @HttpParam(name = "description", description = "设备描述") String description) throws ServiceException;

    @HttpMethod(description = "添加设备预设命令")
    public DeviceCommandDo addDeviceCommand(
            @NotNull @HttpParam(name = "deviceId", description = "设备Id") Long deviceId,
            @NotNull @HttpParam(name = "title", description = "命令名称") String title,
            @NotNull @HttpParam(name = "command", description = "透明传输给设备的字符串") String command,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId,
            @HttpParam(name = "description", description = "命令描述") String description) throws ServiceException;

    @HttpMethod(description = "删除设备预设命令")
    public Boolean deleteDeviceCommand(
            @NotNull @HttpParam(name = "deviceId", description = "设备Id") Long deviceId,
            @NotNull @HttpParam(name = "commandId", description = "命令Id") Long commandId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "删除设备预设参数")
    public Boolean deleteDeviceParam(
            @NotNull @HttpParam(name = "deviceId", description = "设备Id") Long deviceId,
            @NotNull @HttpParam(name = "paramId", description = "参数Id") Long commandId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;


    @HttpMethod(description = "克隆设备 包括命令参数等")
    public Long cloneDevice(
            @NotNull @HttpParam(name = "deviceId", description = "设备Id") Long deviceId,
            @NotNull @HttpParam(name = "name", description = "设备名称 用于鉴权") String name,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    /** APP 小程序等移动终端调用以下接口 **/

    @HttpMethod(description = "向设备发送消息（透明） APP接口")
    public String sendMsgToDevice(
            @NotNull @TextFormat(length = 64) @HttpParam(name = "secretKey", description = "secretKey 用于确定Topic") String secretKey,
            @NotNull @HttpParam(name = "msg", description = "消息正文") String msg,
            @NotNull @Enum({0, 1, 2}) @HttpParam(name = "qos", description = "服务质量 0.只发一次 1.至少发一次 2.仅有一次") Integer qos) throws ServiceException;

    @HttpMethod(description = "向设备发送命令（预设） APP接口")
    public String sendCommandToDevice(
            @NotNull @TextFormat(length = 64) @HttpParam(name = "secretKey", description = "secretKey 用于确定Topic") String secretKey,
            @NotNull @HttpParam(name = "commandId", description = "命令Id") Long commandId,
            @NotNull @Enum({0, 1, 2}) @HttpParam(name = "qos", description = "服务质量 0.只发一次 1.至少发一次(推荐) 2.仅有一次") Integer qos) throws ServiceException;

    @HttpMethod(description = "通过secretKey 获取设备 APP接口")
    public DeviceDo getDeviceBySecretKey(
            @NotNull @TextFormat(length = 64) @HttpParam(name = "secretKey", description = "secretKey 鉴权") String secretKey) throws ServiceException;

    @HttpMethod(description = "通过secretKey 获取设备预设命令 APP接口")
    public List<DeviceCommandDo> getDeviceCommandsBySecretKey(
            @NotNull @TextFormat(length = 64) @HttpParam(name = "secretKey", description = "secretKey 鉴权") String secretKey) throws ServiceException;

    @HttpMethod(description = "通过secretKey 获取设备预设参数 APP接口")
    public List<DeviceParamDo> getDeviceParamsBySecretKey(
            @NotNull @TextFormat(length = 64) @HttpParam(name = "secretKey", description = "secretKey 鉴权") String secretKey) throws ServiceException;
}
