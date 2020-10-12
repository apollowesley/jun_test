import request from './request'
let serverUrl = 'https://gw.iotechn.com/m.api/'  //开发环境
export function getExecutors(page){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'executor',
      _mt:'getExecutors',
      page
    }
  })
}
export function addClass(content,title,description){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'executor',
      _mt:'addClass',
      content,
      title,
      description
    }
  })
}
export function reloadClass(uuid,content,title,description){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'executor',
      _mt:'reloadClass',
      uuid,
      content,
      title,
      description
    }
  })
}
export function getExecutorContent(uuid){
  return request({
    url:serverUrl,
    method:'post',
    data:{
      _gp:'executor',
      _mt:'getExecutorContent',
      uuid
    }
  })  
}