import request from './request'
let serverUrl = 'https://gw.iotechn.com/m.api/'  //
//获取用户创建的设备 
export function getDevicePage(page){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'device',
      _mt:'getDevicePage',
      page
    }
  })
} 
//新创设备
export function createDevice(name,password){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'device',
      _mt:'createDevice',
      name,
      password
    }
  })
}
//更新设备
export function updateDevice(form){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'device',
      _mt:'updateDevice',
      deviceId:form.id,
      secretKey:form.secretKey,
      password:form.password,
      title:form.title,
      description:form.description
    }
  })
}
//删除设备
export function deleteDevice(form){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'device',
      _mt:'deleteDevice',
      deviceId:form.id,
      secretKey:form.secretKey
    }
  })
}
//克隆设备
export function cloneDevice(form){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'device',
      _mt:'cloneDevice',
      deviceId:form.id,
      name:form.name
    }
  })
}
//首页使用。获取设备数量以及在线数量。
export function getDeviceBasicInfo(){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'device',
      _mt:'getDeviceBasicInfo'
    }
  })
}
//
export function addDeviceCommand(form,addCommand){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'device',
      _mt:'addDeviceCommand',
      deviceId:form.id,
      title:addCommand.title,
      command:addCommand.command,
      description:addCommand.description

    }
  })
}
export function getDeviceCommandPage(page,form){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:"device",
      _mt:"getDeviceCommandPage",
      page,
      deviceId:form.id
    }
  })
}
export function deleteDeviceCommand(deviceId,commandId){
  return request({
    url:serverUrl,
    method:"post",
    data:{
      _gp:"device",
      _mt:'deleteDeviceCommand',
      deviceId,
      commandId
    }
  })

}
export function addDeviceParam(deviceId,form){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'device',
      _mt:'addDeviceParam',
      deviceId,
      title:form.title,
      name:form.name,
      expire:form.expire,
      description:form.description
    }
  })
}
export function deleteDeviceParam(deviceId,paramId){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'device',
      _mt:'deleteDeviceParam',
      deviceId,
      paramId
    }
  })
}
export function getDeviceParamPage(page,deviceId){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'device',
      _mt:'getDeviceParamPage',
      page,
      deviceId
    }
  })
}