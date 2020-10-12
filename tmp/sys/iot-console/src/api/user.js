import request from './request'
let serverUrl = 'https://gw.iotechn.com/m.api/'  
//注册接口
export function yzm(phone){
    return request({
        url:serverUrl,
        method: 'post', 
        data:{ 
            _gp:'user',
            _mt:'sendVerifyCode', 
            phone
        }
    })
} 
export function register(phone,password,verifyCode){
    return request({
        url:serverUrl,
        method: 'post', 
        data:{
            _gp:'user',
            _mt:'register',
            phone,
            password,
            verifyCode
        }
    })
}
export function login(phone,password){
    return request({
        url:serverUrl,
        method:'post',
        data:{
            _gp:'user',
            _mt:'login',
            phone,
            password
        }
    })
}
export function getMsg(_gp,_mt){
   return request({
        url:serverUrl,
        method:'post',
        data:{
            _gp,
            _mt
        }
    })
}
export function getMsgDesc(page){
    return request({
        url:serverUrl,
        method:'post',
        data:{
            _gp:'user',
            _mt:'getUserNotifyPage',
            page
        }
    })
}
export function readMsg(notifyIds){
    return request({
        url:serverUrl,
        method:'post',
        data:{
            _gp:'user',
            _mt:'markNotifyRead',
            notifyIds
        }
    })
}